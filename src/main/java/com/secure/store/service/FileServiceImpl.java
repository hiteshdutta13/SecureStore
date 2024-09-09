package com.secure.store.service;

import com.secure.store.constant.GlobalConstants;
import com.secure.store.entity.*;
import com.secure.store.entity.util.Status;
import com.secure.store.modal.Advisory;
import com.secure.store.modal.FileDTO;
import com.secure.store.modal.Response;
import com.secure.store.modal.SharedFileDTO;
import com.secure.store.repository.*;
import com.secure.store.util.DateTimeUtil;
import com.secure.store.util.EntityToModelTransformer;
import com.secure.store.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl extends GlobalService implements FileService {
    //Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    FolderRepository folderRepository;
    @Autowired
    GlobalRepository globalRepository;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    SharedFileRepository sharedFileRepository;
    @Autowired
    SharedFileToUserRepository sharedFileToUserRepository;

    @Override
    public Response upload(Long folderId, MultipartFile file) {
        var response = new Response();
        var preFix = globalRepository.findBy(GlobalConstants.KEYWORD_DOCUMENT_PATH_PREFIX);
        if(preFix.isPresent() && file != null) {
            try {
                var saveFile = new File();
                String path = FileUtil.docFilePath(preFix.get().getValue(), this.getUserId());
                String fileName = System.currentTimeMillis() + FileUtil.DOT + FileUtil.getFileExtension(file.getOriginalFilename());
                if (folderId > 0) {
                    path += folderRepository.getReferenceById(folderId).getPath();
                    saveFile.setPath(folderRepository.getReferenceById(folderId).getPath());
                    var folder = new Folder();
                    folder.setId(folderId);
                    saveFile.setFolder(folder);
                } else {
                    FileUtil.createDirectory(path);
                    saveFile.setPath(FileUtil.FORWARD_SLASH);
                }
                saveFile.setName(fileName);
                saveFile.setOriginalName(file.getOriginalFilename());
                saveFile.setContentType(file.getContentType());
                saveFile.setSize(Math.round(file.getSize() / 1024.0));
                saveFile.setStatus(Status.Active);
                saveFile.setUser(this.getUser());
                saveFile.setCreatedDateTime(DateTimeUtil.currentDateTime());
                saveFile.setUpdatedDateTime(saveFile.getCreatedDateTime());
                if (FileUtil.write(file.getBytes(), path, fileName)) {
                    fileRepository.save(saveFile);
                    response.setPersistId(saveFile.getId());
                }else {
                    response = new Response(false);
                    var advisory = new Advisory();
                    advisory.setMessage("Error occurred during file write");
                    response.getAdvisories().add(advisory);
                }
            }catch (IOException ioException) {
                response = new Response(false);
                var advisory = new Advisory();
                advisory.setMessage(ioException.getMessage());
                response.getAdvisories().add(advisory);
            }
        }else {
            response = new Response(false);
            var advisory = new Advisory();
            advisory.setMessage("File pre-fix path not configured");
            response.getAdvisories().add(advisory);
        }
        return response;
    }

    @Override
    public Response share(SharedFileDTO sharedFileDTO) {
        var response = new Response();
        if(sharedFileDTO != null && sharedFileDTO.getFile() != null && sharedFileDTO.getFile().getId() != null && sharedFileDTO.getToUsers() != null) {
            var sharedFile = new SharedFile();
            List<SharedFileToUser> listToUsers = new ArrayList<>();
            var existingSharedFile = sharedFileRepository.findBy(sharedFileDTO.getFile().getId());
            Set<Long> sharedWith;
            if(existingSharedFile.isPresent()) {
                sharedFile.setId(existingSharedFile.get().getId());
                listToUsers = existingSharedFile.get().getSharedFileToUsers();
                sharedWith = listToUsers.stream().map(sharedFileToUser -> sharedFileToUser.getUserId().getId()).collect(Collectors.toSet());
            } else {
                sharedWith = new HashSet<>();
            }
            List<SharedFileToUser> finalListToUsers = listToUsers;
            sharedFileDTO.getToUsers().stream().filter(toUser -> toUser.getId() != null && toUser.getId() > 0 && !sharedWith.contains(toUser.getId())).forEach(toUser -> {
                Optional<SharedFileToUser> optionalSharedFileToUser = sharedFileToUserRepository.findBy(toUser.getId(), this.getUserId(), sharedFileDTO.getFile().getId());
                if(optionalSharedFileToUser.isEmpty()) {
                    var sharedFileToUser = new SharedFileToUser();
                    var userId = new User();
                    userId.setId(toUser.getId());
                    sharedFileToUser.setUserId(userId);
                    sharedFileToUser.setSharedFile(sharedFile);
                    sharedFileToUser.setSharedDateTime(DateTimeUtil.currentDateTime());
                    finalListToUsers.add(sharedFileToUser);
                }
            });
            if(listToUsers.size() > 0) {
                sharedFile.setSharedFileToUsers(listToUsers);
                var file = new File();
                file.setId(sharedFileDTO.getFile().getId());
                sharedFile.setFile(file);
                sharedFile.setSharedBy(this.getUser());
                sharedFileRepository.save(sharedFile);
            }else {
                response.addMessage("File already shared with users");
            }
        }else {
            response.setSuccess(false);
            response.addMessage("Error occurred while sharing the file.");
        }
        return response;
    }

    @Override
    public byte[] getFile(Long id, boolean shared) {
        var preFix = globalRepository.findBy(GlobalConstants.KEYWORD_DOCUMENT_PATH_PREFIX);
        if(preFix.isPresent()) {
            try {
                File file = fileRepository.getReferenceById(id);
                Long userId = this.getUserId();
                if(shared) {
                    Optional<SharedFile> optionalSharedFile = sharedFileRepository.findBy(file.getId());
                    if(optionalSharedFile.isPresent()) {
                        userId = optionalSharedFile.get().getSharedBy().getId();
                    }
                }
                Path path = Paths.get(FileUtil.fileLocation(preFix.get().getValue(), userId, file));
                return Files.readAllBytes(path);
            }catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } return new byte[0];
    }

    @Override
    public ResponseEntity<Resource> download(Long id, boolean shared) {
        var preFix = globalRepository.findBy(GlobalConstants.KEYWORD_DOCUMENT_PATH_PREFIX);
        if(preFix.isPresent()) {
            try {
                File file = fileRepository.getReferenceById(id);
                Long userId = this.getUserId();
                if(shared) {
                    Optional<SharedFile> optionalSharedFile = sharedFileRepository.findBy(file.getId());
                    if(optionalSharedFile.isPresent()) {
                        userId = optionalSharedFile.get().getSharedBy().getId();
                    }
                }
                Path path = Paths.get(FileUtil.fileLocation(preFix.get().getValue(), userId, file)).normalize();
                Resource resource = new UrlResource(path.toUri());
                if (resource.exists()) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(file.getContentType()))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginalName() + "\"")
                            .body(resource);
                } else {
                    return ResponseEntity.notFound().build();
                }
            }catch (MalformedURLException e) {
                return ResponseEntity.notFound().build();
            }
        } return ResponseEntity.notFound().build();
    }

    @Override
    public FileDTO get(Long id) {
        File file = fileRepository.getReferenceById(id);
        return EntityToModelTransformer.transform(file);
    }

    @Override
    public Response delete(Long id) {
        File file = fileRepository.getReferenceById(id);
        var existingSharedFile = sharedFileRepository.findBy(id);
        if(existingSharedFile.isPresent()) {
            var sharedFile = existingSharedFile.get();
            List<SharedFileToUser> sharedFileToUsers = sharedFile.getSharedFileToUsers();
            sharedFileToUsers.forEach(sharedFileToUser -> sharedFileToUserRepository.deleteById(sharedFileToUser.getId()));
            sharedFileRepository.deleteById(sharedFile.getId());
        }
        file.setStatus(Status.Deleted);
        file.setUpdatedDateTime(DateTimeUtil.currentDateTime());
        fileRepository.save(file);
        return new Response();
    }

    @Override
    public Response rename(FileDTO fileDTO) {
        if(fileDTO != null && fileDTO.getId() != null && fileDTO.getId() > 0) {
            File file = fileRepository.getReferenceById(fileDTO.getId());
            file.setOriginalName(fileDTO.getName()+"."+file.getName().split("\\.")[1]);
            fileRepository.save(file);
            return new Response();
        }else  {
            return new Response(false);
        }
    }
}

package com.secure.store.service;

import com.secure.store.constant.GlobalConstants;
import com.secure.store.entity.File;
import com.secure.store.entity.Folder;
import com.secure.store.entity.SharedFile;
import com.secure.store.entity.User;
import com.secure.store.entity.util.Status;
import com.secure.store.modal.Advisory;
import com.secure.store.modal.Response;
import com.secure.store.modal.SharedFileDTO;
import com.secure.store.repository.FileRepository;
import com.secure.store.repository.SharedFileRepository;
import com.secure.store.repository.FolderRepository;
import com.secure.store.repository.GlobalRepository;
import com.secure.store.util.DateTimeUtil;
import com.secure.store.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Optional;

@Service
public class FileServiceImpl extends GlobalService implements FileService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    FolderRepository folderRepository;
    @Autowired
    GlobalRepository globalRepository;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    SharedFileRepository sharedFileRepository;

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
                    saveFile.setPath("/");
                }
                saveFile.setName(fileName);
                saveFile.setOriginalName(file.getOriginalFilename());
                saveFile.setContentType(file.getContentType());
                saveFile.setSize(file.getSize());
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
        if(sharedFileDTO != null && sharedFileDTO.getFile() != null && sharedFileDTO.getFile().getId() != null && sharedFileDTO.getToUser() != null && sharedFileDTO.getToUser().getId() != null) {
            Optional<SharedFile> optionalSharedFile = sharedFileRepository.findBy(sharedFileDTO.getToUser().getId(), this.getUserId(), sharedFileDTO.getFile().getId());
            if(optionalSharedFile.isEmpty()) {
                var sharedFile = new SharedFile();
                var file = new File();
                file.setId(sharedFileDTO.getFile().getId());
                sharedFile.setFile(file);
                sharedFile.setSharedBy(this.getUser());
                var sharedTo = new User();
                sharedTo.setId(sharedFileDTO.getToUser().getId());
                sharedFile.setSharedTo(sharedTo);
                sharedFile.setSharedDateTime(DateTimeUtil.currentDateTime());
                sharedFileRepository.save(sharedFile);
            }else {
                response.setSuccess(false);
                response.addMessage("File already shared with this user.");
            }
        }else {
            response.setSuccess(false);
            response.addMessage("Error occurred while sharing the file.");
        }
        return response;
    }

    @Override
    public byte[] getFile(Long id) {
        var preFix = globalRepository.findBy(GlobalConstants.KEYWORD_DOCUMENT_PATH_PREFIX);
        if(preFix.isPresent()) {
            try {
                File file = fileRepository.getReferenceById(id);
                String filePath = FileUtil.docFilePath(preFix.get().getValue(), this.getUserId()) + file.getPath() + (file.getPath().trim().equals("/") ? "":"/") + file.getName();
                logger.info("load file from location: "+filePath);
                Path path = Paths.get(filePath);
                return Files.readAllBytes(path);
            }catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } return new byte[0];
    }

    @Override
    public ResponseEntity<Resource> download(Long id) {
        var preFix = globalRepository.findBy(GlobalConstants.KEYWORD_DOCUMENT_PATH_PREFIX);
        if(preFix.isPresent()) {
            try {
                File file = fileRepository.getReferenceById(id);
                String filePath = FileUtil.docFilePath(preFix.get().getValue(), this.getUserId()) + file.getPath() + (file.getPath().trim().equals("/") ? "" : "/") + file.getName();
                logger.info("download file from location: " + filePath);
                Path path = Paths.get(filePath);
                Resource resource = new UrlResource(path.toString());
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
}

package com.secure.store.service;

import com.secure.store.constant.GlobalConstants;
import com.secure.store.constant.SettingConstants;
import com.secure.store.entity.*;
import com.secure.store.entity.util.Status;
import com.secure.store.modal.*;
import com.secure.store.repository.*;
import com.secure.store.util.DateTimeUtil;
import com.secure.store.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class FolderServiceImpl extends GlobalService implements FolderService {
    @Autowired
    FolderRepository folderRepository;
    @Autowired
    GlobalRepository globalRepository;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    SettingRepository settingRepository;
    @Autowired
    SharedFileRepository sharedFileRepository;
    @Autowired
    SharedFileToUserRepository sharedFileToUserRepository;

    @Override
    public Response create(FolderDTO folderDTO) {
        var response = new Response();
        var preFix = globalRepository.findBy(GlobalConstants.KEYWORD_DOCUMENT_PATH_PREFIX);
        if(preFix.isPresent() && folderDTO != null && StringUtils.hasText(folderDTO.getName())) {
            var path = FileUtil.FORWARD_SLASH+folderDTO.getName().trim();
            var folder = new Folder();
            if(folderDTO.getParent() != null && folderDTO.getParent().getId() != null && folderDTO.getParent().getId() > 0) {
                if(folderRepository.findBy(this.getUserId(), folderDTO.getParent().getId(), folderDTO.getName().trim()).isPresent()) {
                    response = new Response(false);
                    response.addMessage("A folder named '"+folderDTO.getName().trim()+"' already exists. Please choose a different name.");
                    return response;
                }else {
                    Optional<Folder> optionalFolder = folderRepository.findById(folderDTO.getParent().getId());
                    if (optionalFolder.isPresent()) {
                        path = optionalFolder.get().getPath() + FileUtil.FORWARD_SLASH + folderDTO.getName().trim();
                        var parent = new Folder();
                        parent.setId(folderDTO.getParent().getId());
                        folder.setParent(parent);
                    }
                }
            }else if(folderRepository.findBy(this.getUserId(), folderDTO.getName().trim()).isPresent()) {
                response = new Response(false);
                response.addMessage("A folder named '"+folderDTO.getName().trim()+"' already exists. Please choose a different name.");
                return response;
            }
            FileUtil.createDirectory(FileUtil.docFilePath(preFix.get().getValue(), this.getUserId())+path);
            folder.setName(folderDTO.getName().trim());
            folder.setPath(path);
            folder.setUser(this.getUser());
            folder.setCreatedDateTime(DateTimeUtil.currentDateTime());
            folder.setUpdateDateTime(folder.getCreatedDateTime());
            folderRepository.save(folder);
            response.setPersistId(folder.getId());
        }else {
            response = new Response(false);
            var advisory = new Advisory();
            advisory.setMessage("File pre-fix path not configured");
            response.getAdvisories().add(advisory);
        }
        return response;
    }

    @Override
    public Response rename(FolderDTO folderDTO) {
        return new Response();
    }

    @Override
    public Response delete(FolderDTO folderDTO) {
        return null;
    }

    @Override
    public DriveDTO findBy(Long id) {
        var driveDTO = new DriveDTO();
        var folder = folderRepository.getReferenceById(id);
        driveDTO.setFolder(this.transform(folder));
        Optional<Setting> optionalSetting = settingRepository.findBy(SettingConstants.DRIVE_DEFAULT_VIEW, this.getUserId());
        driveDTO.setView("grid");
        optionalSetting.ifPresent(setting -> driveDTO.setView(setting.getValue()));
        driveDTO.setBreadcrumb(buildFolderBreadcrumb(folder));
        return driveDTO;
    }

    @Override
    public DriveDTO findAll() {
        var driveDTO = new DriveDTO();
        driveDTO.setFolders(this.transformList(folderRepository.findBy(this.getUserId())));
        driveDTO.setFiles(this.transform(fileRepository.findBy(this.getUserId(), Status.Active)));
        driveDTO.setView("grid");
        Optional<Setting> optionalSetting = settingRepository.findBy(SettingConstants.DRIVE_DEFAULT_VIEW, this.getUserId());
        optionalSetting.ifPresent(setting -> driveDTO.setView(setting.getValue()));
        var breadcrumb = new Breadcrumb();
        breadcrumb.setName("My Drive");
        driveDTO.setBreadcrumb(breadcrumb);
        driveDTO.setSharedFilesWithYou(this.sharedFilesWithYou(sharedFileToUserRepository.findBy(this.getUserId())));
        driveDTO.setSharedFilesByYou(this.sharedFiles(sharedFileRepository.findBySharedBY(this.getUserId())));
        return driveDTO;
    }

    @Override
    public FolderDTO get(Long id) {
        var folderDTO = this.transform(folderRepository.getReferenceById(id));
        folderDTO.setProperty(this.buildProperty(id));
        return folderDTO;
    }

    UserDTO transform(User user) {
        var userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        return userDTO;
    }

    List<SharedFileDTO> sharedFiles(List<SharedFile> sharedFiles) {
        var files = new ArrayList<SharedFileDTO>();
        Optional.ofNullable(sharedFiles).orElseGet(Collections::emptyList).forEach(sharedFile -> {
            var sharedFileDTO = new SharedFileDTO();
            sharedFileDTO.setFile(this.transform(sharedFile.getFile()));
            sharedFileDTO.setId(sharedFile.getId());
            sharedFileDTO.setSharedBy(this.transform(sharedFile.getSharedBy()));
            var listOfUsers = new ArrayList<SharedFileToUserDTO>();
            sharedFile.getSharedFileToUsers().forEach(sharedFileToUser -> {
                var sharedFileToUserDTO = new SharedFileToUserDTO();
                sharedFileToUserDTO.setUserDTO(transform(sharedFileToUser.getUserId()));
                sharedFileToUserDTO.setSharedDateTime(DateTimeUtil.formatDate(sharedFileToUser.getSharedDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
                listOfUsers.add(sharedFileToUserDTO);
            });
            sharedFileDTO.setToUsers(listOfUsers);
            files.add(sharedFileDTO);
        });
        return files;
    }
    List<SharedFileDTO> sharedFilesWithYou(List<SharedFileToUser> sharedFileToUsers) {
        var files = new ArrayList<SharedFileDTO>();
        Optional.ofNullable(sharedFileToUsers).orElseGet(Collections::emptyList).forEach(sharedFileToUser -> {
            var sharedFileDTO = new SharedFileDTO();
            var sharedFile = sharedFileToUser.getSharedFile();
            sharedFileDTO.setFile(this.transform(sharedFile.getFile()));
            sharedFileDTO.setId(sharedFile.getId());
            sharedFileDTO.setSharedBy(this.transform(sharedFile.getSharedBy()));
            sharedFileDTO.setSharedDateTime(DateTimeUtil.formatDate(sharedFileToUser.getSharedDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
            files.add(sharedFileDTO);
        });
        return files;
    }
    FileDTO transform(File file) {
        var fileDTO = new FileDTO();
        fileDTO.setId(file.getId());
        fileDTO.setName(file.getName());
        fileDTO.setType(file.getContentType());
        fileDTO.setOriginalName(file.getOriginalName());
        fileDTO.setSize(file.getSize());
        fileDTO.setPath(file.getPath());
        if(file.getFolder() != null) {
            fileDTO.setFolderId(file.getFolder().getId());
        }
        fileDTO.setCreatedDateTime(DateTimeUtil.formatDate(file.getCreatedDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
        fileDTO.setUpdatedDateTime(DateTimeUtil.formatDate(file.getUpdatedDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
        return fileDTO;
    }
    List<FileDTO> transform(List<File> files) {
        var fileDTOs = new ArrayList<FileDTO>();
        Optional.ofNullable(files).orElseGet(Collections::emptyList).forEach(file -> fileDTOs.add(this.transform(file)));
        return fileDTOs;
    }
    List<FolderDTO> transformList(List<Folder> folders) {
       var folderDTOS = new ArrayList<FolderDTO>();
       Optional.ofNullable(folders).orElseGet(Collections::emptyList).forEach(folder -> folderDTOS.add(this.transform(folder)));
       return folderDTOS;
    }
    void calculateFolderProperty(PropertyDTO propertyDTO, List<Folder> folders) {
       Optional.ofNullable(folders).orElseGet(Collections::emptyList).forEach(folder -> {
           propertyDTO.setFolders(propertyDTO.getFolders() + 1);
           Optional.ofNullable(fileRepository.findByFolder(folder.getId(), Status.Active)).orElseGet(Collections::emptyList).forEach(file -> {
               propertyDTO.setFiles(propertyDTO.getFiles() + 1);
               propertyDTO.setSize(propertyDTO.getSize() + file.getSize());
           });
           this.calculateFolderProperty(propertyDTO, folderRepository.findByParent(folder.getId()));
       });
    }
    FolderPropertyDTO buildProperty(Long folderId) {
        var folderPropertyDTO = new FolderPropertyDTO();
        Folder folder = folderRepository.getReferenceById(folderId);
        folderPropertyDTO.setName(folder.getName());
        var propertyDTO = new PropertyDTO();
        Optional.ofNullable(fileRepository.findByFolder(folder.getId(), Status.Active)).orElseGet(Collections::emptyList).forEach(file -> {
            propertyDTO.setFiles(propertyDTO.getFiles() + 1);
            propertyDTO.setSize(propertyDTO.getSize() + file.getSize());
        });
        this.calculateFolderProperty(propertyDTO, folderRepository.findByParent(folder.getId()));
        folderPropertyDTO.setFolders(propertyDTO.getFolders());
        folderPropertyDTO.setSize(propertyDTO.getSize());
        folderPropertyDTO.setFiles(propertyDTO.getFiles());
        folderPropertyDTO.setCreatedDateTime(DateTimeUtil.formatDate(folder.getCreatedDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
        return folderPropertyDTO;
    }

    FolderDTO transform(Folder folder) {
        var folderDTO = new FolderDTO();
        folderDTO.setId(folder.getId());
        folderDTO.setName(folder.getName());
        folderDTO.setPath(folder.getPath());
        folderDTO.setFiles(this.transform(fileRepository.findBy(this.getUserId(), folder.getId(), Status.Active)));
        folderDTO.setCreatedDateTime(DateTimeUtil.formatDate(folder.getCreatedDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
        folderDTO.setUpdatedDateTime(DateTimeUtil.formatDate(folder.getUpdateDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
        folderDTO.setSubFolders(transformList(folderRepository.findBy(this.getUserId(), folder.getId())));
        return folderDTO;
    }

    Breadcrumb buildFolderBreadcrumb(Folder folder) {
        var breadcrumb = new Breadcrumb();
        if(folder.getParent() != null) {
            breadcrumb.setPrev(buildFolderBreadcrumb(folder.getParent()));
        }else {
            var bc = new Breadcrumb();
            bc.setName("My Drive");
            breadcrumb.setPrev(bc);
        }
        breadcrumb.setId(folder.getId());
        breadcrumb.setName(folder.getName());
        return breadcrumb;
    }
}

package com.secure.store.service;

import com.secure.store.constant.GlobalConstants;
import com.secure.store.constant.SettingConstants;
import com.secure.store.entity.*;
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
            var path = FileUtil.FORWARD_SLASH+folderDTO.getName();
            var folder = new Folder();
            if(folderDTO.getParent() != null && folderDTO.getParent().getId() != null && folderDTO.getParent().getId() > 0) {
                Optional<Folder> optionalFolder = folderRepository.findById(folderDTO.getParent().getId());
                if(optionalFolder.isPresent()) {
                    path = optionalFolder.get().getPath()+FileUtil.FORWARD_SLASH+folderDTO.getName();
                    var parent = new Folder();
                    parent.setId(folderDTO.getParent().getId());
                    folder.setParent(parent);
                }
            }
            FileUtil.createDirectory(FileUtil.docFilePath(preFix.get().getValue(), this.getUserId())+path);
            folder.setName(folderDTO.getName());
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
        driveDTO.setFiles(this.transform(fileRepository.findBy(this.getUserId())));
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
        Optional.ofNullable(sharedFiles).orElseGet(Collections::emptyList).forEach(documentShare -> {
            var sharedFile = new SharedFileDTO();
            sharedFile.setFile(this.transform(documentShare.getFile()));
            sharedFile.setId(documentShare.getId());
            sharedFile.setSharedBy(this.transform(documentShare.getSharedBy()));
            var listOfUsers = new ArrayList<SharedFileToUserDTO>();
            documentShare.getSharedFileToUsers().forEach(sharedFileToUser -> {
                var sharedFileToUserDTO = new SharedFileToUserDTO();
                sharedFileToUserDTO.setUserDTO(transform(sharedFileToUser.getUserId()));
                sharedFileToUserDTO.setSharedDateTime(DateTimeUtil.formatDate(sharedFileToUser.getSharedDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
                listOfUsers.add(sharedFileToUserDTO);
            });
            sharedFile.setToUsers(listOfUsers);
            files.add(sharedFile);
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
    FileDTO transform(File document) {
        var file = new FileDTO();
        file.setId(document.getId());
        file.setName(document.getName());
        file.setType(document.getContentType());
        file.setOriginalName(document.getOriginalName());
        file.setSize(document.getSize());
        file.setPath(document.getPath());
        if(document.getFolder() != null) {
            file.setFolderId(document.getFolder().getId());
        }
        file.setCreatedDateTime(DateTimeUtil.formatDate(document.getCreatedDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
        file.setUpdatedDateTime(DateTimeUtil.formatDate(document.getUpdatedDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
        return file;
    }
    List<FileDTO> transform(List<File> documents) {
        var files = new ArrayList<FileDTO>();
        Optional.ofNullable(documents).orElseGet(Collections::emptyList).forEach(document -> files.add(this.transform(document)));
        return files;
    }
    List<FolderDTO> transformList(List<Folder> folders) {
       var folderDTOS = new ArrayList<FolderDTO>();
       Optional.ofNullable(folders).orElseGet(Collections::emptyList).forEach(folder -> folderDTOS.add(this.transform(folder)));
       return folderDTOS;
    }

    FolderDTO transform(Folder folder) {
        var folderDTO = new FolderDTO();
        folderDTO.setId(folder.getId());
        folderDTO.setName(folder.getName());
        folderDTO.setPath(folder.getPath());
        folderDTO.setFiles(this.transform(fileRepository.findBy(this.getUserId(), folder.getId())));
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

package com.secure.store.service;

import com.secure.store.constant.GlobalConstants;
import com.secure.store.constant.SettingConstants;
import com.secure.store.entity.Document;
import com.secure.store.entity.Folder;
import com.secure.store.entity.Setting;
import com.secure.store.modal.*;
import com.secure.store.repository.DocumentRepository;
import com.secure.store.repository.FolderRepository;
import com.secure.store.repository.GlobalRepository;
import com.secure.store.repository.SettingRepository;
import com.secure.store.util.DateTimeUtil;
import com.secure.store.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FolderServiceImpl extends GlobalService implements FolderServiceIf {
    @Autowired
    FolderRepository folderRepository;
    @Autowired
    GlobalRepository globalRepository;
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    SettingRepository settingRepository;

    @Override
    public Response create(FolderDTO folderDTO) {
        var response = new Response();
        var preFix = globalRepository.findBy(GlobalConstants.KEYWORD_DOCUMENT_PATH_PREFIX);
        if(preFix.isPresent() && folderDTO != null && StringUtils.hasText(folderDTO.getName())) {
            var path = FileUtil.FORWARD_SLASH+folderDTO.getName();
            if(folderDTO.getParent() != null && folderDTO.getParent().getId() != null && folderDTO.getParent().getId() > 0) {
                Optional<Folder> optionalFolder = folderRepository.findById(folderDTO.getParent().getId());
                if(optionalFolder.isPresent()) {
                    path = FileUtil.FORWARD_SLASH+ optionalFolder.get().getPath()+FileUtil.FORWARD_SLASH+folderDTO.getName();
                }
            }
            FileUtil.createDirectory(FileUtil.docFilePath(preFix.get().getValue(), this.getUserId())+path);
            var folder = new Folder();
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
            advisory.setMessage("Document pre-fix path not configured");
            response.getAdvisories().add(advisory);
        }
        return response;
    }

    @Override
    public Response rename(FolderDTO folderDTO) {
        if(folderDTO != null && folderDTO.getId() != null && folderDTO.getId() > 0) {
            Optional<Folder> optionalFolder = folderRepository.findById(folderDTO.getId());
            if(optionalFolder.isPresent()) {

            }
        }
        return new Response();
    }

    @Override
    public Response delete(FolderDTO folderDTO) {
        return null;
    }

    @Override
    public FolderDTO findBy(Long id) {
        return transform(folderRepository.getReferenceById(id));
    }

    @Override
    public DriveDTO findAll() {
        var driveDTO = new DriveDTO();
        driveDTO.setFolders(transformList(folderRepository.findBy(this.getUserId())));
        driveDTO.setFiles(this.transform(documentRepository.findBy(this.getUserId())));
        Optional<Setting> optionalSetting = settingRepository.findBy(SettingConstants.DRIVE_DEFAULT_VIEW, this.getUserId());
        optionalSetting.ifPresent(setting -> driveDTO.setView(setting.getValue()));
        return driveDTO;
    }
    FileDTO transform(Document document) {
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
    List<FileDTO> transform(List<Document> documents) {
        var files = new ArrayList<FileDTO>();
        Optional.ofNullable(documents).orElseGet(Collections::emptyList).forEach(document -> {
            files.add(transform(document));
        });
        return files;
    }
    List<FolderDTO> transformList(List<Folder> folders) {
       var folderDTOS = new ArrayList<FolderDTO>();
       Optional.ofNullable(folders).orElseGet(Collections::emptyList).forEach(folder -> {
            folderDTOS.add(transform(folder));
       });
       return folderDTOS;
    }

    FolderDTO transform(Folder folder) {
        var folderDTO = new FolderDTO();
        folderDTO.setId(folder.getId());
        folderDTO.setName(folder.getName());
        folderDTO.setPath(folder.getPath());
        folderDTO.setFiles(this.transform(documentRepository.findBy(this.getUserId(), folder.getId())));
        folderDTO.setCreatedDateTime(DateTimeUtil.formatDate(folder.getCreatedDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
        folderDTO.setUpdatedDateTime(DateTimeUtil.formatDate(folder.getUpdateDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
        folderDTO.setSubFolders(transformList(folderRepository.findBy(this.getUserId(), folder.getId())));
        return folderDTO;
    }
}

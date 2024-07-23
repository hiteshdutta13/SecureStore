package com.secure.store.service;

import com.secure.store.constant.GlobalConstants;
import com.secure.store.entity.Folder;
import com.secure.store.modal.Advisory;
import com.secure.store.modal.FolderDTO;
import com.secure.store.modal.Response;
import com.secure.store.repository.FoldersRepository;
import com.secure.store.repository.GlobalRepository;
import com.secure.store.util.DateTimeUtil;
import com.secure.store.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FolderImpl extends GlobalService implements FolderIf {
    @Autowired
    FoldersRepository foldersRepository;

    @Autowired
    GlobalRepository globalRepository;

    @Override
    public Response create(FolderDTO folderDTO) {
        var response = new Response();
        var preFix = globalRepository.findBy(GlobalConstants.KEYWORD_DOCUMENT_PATH_PREFIX);
        if(preFix.isPresent() && folderDTO != null) {
            var path = FileUtil.FORWARD_SLASH+folderDTO.getName();
            if(folderDTO.getParent() != null && folderDTO.getParent().getId() != null && folderDTO.getParent().getId() > 0) {
                Optional<Folder> optionalFolder = foldersRepository.findById(folderDTO.getParent().getId());
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
            foldersRepository.save(folder);
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
            Optional<Folder> optionalFolder = foldersRepository.findById(folderDTO.getId());
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
        return transform(foldersRepository.getReferenceById(id));
    }

    @Override
    public List<FolderDTO> findAll() {
        return transformList(foldersRepository.findBy(this.getUserId()));
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
        folderDTO.setCreatedDateTime(DateTimeUtil.formatDate(folder.getCreatedDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
        folderDTO.setUpdatedDateTime(DateTimeUtil.formatDate(folder.getUpdateDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
        folderDTO.setSubFolders(transformList(foldersRepository.findBy(this.getUserId(), folder.getId())));
        return folderDTO;
    }
}

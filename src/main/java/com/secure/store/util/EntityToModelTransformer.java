package com.secure.store.util;

import com.secure.store.entity.File;
import com.secure.store.modal.FileDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EntityToModelTransformer {

    public static List<FileDTO> transform(List<File> files) {
        var fileDTOs = new ArrayList<FileDTO>();
        Optional.ofNullable(files).orElseGet(Collections::emptyList).forEach(file -> fileDTOs.add(EntityToModelTransformer.transform(file)));
        return fileDTOs;
    }

    public static FileDTO transform(File file) {
        var fileDTO = new FileDTO();
        fileDTO.setId(file.getId());
        fileDTO.setName(file.getName());
        fileDTO.setType(file.getContentType());
        fileDTO.setExtension(file.getName().split("\\.")[1]);
        fileDTO.setOriginalName(file.getOriginalName().split("\\."+fileDTO.getExtension())[0]);
        fileDTO.setSize(file.getSize());
        fileDTO.setPath(file.getPath());
        if(file.getFolder() != null) {
            fileDTO.setFolderId(file.getFolder().getId());
        }
        fileDTO.setCreatedDateTime(DateTimeUtil.formatDate(file.getCreatedDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
        fileDTO.setUpdatedDateTime(DateTimeUtil.formatDate(file.getUpdatedDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
        return fileDTO;
    }
}

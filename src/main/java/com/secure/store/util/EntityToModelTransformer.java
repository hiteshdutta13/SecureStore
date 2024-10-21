package com.secure.store.util;

import com.secure.store.entity.File;
import com.secure.store.entity.SharedFile;
import com.secure.store.entity.SharedFileToUser;
import com.secure.store.entity.User;
import com.secure.store.modal.FileDTO;
import com.secure.store.modal.SharedFileDTO;
import com.secure.store.modal.SharedFileToUserDTO;
import com.secure.store.modal.UserDTO;

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
    public static UserDTO transform(User user) {
        var userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public static List<SharedFileToUserDTO> transformListOfSharedFileToUsers(List<SharedFileToUser> sharedFileToUsers) {
        var sharedFileToUserDTOs = new ArrayList<SharedFileToUserDTO>();
        Optional.ofNullable(sharedFileToUsers).orElseGet(Collections::emptyList).forEach(sharedFileToUser -> sharedFileToUserDTOs.add(EntityToModelTransformer.transform(sharedFileToUser)));
        return sharedFileToUserDTOs;
    }

    public static SharedFileToUserDTO transform(SharedFileToUser sharedFileToUser) {
        var sharedFileToUserDTO = new SharedFileToUserDTO();
        sharedFileToUserDTO.setUserDTO(transform(sharedFileToUser.getUserId()));
        sharedFileToUserDTO.setId(sharedFileToUser.getId());
        sharedFileToUserDTO.setSharedDateTime(DateTimeUtil.formatDate(sharedFileToUser.getSharedDateTime(), DateTimeUtil.DATE_TIME_FORMAT_UI));
        return sharedFileToUserDTO;
    }
    public static SharedFileDTO transform(SharedFile sharedFile) {
        var sharedFileDTO = new SharedFileDTO();
        sharedFileDTO.setId(sharedFile.getId());
        sharedFileDTO.setFile(transform(sharedFile.getFile()));
        sharedFileDTO.setSharedBy(transform(sharedFile.getSharedBy()));
        sharedFileDTO.setToUsers(transformListOfSharedFileToUsers(sharedFile.getSharedFileToUsers()));
        return sharedFileDTO;
    }
}

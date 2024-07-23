package com.secure.store.service;

import com.secure.store.modal.FolderDTO;
import com.secure.store.modal.Response;

import java.util.List;

public interface FolderIf {
    Response create(FolderDTO folderDTO);
    Response rename(FolderDTO folderDTO);
    Response delete(FolderDTO folderDTO);
    FolderDTO findBy(Long id);
    List<FolderDTO> findAll();
}

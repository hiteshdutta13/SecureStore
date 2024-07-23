package com.secure.store.service;

import com.secure.store.modal.DriveDTO;
import com.secure.store.modal.FolderDTO;
import com.secure.store.modal.Response;

public interface FolderServiceIf {
    Response create(FolderDTO folderDTO);
    Response rename(FolderDTO folderDTO);
    Response delete(FolderDTO folderDTO);
    FolderDTO findBy(Long id);
    DriveDTO findAll();
}

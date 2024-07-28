package com.secure.store.service;

import com.secure.store.modal.DriveDTO;
import com.secure.store.modal.FolderDTO;
import com.secure.store.modal.Response;

public interface FolderService {
    Response create(FolderDTO folderDTO);
    Response rename(FolderDTO folderDTO);
    Response delete(FolderDTO folderDTO);
    DriveDTO findBy(Long id);
    DriveDTO findAll();
}

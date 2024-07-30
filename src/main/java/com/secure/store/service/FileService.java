package com.secure.store.service;

import com.secure.store.modal.Response;
import com.secure.store.modal.SharedFileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Response upload(Long folderId, MultipartFile file);
    Response share(SharedFileDTO sharedFileDTO);

}

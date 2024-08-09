package com.secure.store.service;

import com.secure.store.modal.FileDTO;
import com.secure.store.modal.Response;
import com.secure.store.modal.SharedFileDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Response upload(Long folderId, MultipartFile file);
    Response share(SharedFileDTO sharedFileDTO);
    byte[] getFile(Long id, boolean shared);
    ResponseEntity<Resource> download(Long id, boolean shared);
    FileDTO get(Long id);
    Response delete(Long id);
}

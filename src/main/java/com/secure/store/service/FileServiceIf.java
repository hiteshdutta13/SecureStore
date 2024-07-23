package com.secure.store.service;

import com.secure.store.modal.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileServiceIf {
    Response upload(Long folderId, MultipartFile file);

}

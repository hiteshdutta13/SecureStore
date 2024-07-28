package com.secure.store.api;

import com.secure.store.modal.Response;
import com.secure.store.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileAPI {

    @Autowired
    FileService fileService;

    @PostMapping(value = "/file/upload/{id}")
    public ResponseEntity<Response> upload( @PathVariable("id") Long folderId,
                                            @RequestParam("file") MultipartFile file) {
        var response = fileService.upload(folderId, file);
        if(response.isSuccess()) {
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}

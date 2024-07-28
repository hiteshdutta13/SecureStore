package com.secure.store.api;

import com.secure.store.modal.FolderDTO;
import com.secure.store.modal.Response;
import com.secure.store.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FolderAPI {
    @Autowired
    private FolderService folderService;

    @PostMapping(value = "/folder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> folder(@RequestBody FolderDTO folder) {
        var response = folderService.create(folder);
        if(response.isSuccess()) {
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping(value = "/folder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> renameFolder(@RequestBody FolderDTO folder) {
        var response = folderService.rename(folder);
        if(response.isSuccess()) {
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping(value = "/folder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> deleteFolder(@RequestBody FolderDTO folder) {
        var response = folderService.delete(folder);
        if(response.isSuccess()) {
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}

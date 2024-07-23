package com.secure.store.api;

import com.secure.store.modal.FolderDTO;
import com.secure.store.modal.Response;
import com.secure.store.service.FolderServiceIf;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FolderAPI {
    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    private FolderServiceIf folderServiceIf;

    @GetMapping(value = "/folder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> folders() {
        var response = new Response();
        response.setData(folderServiceIf.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/folder/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> folder(@PathVariable("id") Long id) {
        var response = new Response();
        response.setData(folderServiceIf.findBy(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/folder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> folder(@RequestBody FolderDTO folder) {
        var response = folderServiceIf.create(folder);
        if(response.isSuccess()) {
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping(value = "/folder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> renameFolder(@RequestBody FolderDTO folder) {
        var response = folderServiceIf.rename(folder);
        if(response.isSuccess()) {
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping(value = "/folder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> deleteFolder(@RequestBody FolderDTO folder) {
        var response = folderServiceIf.delete(folder);
        if(response.isSuccess()) {
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}

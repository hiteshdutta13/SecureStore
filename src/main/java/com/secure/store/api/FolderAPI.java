package com.secure.store.api;

import com.secure.store.modal.FolderDTO;
import com.secure.store.modal.Response;
import com.secure.store.service.FolderIf;
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
    private FolderIf folderIf;

    @GetMapping(value = "/folder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> folders() {
        var response = new Response();
        response.setData(folderIf.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/folder/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> folder(@PathVariable("id") Long id) {
        var response = new Response();
        response.setData(folderIf.findBy(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/folder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> folder(@RequestBody FolderDTO folder) {
        Response response = folderIf.create(folder);
        if(response.isSuccess()) {
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping(value = "/folder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> renameFolder(@RequestBody FolderDTO folder) {
        Response response = folderIf.rename(folder);
        if(response.isSuccess()) {
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping(value = "/folder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> deleteFolder(@RequestBody FolderDTO folder) {
        Response response = folderIf.delete(folder);
        if(response.isSuccess()) {
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}

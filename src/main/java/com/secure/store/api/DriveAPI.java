package com.secure.store.api;

import com.secure.store.modal.Response;
import com.secure.store.modal.SettingDTO;
import com.secure.store.service.FolderService;
import com.secure.store.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DriveAPI {
    @Autowired
    FolderService folderService;
    @Autowired
    SettingService settingService;

    @GetMapping(value = "/drive", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> drive() {
        var response = new Response();
        response.setData(folderService.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/drive/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> drive(@PathVariable("id") Long id) {
        var response = new Response();
        response.setData(folderService.findBy(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/drive/view", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> view(@RequestBody SettingDTO settingDTO) {
        var response = settingService.save(settingDTO);
        if(response.isSuccess()) {
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}

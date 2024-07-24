package com.secure.store.api;

import com.secure.store.modal.Response;
import com.secure.store.modal.SettingDTO;
import com.secure.store.service.FolderServiceIf;
import com.secure.store.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DriveAPI {

    @Autowired
    FolderServiceIf folderServiceIf;

    @Autowired
    SettingService settingService;

    @GetMapping(value = "/drive", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> drive() {
        var response = new Response();
        response.setData(folderServiceIf.findAll());
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

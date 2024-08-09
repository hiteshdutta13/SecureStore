package com.secure.store.api;

import com.secure.store.modal.Response;
import com.secure.store.modal.SharedFileDTO;
import com.secure.store.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
public class FileAPI {

    @Autowired
    FileService fileService;

    @ResponseBody
    @PostMapping(value = "/upload/{id}")
    public ResponseEntity<Response> upload( @PathVariable("id") Long folderId,
                                            @RequestParam("file") MultipartFile file) {
        var response = fileService.upload(folderId, file);
        if(response.isSuccess()) {
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @ResponseBody
    @PostMapping(value = "/share")
    public ResponseEntity<Response> share( @RequestBody SharedFileDTO sharedFileDTO) {
        var response = fileService.share(sharedFileDTO);
        if(response.isSuccess()) {
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @ResponseBody
    @RequestMapping (value="/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable("id") Long id,
                        @RequestParam(value = "shared", required = false, defaultValue = "false") boolean shared) {
        return fileService.getFile(id, shared);
    }

    @ResponseBody
    @RequestMapping (value="/pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] pdf(@PathVariable("id") Long id,
                      @RequestParam(value = "shared", required = false, defaultValue = "false") boolean shared) {
        return fileService.getFile(id, shared);
    }

    @ResponseBody
    @RequestMapping (value="/other/{id}")
    public byte[] text(@PathVariable("id") Long id,
                       @RequestParam(value = "shared", required = false, defaultValue = "false") boolean shared) {
        return fileService.getFile(id, shared);
    }

    @GetMapping(value = "/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") Long id,
                                             @RequestParam(value = "shared", required = false, defaultValue = "false") boolean shared) {
        return fileService.download(id, shared);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response> get(@PathVariable("id") Long id) {
        var response = new Response();
        response.setData(fileService.get(id));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
        var response = new Response();
        response.setData(fileService.delete(id));
        return ResponseEntity.ok(response);
    }

}

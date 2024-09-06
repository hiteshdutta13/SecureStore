package com.secure.store.api;

import com.secure.store.modal.Response;
import com.secure.store.service.BinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bin")
public class BinAPI {

    @Autowired
    BinService binService;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> bin() {
        var response = new Response();
        response.setData(binService.findAll());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
        var response = new Response();
        response.setData(binService.delete(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/restore/{id}")
    public ResponseEntity<Response> restore(@PathVariable("id") Long id) {
        var response = new Response();
        response.setData(binService.restore(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<Response> delete(@RequestBody List<Long> ids) {
        var response = binService.delete(ids);
        if(response.isSuccess()) {
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping(value = "/restore")
    public ResponseEntity<Response> restore(@RequestBody List<Long> ids) {
        var response = binService.restore(ids);
        if(response.isSuccess()) {
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping(value = "/delete/all")
    public ResponseEntity<Response> deleteAll() {
        var response = new Response();
        response.setData(binService.restoreAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/restore/all")
    public ResponseEntity<Response> restoreAll() {
        var response = new Response();
        response.setData(binService.restoreAll());
        return ResponseEntity.ok(response);
    }

}

package com.secure.store.api;

import com.secure.store.modal.Response;
import com.secure.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommonAPI {
    @Autowired
    UserService userService;

    @GetMapping(value = "/user/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> filter(@RequestParam("keyword") String keyword) {
        var response = new Response();
        response.setData(userService.filter(keyword));
        return ResponseEntity.ok(response);
    }
}

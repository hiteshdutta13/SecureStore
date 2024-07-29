package com.secure.store.service;

import com.secure.store.modal.Response;
import com.secure.store.modal.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<Response> register(UserDTO user);
    UserDTO get(Long id);
    UserDTO getActive();
    Response resetPassword(String email);
    Response validate(String token);
}

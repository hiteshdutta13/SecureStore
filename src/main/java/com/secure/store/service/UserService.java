package com.secure.store.service;

import com.secure.store.modal.ResetPasswordDTO;
import com.secure.store.modal.Response;
import com.secure.store.modal.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<Response> register(UserDTO userDTO);
    UserDTO get(Long id);
    UserDTO getActive();
    Response resetPassword(String email);
    Response validate(String token);
    List<UserDTO> filter(String email);
    Response changePassword(ResetPasswordDTO resetPasswordDTO);
    Response update(UserDTO userDTO);
}

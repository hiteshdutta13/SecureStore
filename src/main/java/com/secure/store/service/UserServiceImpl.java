package com.secure.store.service;

import com.secure.store.entity.User;
import com.secure.store.modal.Advisory;
import com.secure.store.modal.Response;
import com.secure.store.modal.UserDTO;
import com.secure.store.repository.UserRepository;
import com.secure.store.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserServiceIf {
    @Autowired
    private UserRepository repository;

    @Override
    public ResponseEntity<Response> register(UserDTO user) {
        if(repository.findBy(user.getUsername()).isPresent() || repository.findByEmail(user.getEmail()).isPresent()){
            var response = new Response(false);
            var advisory = new Advisory();
            advisory.setCode(1001);
            advisory.setMessage("Username or email already registered");
            response.getAdvisories().add(advisory);
            return ResponseEntity.badRequest().body(response);
        }
        var registerUser = new User();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        registerUser.setPassword(encodedPassword);
        registerUser.setEmail(user.getEmail());
        registerUser.setUsername(user.getUsername());
        registerUser.setFirstName(user.getFirstName());
        registerUser.setLastName(user.getLastName());
        registerUser.setCreatedDateTime(DateTimeUtil.currentDateTime());
        registerUser.setUpdateDateTime(registerUser.getCreatedDateTime());
        repository.save(registerUser);
        return null;
    }

    @Override
    public UserDTO get(Long id) {
        return null;
    }
}

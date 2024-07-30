package com.secure.store.service;

import com.secure.store.entity.User;
import com.secure.store.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GlobalService {

    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getId();
        }
        return null; // or throw an appropriate exception
    }

    public User getUser() {
        var user = new User();
        user.setId(getUserId());
        return user;
    }

}

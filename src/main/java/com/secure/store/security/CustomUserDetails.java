package com.secure.store.security;

import com.secure.store.modal.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final UserDTO user;
    public CustomUserDetails(UserDTO user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }
    public Long getId() {
        return user.getId();
    }
}

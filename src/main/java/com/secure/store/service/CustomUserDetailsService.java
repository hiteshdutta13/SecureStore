package com.secure.store.service;

import com.secure.store.entity.User;
import com.secure.store.modal.UserDTO;
import com.secure.store.repository.UserRepository;
import com.secure.store.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> users = userRepository.findBy(username);
        var user = new UserDTO();
        if(users.isPresent()) {
            user = this.buildUser(users.get());
        }else {
            users = userRepository.findByEmail(username);
            if(users.isPresent()) {
                user = this.buildUser(users.get());
            }else {
                throw new UsernameNotFoundException("Username not found");
            }
        }
        return new CustomUserDetails(user);
    }
    private UserDTO buildUser(User dbUser) {
        var user = new UserDTO();
        user.setId(dbUser.getId());
        user.setEmail(dbUser.getEmail());
        user.setUsername(dbUser.getUsername());
        user.setPassword(dbUser.getPassword());
        user.setLastName(dbUser.getLastName());
        user.setFirstName(dbUser.getFirstName());
        return user;
    }
}

package com.secure.store.service;

import com.secure.store.entity.User;
import com.secure.store.entity.UserSession;
import com.secure.store.modal.UserDTO;
import com.secure.store.repository.UserRepository;
import com.secure.store.repository.UserSessionRepository;
import com.secure.store.security.CustomUserDetails;
import com.secure.store.util.DateTimeUtil;
import com.secure.store.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserSessionRepository userSessionRepository;

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
        Optional.ofNullable(userSessionRepository.findBy(user.getId())).orElseGet(Collections::emptyList).forEach(userSession -> {
            userSession.setLogoutDateTime(DateTimeUtil.currentDateTime());
            userSessionRepository.save(userSession);
        });
        var userSession = new UserSession();
        userSession.setLoginDateTime(DateTimeUtil.currentDateTime());
        var userId = new User();
        userId.setId(user.getId());
        userSession.setUser(userId);
        userSession.setToken(TokenUtil.generateToken());
        userSessionRepository.save(userSession);
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

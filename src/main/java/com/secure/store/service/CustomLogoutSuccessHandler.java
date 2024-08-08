package com.secure.store.service;

import com.secure.store.entity.User;
import com.secure.store.repository.UserRepository;
import com.secure.store.repository.UserSessionRepository;
import com.secure.store.util.DateTimeUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    UserSessionRepository userSessionRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Optional<User> optionalUser = userRepository.findBy(authentication.getName());
        optionalUser.ifPresent(user -> Optional.ofNullable(userSessionRepository.findBy(user.getId())).orElseGet(Collections::emptyList).forEach(userSession -> {
            userSession.setLogoutDateTime(DateTimeUtil.currentDateTime());
            userSessionRepository.save(userSession);
        }));
        response.sendRedirect("/");
    }
}

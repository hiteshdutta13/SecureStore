package com.secure.store.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String errorMessage = "1";
        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            errorMessage = "2";
        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            errorMessage = "3";
        }

        response.sendRedirect("/login?error=" + errorMessage);
    }
}

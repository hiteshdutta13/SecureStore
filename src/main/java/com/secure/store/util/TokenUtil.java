package com.secure.store.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenUtil {
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";

    // Compile the pattern
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static String generateToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
    public static String generate6DigitCode() {
        int code = secureRandom.nextInt(900000) + 100000; // Generates a random number between 100000 and 999999
        return String.valueOf(code);
    }

    public static String getApplicationUrl(HttpServletRequest request) {
        return ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(request.getContextPath())
                .build()
                .toUriString();
    }

    public static boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static String getErrorMessage(String password) {
        if (password.length() < 6) {
            return "Password must be at least 6 characters long.";
        }
        if (!password.matches(".*[0-9].*")) {
            return "Password must contain at least one digit.";
        }
        if (!password.matches(".*[a-z].*")) {
            return "Password must contain at least one lowercase letter.";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Password must contain at least one uppercase letter.";
        }
        if (!password.matches(".*[@#$%^&+=].*")) {
            return "Password must contain at least one special character (e.g., @, #, $, %, ^, &, +, =).";
        }
        if (password.matches(".*\\s.*")) {
            return "Password must not contain any whitespace characters.";
        }

        return "Password does not match the required pattern.";
    }

}

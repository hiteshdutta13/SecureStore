package com.secure.store.modal;

import lombok.Data;

@Data
public class ResetPasswordDTO {
    private String newPassword;
    private String confirmPassword;
    private String token;
}

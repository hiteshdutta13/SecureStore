package com.secure.store.modal;

import lombok.Data;

@Data
public class SharedFileToUserDTO {
    private Long id;
    private UserDTO userDTO;
    private String sharedDateTime;
}

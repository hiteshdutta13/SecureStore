package com.secure.store.modal;

import lombok.Data;

@Data
public class SharedFileDTO {
    private Long id;
    private String sharedDateTime;
    private UserDTO toUser;
    private UserDTO sharedBy;
    private FileDTO file;
}

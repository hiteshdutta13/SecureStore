package com.secure.store.modal;

import lombok.Data;

import java.util.List;

@Data
public class SharedFileDTO {
    private Long id;
    private String sharedDateTime;
    private List<SharedFileToUserDTO> toUsers;
    private UserDTO sharedBy;
    private FileDTO file;
}

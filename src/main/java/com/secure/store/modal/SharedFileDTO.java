package com.secure.store.modal;

import lombok.Data;

import java.util.List;

@Data
public class SharedFileDTO {
    private Long id;
    private String sharedDateTime;
    private UserDTO toUser;
    private List<Long> toUsers;
    private UserDTO sharedBy;
    private FileDTO file;
}

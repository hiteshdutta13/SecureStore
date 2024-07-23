package com.secure.store.modal;

import lombok.Data;

@Data
public class FileDTO {
    private Long id;
    private String name;
    private String type;
    private int size;
}

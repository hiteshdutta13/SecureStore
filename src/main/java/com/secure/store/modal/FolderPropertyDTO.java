package com.secure.store.modal;

import lombok.Data;

@Data
public class FolderPropertyDTO {
    private String name;
    private Long folders;
    private Long files;
    private Long size;
    private String createdDateTime;
}

package com.secure.store.modal;

import lombok.Data;

@Data
public class FileDTO {
    private Long id;
    private String name;
    private String originalName;
    private String path;
    private String extension;
    private String type;
    private Long size;
    private String status;
    private Long folderId;
    private String createdDateTime;
    private String updatedDateTime;
}

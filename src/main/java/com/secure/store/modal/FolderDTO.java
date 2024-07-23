package com.secure.store.modal;

import lombok.Data;

import java.util.List;

@Data
public class FolderDTO {
    private Long id;
    private String name;
    private String path;
    private FolderDTO parent;
    private String createdDateTime;
    private String updatedDateTime;
    private List<FolderDTO> subFolders;
    private List<FileDTO> files;
}

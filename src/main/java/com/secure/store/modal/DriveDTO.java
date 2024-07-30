package com.secure.store.modal;

import lombok.Data;

import java.util.List;

@Data
public class DriveDTO {
    private String view;
    private FolderDTO folder;
    private List<FolderDTO> folders;
    private List<FileDTO> files;
    private List<SharedFileDTO> sharedFiles;
    private Breadcrumb breadcrumb;
}

package com.secure.store.modal;

import lombok.Data;

import java.util.List;

@Data
public class DriveDTO {
    private String view;
    private FolderDTO folder;
    private List<FolderDTO> folders;
    private List<FileDTO> files;
    private List<SharedFileDTO> sharedFilesWithYou;
    private List<SharedFileDTO> sharedFilesByYou;
    private Breadcrumb breadcrumb;
}

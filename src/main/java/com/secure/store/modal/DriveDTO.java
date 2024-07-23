package com.secure.store.modal;

import lombok.Data;

import java.util.List;

@Data
public class DriveDTO {
    private List<FolderDTO> folders;
    private List<FileDTO> files;
}

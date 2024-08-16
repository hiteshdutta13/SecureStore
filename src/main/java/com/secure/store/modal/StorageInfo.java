package com.secure.store.modal;

import lombok.Data;

@Data
public class StorageInfo {
    private Long totalSpace;
    private Long freeSpace;
    private Long occupiedSpace;
    private Integer occupiedSpaceInPercentage;
}

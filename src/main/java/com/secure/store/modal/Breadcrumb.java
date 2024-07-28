package com.secure.store.modal;

import lombok.Data;

@Data
public class Breadcrumb {
    private String name;
    private Long id;
    private Breadcrumb prev;
}

package com.secure.store.modal;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Response {
    private List<Advisory> advisories = new ArrayList<>();
    private boolean success;
    private Long persistId;
    private Object data;
    public Response() {
        this.success = true;
    }
    public Response(boolean success) {
        this.success = success;
    }
}

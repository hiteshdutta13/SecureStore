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
    public void addMessage(String message) {
        var advisory = new Advisory();
        advisory.setMessage(message);
        advisories.add(advisory);
    }
    public String getMessage() {
        if(!advisories.isEmpty()) {
            return advisories.get(0).getMessage();
        } return null;
    }
}

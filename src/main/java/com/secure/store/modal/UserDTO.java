package com.secure.store.modal;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String dateOfBirth;
    private String gender;
    private String status;
    private List<SettingDTO> settings = new ArrayList<>();
    private PlanDTO plan;
    private StorageInfo storageInfo;
}

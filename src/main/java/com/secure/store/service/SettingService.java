package com.secure.store.service;

import com.secure.store.modal.Response;
import com.secure.store.modal.SettingDTO;

public interface SettingService {
    Response save(SettingDTO settingDTO);
}

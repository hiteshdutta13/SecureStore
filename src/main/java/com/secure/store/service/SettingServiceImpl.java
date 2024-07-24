package com.secure.store.service;

import com.secure.store.entity.Setting;
import com.secure.store.modal.Advisory;
import com.secure.store.modal.Response;
import com.secure.store.modal.SettingDTO;
import com.secure.store.repository.SettingRepository;
import com.secure.store.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl extends GlobalService implements SettingService {

    @Autowired
    SettingRepository settingRepository;

    @Override
    public Response save(SettingDTO settingDTO) {
        var response = new Response();
        if(settingDTO != null) {
            try {
                var setting = new Setting();
                if (settingDTO.getId() != null && settingDTO.getId() > 0) {
                    setting = settingRepository.getReferenceById(settingDTO.getId());
                    setting.setUpdateDateTime(DateTimeUtil.currentDateTime());
                } else {
                    setting.setCreatedDateTime(DateTimeUtil.currentDateTime());
                    setting.setUpdateDateTime(setting.getCreatedDateTime());
                    setting.setKeyword(settingDTO.getKeyword());
                }
                setting.setUser(this.getUser());
                setting.setValue(settingDTO.getValue());
                settingRepository.save(setting);
                response.setPersistId(setting.getId());
            }catch (Exception e) {
                response = new Response(false);
                var advisory = new Advisory();
                advisory.setMessage(e.getMessage());
                response.getAdvisories().add(advisory);
            }
        }
        return response;
    }
}

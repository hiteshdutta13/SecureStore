package com.secure.store.service;

import com.secure.store.modal.PlanDTO;
import com.secure.store.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    PlanRepository planRepository;

    @Override
    public List<PlanDTO> getAll() {
        var list = new ArrayList<PlanDTO>();
        planRepository.findAll().forEach(plan -> {
            var planDTO = new PlanDTO();
            planDTO.setId(plan.getId());
            planDTO.setName(plan.getName());
            planDTO.setPrice(plan.getPrice());
            planDTO.setShare(plan.getShare());
            planDTO.setStorage(plan.getStorage());
            planDTO.setPricingType(plan.getPricingType().toString());
            planDTO.setStorageType(plan.getStorageType().toString());
            planDTO.setStatus(plan.getStatus().toString());
            list.add(planDTO);
        });
        return list;
    }
}

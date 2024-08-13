package com.secure.store.modal;
import lombok.Data;

@Data
public class PlanDTO {
    private Long id;
    private String name;
    private Long storage;
    private String storageType;
    private Integer share;
    private Double price;
    private String pricingType;
    private String status;
}

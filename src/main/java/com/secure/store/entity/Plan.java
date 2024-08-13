package com.secure.store.entity;

import com.secure.store.entity.util.PricingType;
import com.secure.store.entity.util.Status;
import com.secure.store.entity.util.StorageType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="PLAN")
public class Plan {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="NAME", nullable = false, unique = true)
    private String name;

    @Column(name="STORAGE", nullable = false)
    private Long storage;

    @Enumerated(EnumType.STRING)
    @Column(name="STORAGE_TYPE", nullable = false)
    private StorageType storageType;

    @Column(name="SHARE", nullable = false)
    private Integer share;

    @Column(name="PRICE", nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name="PRICING_TYPE", nullable = false)
    private PricingType pricingType;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS", nullable = false)
    private Status status;
}

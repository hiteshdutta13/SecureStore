package com.secure.store.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="GLOBAL")
public class Global {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="KEYWORD", nullable = false, unique = true)
    private String keyword;

    @Column(name="VALUE", nullable = false)
    private String value;
}

package com.secure.store.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="SETTING", uniqueConstraints = @UniqueConstraint(columnNames= {"USER_ID", "KEYWORD"}))
public class Setting {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="KEYWORD", nullable = false)
    private String keyword;

    @Column(name="VALUE", nullable = false)
    private String value;

    @JoinColumn(name="USER_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE_TIME", nullable = false)
    private Date createdDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_DATE_TIME", nullable = false)
    private Date updateDateTime;
}

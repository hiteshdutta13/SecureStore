package com.secure.store.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="TOKENS")
public class Token {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @JoinColumn(name="USERS_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name="TOKEN", unique = true, nullable = false)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXPIRE_DATE_TIME", nullable = false)
    private Date expireDateTime;
}

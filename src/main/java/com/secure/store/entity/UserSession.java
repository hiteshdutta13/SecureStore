package com.secure.store.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="USER_SESSION")
public class UserSession {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @JoinColumn(name="USER_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name="TOKEN", unique = true, nullable = false)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LOGIN_DATE_TIME", nullable = false)
    private Date loginDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LOGOUT_DATE_TIME")
    private Date logoutDateTime;
}

package com.secure.store.entity;

import com.secure.store.entity.util.RequestType;
import com.secure.store.entity.util.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="USER_REQUEST")
public class UserRequest {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @JoinColumn(name="USER_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name="TYPE", nullable = false)
    private RequestType type;

    @Column(name="ACCESS_CODE", nullable = false)
    private String code;

    @Column(name="ACCESS_TOKEN", nullable = false)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE_TIME", nullable = false)
    private Date createdDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS", nullable = false)
    private Status status;
}

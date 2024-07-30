package com.secure.store.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="AUDIT_LOG")
public class AuditLog {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @JoinColumn(name="USER_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name="DOCUMENT_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private File file;

    @Column(name="ACTION", nullable = false)
    private String action;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE_TIME", nullable = false)
    private Date createdDateTime;
}

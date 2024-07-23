package com.secure.store.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="DOCUMENTS")
public class Document {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @JoinColumn(name="USERS_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name="FOLDERS_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Folder folder;

    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name="PATH", nullable = false)
    private String path;

    @Column(name="SIZE", nullable = false)
    private Long size;

    @Column(name="CONTENT_TYPE", nullable = false)
    private String contentType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE_TIME", nullable = false)
    private Date createdDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_DATE_TIME", nullable = false)
    private Date updateDateTime;
}

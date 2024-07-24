package com.secure.store.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="FOLDER")
public class Folder {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @JoinColumn(name="USER_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name="PARENT_FOLDER_ID", referencedColumnName="ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Folder parent;

    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name="PATH", nullable = false, columnDefinition = "TEXT")
    private String path;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE_TIME", nullable = false)
    private Date createdDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_DATE_TIME", nullable = false)
    private Date updateDateTime;
}

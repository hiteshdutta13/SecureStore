package com.secure.store.entity;

import com.secure.store.entity.util.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="FILE")
public class File {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @JoinColumn(name="USER_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name="FOLDER_ID", referencedColumnName="ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Folder folder;

    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name="ORIGINAL_NAME", nullable = false)
    private String originalName;

    @Column(name="PATH", nullable = false)
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS", nullable = false)
    private Status status;

    @Column(name="SIZE", nullable = false)
    private Long size;

    @Column(name="CONTENT_TYPE", nullable = false)
    private String contentType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE_TIME", nullable = false)
    private Date createdDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_DATE_TIME", nullable = false)
    private Date updatedDateTime;
}

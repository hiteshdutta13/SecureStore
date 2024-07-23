package com.secure.store.entity;

import com.secure.store.entity.util.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="DOCUMENT_VERSION")
public class DocumentVersion {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @JoinColumn(name="DOCUMENT_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Document document;

    @Column(name="VERSION_NUMBER", nullable = false)
    private Integer versionNumber;

    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name="PATH", nullable = false)
    private String path;

    @Column(name="ORIGINAL_NAME", nullable = false)
    private String originalName;

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
}

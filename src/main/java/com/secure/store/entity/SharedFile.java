package com.secure.store.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="SHARED_FILE")
public class SharedFile {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @JoinColumn(name="FILE_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private File file;

    @JoinColumn(name="SHARED_TO_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User sharedTo;

    @JoinColumn(name="SHARED_BY_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User sharedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="SHARED_DATE_TIME", nullable = false)
    private Date sharedDateTime;
}

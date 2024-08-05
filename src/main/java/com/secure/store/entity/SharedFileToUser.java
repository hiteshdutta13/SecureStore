package com.secure.store.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="SHARED_FILE_TO_USER", uniqueConstraints = @UniqueConstraint(columnNames= {"SHARED_FILE_ID", "USER_ID"}))
public class SharedFileToUser {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @JoinColumn(name="SHARED_FILE_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private SharedFile sharedFile;

    @JoinColumn(name="USER_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="SHARED_DATE_TIME", nullable = false)
    private Date sharedDateTime;
}

package com.secure.store.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="SHARED_FILE")
public class SharedFile {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @JoinColumn(name="FILE_ID", referencedColumnName="ID", nullable = false, unique = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private File file;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sharedFile", fetch=FetchType.LAZY, orphanRemoval = true)
    private List<SharedFileToUser> sharedFileToUsers;

    @JoinColumn(name="SHARED_BY_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User sharedBy;
}

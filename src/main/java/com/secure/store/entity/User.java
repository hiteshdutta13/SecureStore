package com.secure.store.entity;

import com.secure.store.entity.util.Gender;
import com.secure.store.entity.util.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="USER")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name="EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name="PASSWORD", nullable = false)
    private String password;

    @Column(name="FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name="LAST_NAME", nullable = false)
    private String lastName;

    @Column(name="MOBILE_NO")
    private String mobileNo;

    @Temporal(TemporalType.DATE)
    @Column(name="DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name="GENDER")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS", nullable = false)
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE_TIME", nullable = false)
    private Date createdDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_DATE_TIME", nullable = false)
    private Date updateDateTime;
}

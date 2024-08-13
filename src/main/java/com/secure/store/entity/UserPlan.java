package com.secure.store.entity;

import com.secure.store.entity.util.Status;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="USER_PLAN", uniqueConstraints = @UniqueConstraint(columnNames= {"PLAN_ID", "USER_ID"}))
public class UserPlan {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @JoinColumn(name="PLAN_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Plan plan;

    @JoinColumn(name="USER_ID", referencedColumnName="ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS", nullable = false)
    private Status status;
}

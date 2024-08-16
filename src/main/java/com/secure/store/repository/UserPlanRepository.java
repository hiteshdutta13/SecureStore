package com.secure.store.repository;

import com.secure.store.entity.Plan;
import com.secure.store.entity.UserPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPlanRepository extends JpaRepository<UserPlan, Long> {

    @Query("SELECT up FROM UserPlan up WHERE up.user.id = :userId")
    Optional<UserPlan> findBy(@Param("userId") Long userId);
}

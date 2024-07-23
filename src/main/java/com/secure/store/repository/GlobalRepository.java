package com.secure.store.repository;

import com.secure.store.entity.Global;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GlobalRepository extends JpaRepository<Global, Long> {
    @Query("SELECT g FROM Global g WHERE g.keyword = :keyword")
    Optional<Global> findBy(@Param("keyword") String keyword);
}

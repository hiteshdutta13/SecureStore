package com.secure.store.repository;

import com.secure.store.entity.File;
import com.secure.store.entity.util.Status;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    @Query("SELECT d FROM File d WHERE d.user.id = :userId AND d.folder IS NULL AND d.status = :status")
    List<File> findBy(@Param("userId") Long userId, @Param("status") Status status, Sort sort);

    @Query("SELECT d FROM File d WHERE d.user.id = :userId AND d.folder.id = :folderId AND d.status = :status")
    List<File> findBy(@Param("userId") Long userId, @Param("folderId") Long folderId, @Param("status") Status status, Sort sort);

    @Query("SELECT d FROM File d WHERE d.folder.id = :folderId AND d.status = :status")
    List<File> findByFolder(@Param("folderId") Long folderId, @Param("status") Status status, Sort sort);

    @Query("SELECT d FROM File d WHERE d.user.id = :userId AND d.status = :status")
    List<File> findByUser(@Param("userId") Long userId, @Param("status") Status status, Sort sort);
}

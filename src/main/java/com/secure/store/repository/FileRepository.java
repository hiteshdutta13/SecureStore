package com.secure.store.repository;

import com.secure.store.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    @Query("SELECT d FROM File d WHERE d.user.id = :userId AND d.folder IS NULL")
    List<File> findBy(@Param("userId") Long userId);

    @Query("SELECT d FROM File d WHERE d.user.id = :userId AND d.folder.id = :folderId")
    List<File> findBy(@Param("userId") Long userId, @Param("folderId") Long folderId);

    @Query("SELECT d FROM File d WHERE d.folder.id = :folderId")
    List<File> findByFolder(@Param("folderId") Long folderId);
}

package com.secure.store.repository;

import com.secure.store.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("SELECT d FROM Document d WHERE d.user.id = :userId AND d.folder IS NULL")
    List<Document> findBy(@Param("userId") Long userId);

    @Query("SELECT d FROM Document d WHERE d.user.id = :userId AND d.folder.id = :folderId")
    List<Document> findBy(@Param("userId") Long userId, @Param("folderId") Long folderId);
}

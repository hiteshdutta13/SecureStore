package com.secure.store.repository;

import com.secure.store.entity.SharedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SharedFileRepository extends JpaRepository<SharedFile, Long> {
    @Query("SELECT ds FROM SharedFile ds WHERE ds.sharedBy.id = :sharedById")
    List<SharedFile> findBySharedBY(@Param("sharedById") Long sharedById);

    @Query("SELECT ds FROM SharedFile ds WHERE ds.file.id = :fileId")
    Optional<SharedFile> findBy(@Param("fileId") Long fileId);
}

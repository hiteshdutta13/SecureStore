package com.secure.store.repository;

import com.secure.store.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    @Query("SELECT f FROM Folder f WHERE f.user.id = :userId AND f.parent IS NULL")
    List<Folder> findBy(@Param("userId") Long userId);

    @Query("SELECT f FROM Folder f WHERE f.user.id = :userId AND f.parent.id = :parentId")
    List<Folder> findBy(@Param("userId") Long userId, @Param("parentId") Long parentId);

    @Query("SELECT f FROM Folder f WHERE f.parent.id = :parentId")
    List<Folder> findByParent(@Param("parentId") Long parentId);
}

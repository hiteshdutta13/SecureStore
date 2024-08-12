package com.secure.store.repository;

import com.secure.store.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    @Query("SELECT f FROM Folder f WHERE f.user.id = :userId AND f.parent IS NULL")
    List<Folder> findBy(@Param("userId") Long userId);

    @Query("SELECT f FROM Folder f WHERE f.user.id = :userId AND f.parent IS NULL AND f.name = :name")
    Optional<Folder> findBy(@Param("userId") Long userId, @Param("name") String name);
    @Query("SELECT f FROM Folder f WHERE f.user.id = :userId AND f.parent.id = :parentId AND f.name = :name")
    Optional<Folder> findBy(@Param("userId") Long userId, @Param("parentId") Long parentId, @Param("name") String name);

    @Query("SELECT f FROM Folder f WHERE f.user.id = :userId AND f.parent.id = :parentId")
    List<Folder> findBy(@Param("userId") Long userId, @Param("parentId") Long parentId);

    @Query("SELECT f FROM Folder f WHERE f.parent.id = :parentId")
    List<Folder> findByParent(@Param("parentId") Long parentId);
}

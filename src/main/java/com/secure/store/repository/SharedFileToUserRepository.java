package com.secure.store.repository;

import com.secure.store.entity.SharedFileToUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SharedFileToUserRepository extends JpaRepository<SharedFileToUser, Long> {

    @Query("SELECT sftu FROM SharedFileToUser sftu WHERE sftu.userId.id = :sharedToId AND sftu.sharedFile.sharedBy.id = :sharedById")
    List<SharedFileToUser> findBy(@Param("sharedToId") Long sharedToId, @Param("sharedById") Long sharedById);

    @Query("SELECT sftu FROM SharedFileToUser sftu WHERE sftu.userId.id = :sharedToId")
    List<SharedFileToUser> findBy(@Param("sharedToId") Long sharedToId);

    @Query("SELECT sftu FROM SharedFileToUser sftu WHERE sftu.userId.id = :sharedToId AND sftu.sharedFile.sharedBy.id = :sharedById AND sftu.sharedFile.file.id = :fileId")
    Optional<SharedFileToUser> findBy(@Param("sharedToId") Long sharedToId, @Param("sharedById") Long sharedById,  @Param("fileId") Long fileId);
}

package com.secure.store.repository;

import com.secure.store.entity.UserRequest;
import com.secure.store.entity.util.RequestType;
import com.secure.store.entity.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequest, Long> {

    @Query("SELECT ur FROM UserRequest ur WHERE ur.user.id = :userId AND ur.type = :type AND ur.status = :status")
    Optional<UserRequest> findBy(@Param("userId") Long userId, @Param("type") RequestType type, @Param("status") Status status);

    @Query("SELECT ur FROM UserRequest ur WHERE ur.token = :token AND ur.type = :type AND ur.status = :status")
    Optional<UserRequest> findBy(@Param("token") String token, @Param("type") RequestType type, @Param("status") Status status);
}

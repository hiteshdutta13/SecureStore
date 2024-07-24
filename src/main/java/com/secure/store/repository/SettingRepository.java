package com.secure.store.repository;

import com.secure.store.entity.Global;
import com.secure.store.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {

    @Query("SELECT s FROM Setting s WHERE s.keyword = :keyword AND s.user.id = :userId")
    Optional<Setting> findBy(@Param("keyword") String keyword, @Param("userId") Long userId);
}

package com.secure.store.repository;

import com.secure.store.entity.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentVersionsRepository extends JpaRepository<DocumentVersion, Long> {
}

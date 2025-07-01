package com.revoktek.motivus.persistence.repositories;

import com.revoktek.motivus.persistence.entities.LogAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogAuditoria, Long> {
}
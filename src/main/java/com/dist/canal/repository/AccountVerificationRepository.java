package com.dist.canal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dist.canal.entity.AccountVerification;

public interface AccountVerificationRepository extends JpaRepository<AccountVerification,Long> {
}

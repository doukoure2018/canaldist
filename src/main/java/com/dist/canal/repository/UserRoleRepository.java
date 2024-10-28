package com.dist.canal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dist.canal.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {

    UserRole findUserRolesByUserId(Long userId);
}

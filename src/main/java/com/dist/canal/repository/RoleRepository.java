package com.dist.canal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.dist.canal.entity.Role;
import com.dist.canal.payload.RoleDto;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    @Query("SELECT new com.dist.canal.payload.RoleDto(r.id, r.name, r.permission) " +
            "FROM Role r " +
            "JOIN UserRole ur ON ur.role.id = r.id " +
            "JOIN User u ON u.id = ur.user.id " +
            "WHERE u.id = :userId")
    RoleDto getRoleByUserId(@Param("userId") Long userId);

}

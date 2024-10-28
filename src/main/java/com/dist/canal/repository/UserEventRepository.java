package com.dist.canal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.dist.canal.entity.UserEvent;
import com.dist.canal.payload.UserEventDto;

import java.util.List;

public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

    @Query("SELECT new com.dist.canal.payload.UserEventDto(uev.id, uev.device, uev.ipAddress, ev.type, ev.description, uev.createdAt) " +
            "FROM UserEvent uev JOIN uev.event ev JOIN uev.user u " +
            "WHERE u.id = :userId " +
            "ORDER BY uev.createdAt DESC LIMIT 10")
    List<UserEventDto> findRecentEventsByUserId(@Param("userId") Long userId);

}
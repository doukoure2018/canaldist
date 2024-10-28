package com.dist.canal.service;

import com.dist.canal.enumeration.EventType;
import com.dist.canal.payload.UserEventDto;

import java.time.LocalDateTime;
import java.util.List;


public interface UserEventService {
    List<UserEventDto> getEventsByUserId(Long userId);
    void addUserEvent(String email, EventType eventType, String device, String ipAddress, LocalDateTime createdAt);
}

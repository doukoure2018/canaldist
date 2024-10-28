package com.dist.canal.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.dist.canal.entity.Event;
import com.dist.canal.entity.User;
import com.dist.canal.entity.UserEvent;
import com.dist.canal.enumeration.EventType;
import com.dist.canal.payload.UserEventDto;
import com.dist.canal.repository.EventRepository;
import com.dist.canal.repository.UserEventRepository;
import com.dist.canal.repository.UserRepository;
import com.dist.canal.service.UserEventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserEventServiceImpl implements UserEventService {

    private final UserEventRepository userEventsRepository;
    private final UserRepository userRepository;
    private final EventRepository eventsRepository;

    @Override
    public List<UserEventDto> getEventsByUserId(Long userId) {
        return userEventsRepository.findRecentEventsByUserId(userId);
    }

    @Override
    public void addUserEvent(String email, EventType eventType, String device, String ipAddress, LocalDateTime createdAt) {
        // get User by email
        Optional<User> user = userRepository.findByEmail(email);
        // get Event by eventType
        Optional<Event> events = eventsRepository.findByType(eventType.toString());
        UserEvent userEvents = new UserEvent();
        userEvents.setUser(user.get());
        userEvents.setEvent(events.get());
        userEvents.setDevice(device);
        userEvents.setIpAddress(ipAddress);
        userEvents.setCreatedAt(LocalDateTime.now());
        userEventsRepository.save(userEvents);
    }
}

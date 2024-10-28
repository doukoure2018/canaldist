package com.dist.canal.service.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.dist.canal.entity.Event;
import com.dist.canal.enumeration.EventType;
import com.dist.canal.repository.EventRepository;
import com.dist.canal.service.EventService;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventsRepository;
    private final ModelMapper mapper;
    @Override
    public void addEvents(EventType eventType) {
        Event events= mapper.map(eventType,Event.class);
        eventsRepository.save(events);
    }
}

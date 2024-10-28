package com.dist.canal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dist.canal.entity.Event;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event,Long> {

    Optional<Event> findByType(String eventType);
}

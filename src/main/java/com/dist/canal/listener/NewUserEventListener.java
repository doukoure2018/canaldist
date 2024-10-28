package com.dist.canal.listener;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.dist.canal.event.NewUserEvent;
import com.dist.canal.service.UserEventService;

import java.time.LocalDateTime;

import static com.dist.canal.utils.RequestUtils.getDevice;
import static com.dist.canal.utils.RequestUtils.getIpAddress;

@Component
@RequiredArgsConstructor
public class NewUserEventListener {
    private final UserEventService eventService;
    private final HttpServletRequest request;

    @EventListener
    public void onNewUserEvent(NewUserEvent event) {
        eventService.addUserEvent(event.getEmail(), event.getType(), getDevice(request), getIpAddress(request), LocalDateTime.now());
    }
}

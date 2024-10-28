package com.dist.canal.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import com.dist.canal.enumeration.EventType;

@Getter
@Setter
public class NewUserEvent extends ApplicationEvent {
    private EventType type;
    private String email;

    public NewUserEvent(String email, EventType type) {
        super(email);
        this.type = type;
        this.email = email;
    }
}

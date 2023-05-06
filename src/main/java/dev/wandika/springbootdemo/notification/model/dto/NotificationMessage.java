package dev.wandika.springbootdemo.notification.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class NotificationMessage extends Notification implements Serializable {
    String action;

    public NotificationMessage() {

    }

    public NotificationMessage(Notification p, String action) {
        this.setId(p.getId());
        this.setMessage(p.getMessage());
        this.action = action;
    }
}

package dev.wandika.springbootdemo.notification.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notifications")
public class NotificationDocument {
    @Id
    private int id;
    private String message;
    private String action;
}
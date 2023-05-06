package dev.wandika.springbootdemo.notification.service;

import dev.wandika.springbootdemo.notification.kafka.producer.NotificationProducer;
import dev.wandika.springbootdemo.notification.model.NotificationMapper;
import dev.wandika.springbootdemo.notification.model.dto.Notification;
import dev.wandika.springbootdemo.notification.model.dto.NotificationMessage;
import dev.wandika.springbootdemo.notification.repository.NotificationMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {
    @Autowired
    private NotificationProducer kafkaProducer;

    @Autowired
    private NotificationMongoRepository notificationMongoRepository;

    @Autowired
    private NotificationMapper mapper;

    public void sendNotification(String id, String message) {
        Notification notification = new Notification();
        notification.setId(id);
        notification.setMessage(message);
        kafkaProducer.send(new NotificationMessage(notification, "create"));
    }

    public void processNotification(NotificationMessage message) {
        //for now only save the notif to db
        notificationMongoRepository.save(mapper.convertToEntity(message));
        log.info("Successfully save {}", message);
    }
}

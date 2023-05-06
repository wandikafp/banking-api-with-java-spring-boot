package dev.wandika.springbootdemo.notification.kafka.consumer;

import dev.wandika.springbootdemo.notification.model.dto.NotificationMessage;
import dev.wandika.springbootdemo.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationConsumer {
    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "banking-notification", containerFactory = "kafkaListenerContainerFactory")
    public void newProductListener(NotificationMessage notification) {
        log.info("Get message from notification : " + notification.toString());
        notificationService.processNotification(notification);
    }
}

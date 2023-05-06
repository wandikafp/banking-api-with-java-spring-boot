package dev.wandika.springbootdemo.notification.kafka.producer;

import dev.wandika.springbootdemo.notification.model.dto.Notification;
import dev.wandika.springbootdemo.notification.model.dto.NotificationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.Serializable;

@Slf4j
@Component
public class NotificationProducer {
    final String TOPIC = "banking-notification";

    @Autowired
    private KafkaTemplate<String, Serializable> kafkaTemplate;

    public void send(NotificationMessage message) {
        ListenableFuture<SendResult<String, Serializable>> future = kafkaTemplate.send(TOPIC, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Serializable>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message = {} dut to: {}", message.toString(), ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Serializable> result) {
                log.info("Message sent successfully with offset = {}", result.getRecordMetadata().offset());
            }
        });
    }
}

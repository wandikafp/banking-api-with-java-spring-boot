package dev.wandika.springbootdemo.notification.repository;

import dev.wandika.springbootdemo.notification.model.entity.NotificationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationMongoRepository extends MongoRepository<NotificationDocument,Integer> {
}

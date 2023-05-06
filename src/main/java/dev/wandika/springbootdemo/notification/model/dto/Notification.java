package dev.wandika.springbootdemo.notification.model.dto;

import lombok.*;

import java.io.Serializable;

@Data
public class Notification implements Serializable {
    private String id;
    private String message;
}
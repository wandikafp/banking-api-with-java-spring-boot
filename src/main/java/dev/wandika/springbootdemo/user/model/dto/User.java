package dev.wandika.springbootdemo.user.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
    private String identificationNumber;
}

package dev.wandika.springbootdemo.base.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends DemoBaseException{
    public EntityNotFoundException() {
        super("Requested entity not present in the DB.", String.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    public EntityNotFoundException (String message) {
        super(message, String.valueOf(HttpStatus.NOT_FOUND.value()));
    }
}

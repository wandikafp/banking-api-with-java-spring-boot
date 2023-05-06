package dev.wandika.springbootdemo.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DemoBaseException extends RuntimeException {
    private String code;
    private String message;

    public DemoBaseException(String message) {
        super(message);
    }
}

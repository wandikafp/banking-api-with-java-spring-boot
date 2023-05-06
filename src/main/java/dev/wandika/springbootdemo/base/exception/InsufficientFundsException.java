package dev.wandika.springbootdemo.base.exception;

public class InsufficientFundsException extends DemoBaseException {
    public InsufficientFundsException(String message, String code) {
        super(message, code);
    }
}

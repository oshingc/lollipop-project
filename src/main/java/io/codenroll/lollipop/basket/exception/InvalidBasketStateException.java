package io.codenroll.lollipop.basket.exception;

public class InvalidBasketStateException extends RuntimeException {
    public InvalidBasketStateException(String message) {
        super(message);
    }
}
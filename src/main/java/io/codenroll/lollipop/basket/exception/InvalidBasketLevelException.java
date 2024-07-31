package io.codenroll.lollipop.basket.exception;

public class InvalidBasketLevelException extends RuntimeException {

    public InvalidBasketLevelException(String basketId) {
        super("Basket not found with id: " + basketId);
    }
}

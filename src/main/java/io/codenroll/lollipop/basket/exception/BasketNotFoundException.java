package io.codenroll.lollipop.basket.exception;

public class BasketNotFoundException extends RuntimeException {
    public BasketNotFoundException(String basketId) {
        super("Basket not found with id: " + basketId);
    }
}

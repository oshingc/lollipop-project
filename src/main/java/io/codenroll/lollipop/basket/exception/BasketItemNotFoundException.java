package io.codenroll.lollipop.basket.exception;

public class BasketItemNotFoundException extends RuntimeException {
    public BasketItemNotFoundException(String basketItemId) {
        super("Basket not found with id: " + basketItemId);
    }
}

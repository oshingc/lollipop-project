package io.codenroll.lollipop.basket.model;

import io.codenroll.lollipop.basket.exception.BasketItemNotFoundException;

import java.util.List;
import java.util.Optional;

public class BasketItem {
    private String id;
    private String productId; /** Agregado para la relación con producto**/
    private String name;
    private Double price;
    private Integer quantity;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BasketItem (String name,Integer quantity, Double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    private Optional<BasketItem> findBasketItem(List<BasketItem> basketItems, String basketItemId) {
        return basketItems.stream()
                .filter(item -> item.getId().equals(basketItemId))
                .findFirst();
    }

    public void updateBasketItem(List<BasketItem> basketItems, BasketItem newBasketItem) {
        BasketItem existingItem = findBasketItem(basketItems, newBasketItem.getId())
                .orElseThrow(() -> new BasketItemNotFoundException(newBasketItem.getId()));

        existingItem.setQuantity(newBasketItem.getQuantity());
        //calculateTotal();
        //calculateTotalByLevel();
    }

    public void removeBasketItem(List<BasketItem> basketItems, String basketItemId) {
        basketItems.removeIf(item -> item.getId().equals(basketItemId));
        //this.calculateTotal();
    }

    /*@Override
    public String toString() {
        return "BasketItem{" +
                "id='" + id + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }*/
}

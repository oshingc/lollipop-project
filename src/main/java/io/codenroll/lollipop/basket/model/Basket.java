package io.codenroll.lollipop.basket.model;

import java.util.*;

/**
 * Tenemos un Basket el cual se compone de una lista de
 * Items los cuales hacen referencia al producto que se quiere comprar,
 * la cantidad y su precio
 **/
public class Basket {
    private String id;
    // private final List<Product> products = new ArrayList<>();
    private final List<BasketItem> basketItems = new ArrayList<>();

    private double total;
    private BasketStatus status;
    private final BasketLevel level;

    public Basket(BasketLevel level) {
        this.id = UUID.randomUUID().toString();
        this.status = BasketStatus.NEW;
        this.total = 0.0d;
        this.level = level;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    // Now getProducts returns a list of products with encapsulation
    public List<BasketItem> getBasketItems() {
        return new ArrayList<>(basketItems);
    }

    public double getTotal() {
        return total;
    }

    public BasketStatus getStatus() {
        return status;
    }

    public BasketLevel getLevel() {
        return level;
    }

    public void addBasketItem(BasketItem basketItem) {
        basketItems.add(basketItem);
        this.calculateTotal();
    }

    public void deleteBasketItem(BasketItem basketItem) {
        basketItems.remove(basketItem);
        this.calculateTotal();
    }

    public void deleteBasketItemById(String basketItemId) {
        basketItems.removeIf(b->b.getId().equals(basketItemId));
        this.calculateTotal();
    }

    /** Max 100 products **/
    public void calculateTotal() {
        for (BasketItem pr : this.getBasketItems()) {
            this.total += pr.getPrice() * pr.getQuantity();
        }
    }

    public void calculateTotalByLevel() {
        try {
            if (this.total < 0) {
                throw new IllegalArgumentException("Only Positive Values are Valid");
            }
            this.level.calculateTotal(this.total);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setStatus(BasketStatus status) {
        this.status = status;
    }

    public void updateTotal(double newValue) {
        this.total += newValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Basket basket = (Basket) obj;
        return Double.compare(basket.total, total) == 0 &&
                Objects.equals(basketItems, basket.basketItems) &&
                status == basket.status &&
                level == basket.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addItem(BasketItem basketItem) {
        this.getBasketItems().add(basketItem);
    }

}

package io.codenroll.lollipop.basket.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**Tenemos un Basket el cual se compone de una lista de
 * Items los cuales hacen referencia al producto que se quiere comprar,
 * la cantidad y su precio**/
public class Basket {
    private final String id;
    //private final List<Product> products = new ArrayList<>();
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
        //updateTotal(product.getPrice());
        this.calculateTotal();
    }

    /**Max 100 products**/
    public void calculateTotal() {
        for(BasketItem pr: this.getBasketItems()) {
            this.total += pr.getPrice() * pr.getCantidad();
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

    /** Removed to make id inmutable **/
    /*
    public void setId(String id) {
        this.id = id;
    }*/

    /** Removed because we don't change value directly outside the entity **/
    /*
    public void setLevel(BasketLevel level) {
        this.level = level;
    }*/

    /*
    public void setTotal(double total) {
        this.total = total;
    }*/

}

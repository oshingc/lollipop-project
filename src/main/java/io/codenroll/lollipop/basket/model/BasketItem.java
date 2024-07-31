package io.codenroll.lollipop.basket.model;

public class BasketItem {
    private String id;
    private String productId; /** Agregado para la relación con producto**/
    private String name;
    private Double price;
    private Integer cantidad;
    /** I wonder if we can define English as general language in the code
     * Other way the mix can lead to confusion and inconsistency if not managed properly
     * **/

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}

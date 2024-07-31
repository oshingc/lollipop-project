package io.codenroll.lollipop.basket.service;

import io.codenroll.lollipop.basket.model.Basket;
import io.codenroll.lollipop.basket.model.BasketLevel;
import io.codenroll.lollipop.basket.model.BasketStatus;
import io.codenroll.lollipop.basket.repository.BasketRepository;
import io.codenroll.lollipop.product.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketServiceOld {
    //@Autowired
    private final BasketRepository basketRepository;

    public BasketServiceOld(BasketRepository basketRepository){
        this.basketRepository = basketRepository;
    }

    public Basket create(BasketLevel level) {
        //String id = UUID.randomUUID().toString();
        //Basket basket = new Basket();

        Basket basket = new Basket(level);
        /*basket.setStatus(BasketStatus.NEW);
        basket.setId(id);
        basket.setTotal(0.0d);
        basket.setLevel(level);*/

        basketRepository.save(basket);
        return basket;
    }

    public Basket retrieveBasket(String id) {
        return basketRepository.find(id);
    }

    public void addProduct(String id, Product product) {
        Basket basket = basketRepository.find(id);
        if (basket != null && basket.getProducts() != null) {
            basket.getProducts().add(product);

            double total = 0.0d;
            for (Product pr : basket.getProducts()) {
                total += pr.getPrice() * pr.getCantidad();
            }

            /** Calling calculateTotal**/
            //basket.setTotal(total);
            basket.calculateTotal();
            basket.setStatus(BasketStatus.IN_PROGRESS);

            basketRepository.update(basket);
        }
    }

    public void updateProduct(String id, Product product) {

        Basket basket = basketRepository.find(id);
        List<Product> products = basket.getProducts();

        int i = -1;
        for (Product prd: products) {
            i++;
            if (prd.getId().equals(product.getId())) {
                products.set(i, product);
                basket.addProduct(product);
            }
        }

        /** Refactoring the logic calculate total**/
        /*
        double total = 0.0d;
        for(Product pr: basket.getProducts()) {
            total += pr.getPrice() * pr.getCantidad();
        }*/

        basket.calculateTotal();
        basketRepository.update(basket);
    }

    public void deleteProduct(String id, String pId) {
        Basket basket = basketRepository.find(id);
        List<Product> products = basket.getProducts();

        int i = -1;
        for (Product prd: products) {
            i++;
            if (prd.getId().equals(pId)) {
                products.remove(i);
                break;
            }
        }

        double total = 0.0d;
        for(Product pr: basket.getProducts()) {
            total += pr.getPrice() * pr.getCantidad();
        }

        /** Call to calculate total **/
        basket.calculateTotal();
        basketRepository.update(basket);
    }


    public void complete(String id) {
        Basket basket = basketRepository.find(id);
        basket.setStatus(BasketStatus.COMPLETED);
        basket.calculateTotal();
        //basket.setTotal(basket.calculateTotal());

        basket.calculateTotalByLevel();
        /*switch (basket.getLevel()) {
            case NORMAL -> basket.setTotal(basket.getTotal());
            case SILVER -> basket.setTotal(basket.getTotal() * 0.9);
            case GOLD -> basket.setTotal(basket.getTotal() * 0.95);
        }*/
        basketRepository.update(basket);
    }
}

package io.codenroll.lollipop.basket.repository;

import io.codenroll.lollipop.basket.exception.BasketNotFoundException;
import io.codenroll.lollipop.basket.model.Basket;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BasketRepositoryImpl implements BasketRepository {
    private Map<String, Basket> basketMap;

    public BasketRepositoryImpl() {
        this.basketMap = new HashMap<>();
    }

    @Override
    public Optional<Basket> findById(String id) {
        return Optional.ofNullable(basketMap.get(id));
    }

    @Override
    public Basket save(Basket basket) {
        basketMap.put(basket.getId(), basket);
        return basket;
    }
    @Override
    public Basket update(Basket basket) {
        if (basketMap.containsKey(basket.getId())) {
            basketMap.put(basket.getId(), basket);
            return basket;
        }
        throw new BasketNotFoundException(basket.getId());
    }

    @Override
    public void deleteById(String id) {
        basketMap.remove(id);
    }

}

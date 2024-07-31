package io.codenroll.lollipop.basket.repository;

import io.codenroll.lollipop.basket.model.Basket;

import java.util.Optional;

public interface BasketRepository {
    Optional<Basket> findById(String id);

    Basket save(Basket basket);

    Basket update(Basket basket);

    void deleteById(String id);
}

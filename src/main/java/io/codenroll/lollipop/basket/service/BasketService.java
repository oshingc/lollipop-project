package io.codenroll.lollipop.basket.service;

import io.codenroll.lollipop.basket.exception.BasketItemNotFoundException;
import io.codenroll.lollipop.basket.exception.BasketNotFoundException;
import io.codenroll.lollipop.basket.model.Basket;
import io.codenroll.lollipop.basket.model.BasketItem;
import io.codenroll.lollipop.basket.model.BasketLevel;
import io.codenroll.lollipop.basket.model.BasketStatus;
import io.codenroll.lollipop.basket.repository.BasketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService {
    // @Autowired
    private final BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public Basket create(BasketLevel level) {
        Basket basket = new Basket(level);// Removed set status, id, total and level - Added custom constructor **/
        basketRepository.save(basket);
        return basket;
    }

    public Optional<Basket> retrieveBasket(String id) {
        Optional<Basket> optionalBasket = basketRepository.findById(id);
        return Optional.ofNullable(optionalBasket.orElseThrow(() -> new BasketNotFoundException(id)));
    }

    public Basket addBasketItem(String basketId, BasketItem basketItem) {
        return basketRepository.findById(basketId)
                .map(basket -> {
                    basket.addBasketItem(basketItem);
                    basket.calculateTotal();
                    basket.setStatus(BasketStatus.IN_PROGRESS);
                    return basketRepository.update(basket);
                })
                .orElseThrow(() -> new BasketNotFoundException(basketId));
    }

    public Basket updateBasketItem(String basketId, BasketItem basketItem) {
        return basketRepository.findById(basketId)
                .map(basket -> {
                    BasketItem existingItem = basket.getBasketItems().stream()
                            .filter(item -> item.getId().equals(basketItem.getId()))
                            .findFirst()
                            .orElseThrow(() -> new BasketItemNotFoundException(basketItem.getId()));

                    existingItem.setQuantity(existingItem.getQuantity() + basketItem.getQuantity());

                    basket.calculateTotal();
                    basket.calculateTotalByLevel();
                    return basketRepository.update(basket);
                })
                .orElseThrow(() -> new BasketNotFoundException(basketId));
    }
    public void deleteBasketItem(String basketId, String basketItemId) {

        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new BasketNotFoundException(basketId));

        basket.deleteBasketItemById(basketItemId);
        basketRepository.update(basket);
    }

    public void complete(String id) {
        Optional<Basket> optionalBasket = basketRepository.findById(id);
        if (optionalBasket.isPresent()) {
            Basket basket = optionalBasket.get();
            // Update basket status
            basket.setStatus(BasketStatus.COMPLETED);

            // Calculate total
            basket.calculateTotal();
            // Calculate total by level
            basket.calculateTotalByLevel();

            // Save updated basket
            basketRepository.update(basket);
        } else {
            throw new BasketNotFoundException(id);
        }
    }
}

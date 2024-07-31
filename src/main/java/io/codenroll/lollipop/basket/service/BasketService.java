package io.codenroll.lollipop.basket.service;

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
    //@Autowired
    private final BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository){
        this.basketRepository = basketRepository;
    }

    public Basket create(BasketLevel level) {
        Basket basket = new Basket(level);// Removed set status, id, total and level - Added custom constructor **/
        basketRepository.save(basket);
        return basket;
    }

    public Basket retrieveBasket(String id) {
        Optional<Basket> optionalBasket = basketRepository.findById(id);
        return optionalBasket.orElseThrow(() -> new BasketNotFoundException(id));
    }

    public void addBasketItem(String id, BasketItem basketItem) {
        Optional<Basket> optionalBasket = Optional.ofNullable(basketRepository.findById(id).orElseThrow(() -> new BasketNotFoundException(id)));
        if (optionalBasket.isPresent()) {
            Basket basket = optionalBasket.get();
            if (basket.getBasketItems() != null) {
                basket.getBasketItems().add(basketItem);
                basket.calculateTotal();
                basket.setStatus(BasketStatus.IN_PROGRESS);
                basketRepository.update(basket);
            }
        }
    }

    public void updateBasketItem(String id, BasketItem basketItem) {

        Optional<Basket> optionalBasket = basketRepository.findById(id);//improve with map
        if (optionalBasket.isPresent()) {
            Basket basket = optionalBasket.get();

            List<BasketItem> basketItems = basket.getBasketItems();

            int i = -1;//equals y hashcode
            for (BasketItem basketItemTemp : basketItems) {
                i++;
                if (basketItem.getId().equals(basketItemTemp.getId())) {
                    basketItemTemp.setCantidad(basketItemTemp.getCantidad() + basketItem.getCantidad());
                    basketItems.set(i, basketItem);
                }
            }

            /** Removed for lop for calculate - Added call to calculate total **/
            basket.calculateTotal();
            basketRepository.update(basket);
        } else {
            throw new BasketNotFoundException(id);
        }
    }

    public void deleteBasketItem(String id, String basketItemId) {
        Optional<Basket> optionalBasket = basketRepository.findById(id);
        if (optionalBasket.isPresent()) {
            Basket basket = optionalBasket.get();
            List<BasketItem> basketItems = basket.getBasketItems();
            int i = -1;//
            for (BasketItem basketItem: basketItems) {
                i++;
                if (basketItem.getId().equals(basketItemId)) {
                    basketItems.remove(i);
                    break;
                }
            }
            /** Removed for lop for calculate - Added call to calculate total **/
            basket.calculateTotal();
            basketRepository.update(basket);
        } else {
            throw new BasketNotFoundException(id);
        }

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

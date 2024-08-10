package io.codenroll.lollipop.basket.controller;

import io.codenroll.lollipop.basket.controller.dto.CreateBasketDto;
import io.codenroll.lollipop.basket.exception.BasketNotFoundException;
import io.codenroll.lollipop.basket.exception.InvalidBasketLevelException;
import io.codenroll.lollipop.basket.exception.InvalidBasketStateException;
import io.codenroll.lollipop.basket.model.Basket;
import io.codenroll.lollipop.basket.model.BasketItem;
import io.codenroll.lollipop.basket.model.BasketStatus;
import io.codenroll.lollipop.basket.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/baskets")
public class BasketController {
    @Autowired
    private BasketService basketService;

    @PostMapping
    public ResponseEntity<Basket> createBasket(@RequestBody CreateBasketDto dto) {
        try {
            Basket createdBasket = basketService.create(dto.level());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBasket);
        } catch (InvalidBasketLevelException ex) {
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{basketId}/basketItem")
    @ResponseBody
    public ResponseEntity<Void> addBasketItemToBasket(@PathVariable String basketId, @RequestBody BasketItem basketItem) {
        try {
            basketService.addBasketItem(basketId, basketItem);
            return ResponseEntity.ok().build();
        } catch (BasketNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /** Removed nid, no needed, changed PutMapping for PostMapping**/
    @PostMapping("/{basketId}/products")
    public ResponseEntity<Basket> updateBasketItem(@PathVariable String basketId, @RequestBody BasketItem basketItem) {
        try {
            basketService.updateBasketItem(basketId, basketItem);
            return ResponseEntity.ok().build();
        } catch (BasketNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /** Added missing basketId, changed method name, changed param name **/
    @PostMapping("/{basketId}/basket-items/{basketItemId}")
    public ResponseEntity<Void> deleteBasketItem(@PathVariable String basketId, @PathVariable String basketItemId) {
        try {
            basketService.deleteBasketItem(basketId, basketItemId);
            return ResponseEntity.ok().build();
        } catch (BasketNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{basketId}/complete")
    public ResponseEntity<Void> completeBasket(@PathVariable String basketId) {
        try {
            Optional<Basket> basket = basketService.retrieveBasket(basketId);
            if (basket.isPresent() && basket.get().getStatus() != BasketStatus.IN_PROGRESS) {
                throw new InvalidBasketStateException("Basket must be IN PROGRESS to complete.");
            }
            basketService.complete(basketId);
            return ResponseEntity.ok().build();
        } catch (BasketNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{basketId}")
    public ResponseEntity<Optional<Basket>> retrieveBasket(@PathVariable String basketId) {
        try {
            Optional<Basket> basket = basketService.retrieveBasket(basketId);
            return ResponseEntity.ok(basket);
        } catch (BasketNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
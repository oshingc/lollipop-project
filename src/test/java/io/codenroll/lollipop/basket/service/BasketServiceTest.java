package io.codenroll.lollipop.basket.service;

import io.codenroll.lollipop.basket.model.Basket;
import io.codenroll.lollipop.basket.model.BasketItem;
import io.codenroll.lollipop.basket.model.BasketLevel;
import io.codenroll.lollipop.basket.repository.BasketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    private BasketRepository basketRepository;

    @InjectMocks
    private BasketService basketService;

    private Basket basket;

    @BeforeEach
    public void setUp() {
        basket = new Basket(BasketLevel.NORMAL);
        basket.setId("32dc1db9-a327-4362-b367-15ab668e092a");
    }

    @Test
    public void testCreateBasket() {
        // Arrange
        when(basketRepository.save(any(Basket.class))).thenReturn(basket);

        // Act
        Basket createdBasket = basketService.create(BasketLevel.NORMAL);

        // Assert
        assertNotNull(createdBasket);
        assertEquals(BasketLevel.NORMAL, createdBasket.getLevel());
        verify(basketRepository).save(any(Basket.class));
    }

    @Test
    public void testAddBasketItem() {
        // Arrange
        BasketItem basketItem = new BasketItem("item1", 2, 10.00);
        basketItem.setId("item1");
        when(basketRepository.findById(eq(basket.getId()))).thenReturn(Optional.of(basket));
        when(basketRepository.update(any(Basket.class))).thenReturn(basket);

        // Act
        Basket updatedBasket = basketService.addBasketItem(basket.getId(), basketItem);

        // Assert
        assertNotNull(updatedBasket);
        assertEquals(1, updatedBasket.getBasketItems().size());
        assertEquals("item1", updatedBasket.getBasketItems().get(0).getId());
        verify(basketRepository).update(any(Basket.class));
    }

    @Test
    public void testUpdateBasketItem() {
        // Arrange
        BasketItem existingItem = new BasketItem("item1", 2, 10.00);
        existingItem.setId("basketItemId1");
        basket.addBasketItem(existingItem);
        when(basketRepository.findById(eq(basket.getId()))).thenReturn(Optional.of(basket));
        when(basketRepository.update(any(Basket.class))).thenReturn(basket);

        // Act
        BasketItem updatedItem = new BasketItem("item1", 3, 5.00);
        updatedItem.setId("basketItemId1");
        Basket updatedBasket = basketService.updateBasketItem(basket.getId(), updatedItem);

        // Assert
        assertNotNull(updatedBasket);
        assertEquals(1, updatedBasket.getBasketItems().size());
        assertEquals(5, updatedBasket.getBasketItems().get(0).getQuantity());
        verify(basketRepository).update(any(Basket.class));
    }

    @Test
    public void testDeleteBasketItem() {
        // Arrange
        BasketItem basketItem = new BasketItem("item1", 2, 10.00);
        basketItem.setId("basketId");
        basket.addBasketItem(basketItem);
        when(basketRepository.findById(eq(basket.getId()))).thenReturn(Optional.of(basket));

        // Act
        basketService.deleteBasketItem(basket.getId(), basketItem.getId());

        // Assert
        assertTrue(basket.getBasketItems().isEmpty());
        verify(basketRepository).update(any(Basket.class));
    }
}
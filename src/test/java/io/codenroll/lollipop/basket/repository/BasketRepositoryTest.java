package io.codenroll.lollipop.basket.repository;

import io.codenroll.lollipop.basket.model.Basket;
import io.codenroll.lollipop.basket.model.BasketLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BasketRepositoryTest {

    @Mock
    private BasketRepository basketRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindById() {
        // Arrange
        String basketId = "testId";
        Basket expectedBasket = new Basket(BasketLevel.NORMAL);
        expectedBasket.setId(basketId);

        when(basketRepository.findById(basketId)).thenReturn(Optional.of(expectedBasket));

        // Act
        Optional<Basket> actualBasket = basketRepository.findById(basketId);

        // Assert
        assertTrue(actualBasket.isPresent());
        assertEquals(expectedBasket, actualBasket.get());
        verify(basketRepository, times(1)).findById(basketId);
    }

    @Test
    void testSave() {
        // Arrange
        Basket basket = new Basket(BasketLevel.NORMAL);
        basket.setId("newBasketId");

        when(basketRepository.save(basket)).thenReturn(basket);

        // Act
        Basket savedBasket = basketRepository.save(basket);

        // Assert
        assertNotNull(savedBasket);
        assertEquals("newBasketId", savedBasket.getId());
        verify(basketRepository, times(1)).save(basket);
    }

    @Test
    void testUpdate() {
        // Arrange
        Basket basket = new Basket(BasketLevel.NORMAL);
        basket.setId("existingBasketId");

        when(basketRepository.update(basket)).thenReturn(basket);

        // Act
        Basket updatedBasket = basketRepository.update(basket);

        // Assert
        assertNotNull(updatedBasket);
        assertEquals("existingBasketId", updatedBasket.getId());
        verify(basketRepository, times(1)).update(basket);
    }

    @Test
    void testDeleteById() {
        // Arrange
        String basketId = "testId";

        // Act
        basketRepository.deleteById(basketId);

        // Assert
        verify(basketRepository, times(1)).deleteById(basketId);
    }
}
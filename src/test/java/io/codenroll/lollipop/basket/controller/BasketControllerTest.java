package io.codenroll.lollipop.basket.controller;
import io.codenroll.lollipop.basket.model.Basket;
import io.codenroll.lollipop.basket.model.BasketItem;
import io.codenroll.lollipop.basket.model.BasketLevel;
import java.util.Optional;

import io.codenroll.lollipop.basket.repository.BasketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import io.codenroll.lollipop.basket.service.BasketService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BasketControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private BasketService basketService;

    @InjectMocks
    private BasketController basketController;

    @BeforeEach
    public void setUp() {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(basketController).build();
    }

    @Test
    public void testAddItemToBasket() throws Exception {
        // Arrange
        String basketId = "32dc1db9-a327-4362-b367-15ab668e092a";
        String itemId = "itemId1";
        String itemName = "item1";
        int quantity = 2;
        double price = 10.00;

        // Create the BasketItem to be added
        BasketItem basketItem = new BasketItem(itemName, quantity, price);
        basketItem.setId(itemId);

        // Initialize the Basket and add the item
        Basket basket = new Basket(BasketLevel.NORMAL);
        basket.setId(basketId);  // Assuming Basket has a setId method

        // Mock the repository to return the basket when findById is called
        when(basketRepository.findById(eq(basketId))).thenReturn(Optional.of(basket));

        // Mock the service to return the updated basket when addBasketItem is called
        when(basketService.addBasketItem(eq(basketId), any(BasketItem.class))).thenAnswer(invocation -> {
            BasketItem b = invocation.getArgument(0, BasketItem.class);
            basket.addItem(b);
            //b.addItem(basketItem);
            return basket;
        });

        // Act & Assert
        mockMvc.perform(post("/baskets/" + basketId + "/basketItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"item1\",\"quantity\":2,\"price\":10.00}"))
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString())) // Log the response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.basketItems[0].id").value("item1"))
                .andExpect(jsonPath("$.basketItems[0].quantity").value(2))
                .andExpect(jsonPath("$.basketItems[0].price").value(10.00));

        // Verify that the service was called with the correct parameters
        verify(basketService).addBasketItem(eq(basketId), any(BasketItem.class));
    }

}
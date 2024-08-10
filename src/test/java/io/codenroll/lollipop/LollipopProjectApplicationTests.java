package io.codenroll.lollipop;

import io.codenroll.lollipop.basket.controller.BasketControllerTest;
import io.codenroll.lollipop.basket.model.Basket;
import io.codenroll.lollipop.basket.model.BasketLevel;
import io.codenroll.lollipop.basket.repository.BasketRepository;
import io.codenroll.lollipop.basket.repository.BasketRepositoryTest;
import io.codenroll.lollipop.basket.service.BasketService;
import io.codenroll.lollipop.basket.service.BasketServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class LollipopProjectApplicationTests {

	@Mock
	private BasketRepository basketRepository;
	@InjectMocks
	private BasketService basketService;
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void contextLoads() {
	}

	@Test
	void testCreateBasket() {

		Basket expectedBasket = new Basket(BasketLevel.NORMAL);
		// Mock the repository's save method
		when(basketRepository.save(expectedBasket)).thenReturn(expectedBasket);
		// Act
		Basket result = basketService.create(BasketLevel.NORMAL);
		// Assert
		assertNotNull(result);
		assertEquals(expectedBasket, result);
	}

}

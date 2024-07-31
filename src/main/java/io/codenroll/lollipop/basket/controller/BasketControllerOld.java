package io.codenroll.lollipop.basket.controller;

import io.codenroll.lollipop.basket.model.Basket;
import io.codenroll.lollipop.basket.controller.dto.CreateBasketDto;
import io.codenroll.lollipop.product.model.Product;
import io.codenroll.lollipop.basket.service.BasketServiceOld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/baskets")
public class BasketControllerOld {
    @Autowired
    private BasketServiceOld basketService;

    @PostMapping
    public Basket createBasket(@RequestBody CreateBasketDto dto) {
        return basketService.create(dto.level());
    }

    @PostMapping("/{basketId}/products")
    @ResponseBody
    public boolean addProductToBasket(@PathVariable String id, @RequestBody Product product) {
        basketService.addProduct(id, product);
        return true;
    }

    /** Removed nid, no needed, changed PutMapping for PostMapping**/
    @PostMapping("/{id}/products")
    public boolean updateProduct(@PathVariable String id, @RequestBody Product product) {
        basketService.updateProduct(id, product);
        return true;
    }

    /** Added missing basketId, changed method name, changed param name **/
    @PostMapping("/{basketId}/products/{productId}")
    public boolean deleteProduct(@PathVariable String id, @PathVariable String productId) {
        basketService.deleteProduct(id, productId);
        return true;
    }

    @PostMapping("/{id}/complete")
    public boolean completeBasket(@PathVariable String id) {
        basketService.complete(id);
        return true;
    }

    @GetMapping("/{id}")
    public Basket retrieveBasket(@PathVariable String id) {
        return basketService.retrieveBasket(id);
    }

}
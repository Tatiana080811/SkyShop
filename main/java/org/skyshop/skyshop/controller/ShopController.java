package org.skyshop.skyshop.controller;

import org.skyshop.skyshop.service.BasketService;
import org.skyshop.skyshop.service.UserBasket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.skyshop.skyshop.model.search.SearchService;
import org.skyshop.skyshop.service.StorageService;
import org.skyshop.skyshop.model.search.product.Product;
import org.skyshop.skyshop.model.search.article.Article;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
public class ShopController {

    private final StorageService storageService;
    private final SearchService searchService;
    private final BasketService basketService;
    @Autowired
    public ShopController(StorageService storageService, SearchService searchService, BasketService basketService) {
        this.storageService = storageService;
        this.searchService = searchService;
        this.basketService = basketService;
    }


    @GetMapping("/products")
    public Collection<Product> getAllProducts() {
        return storageService.getAllProducts();
    }

    @GetMapping("/articles")
    public Collection<Article> getAllArticles() {
        return storageService.getAllArticles();
    }

    @GetMapping("/search")
    public List<?> search(@RequestParam String pattern) {
        return searchService.search(pattern);
    }

    @GetMapping("/basket/{id}")
    public String addProduct(@PathVariable("id") UUID id) {
        basketService.addToProductBasket(id);
        return "Продукт успешно добавлен";
    }

    @GetMapping("/basket")
    public UserBasket getUserBasket() {
        return basketService.getUserBasket();
    }
}




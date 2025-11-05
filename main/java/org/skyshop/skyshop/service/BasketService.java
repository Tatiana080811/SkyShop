package org.skyshop.skyshop.service;

import org.skyshop.skyshop.model.search.product.Product;
import org.skyshop.skyshop.exception.NoSuchProductException;
import org.springframework.stereotype.Service;
import org.skyshop.skyshop.service.StorageService;
import org.skyshop.skyshop.model.search.basket.ProductBasket;
import org.skyshop.skyshop.service.UserBasket;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private final StorageService storageService;
    private final ProductBasket basketComponent;

    public BasketService(final StorageService storageService,
                         final ProductBasket basketComponent) {
        if (storageService == null || basketComponent == null) {
            throw new NullPointerException("Arguments must not be null.");
        }
        this.storageService = storageService;
        this.basketComponent = basketComponent;
    }

    public void addToProductBasket(final UUID productId) {
        try {
            Product product = storageService.getProductById(productId); // Используем новый метод, без Optional
            basketComponent.addItem(product);
        } catch (NoSuchProductException e) {
            throw new NoSuchElementException("Продукт с таким ID не найден");
        }
    }

    public UserBasket getUserBasket() {
        Map<UUID, Integer> currentBasket = basketComponent.getCurrentBasket();
        List<BasketItem> basketItems = currentBasket.entrySet().stream()
                .map(entry -> {
                    try {
                        Product product = storageService.getProductById(entry.getKey());
                        return new BasketItem(product, entry.getValue());
                    } catch (NoSuchProductException e) {
                        throw new IllegalStateException("Продукт с данным ID (" + entry.getKey() + ") отсутствует в магазине.", e);
                    }
                }).collect(Collectors.toList());

        BigDecimal total = calculateTotal(basketItems);
        return new UserBasket(basketItems, total);
    }

    public BigDecimal calculateTotal(List<BasketItem> basketItems) {
        return basketItems.stream()
                .map(item -> {
                    BigDecimal price = item.product().getPrice();
                    int quantity = item.quantity();
                    return price.multiply(BigDecimal.valueOf(quantity));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
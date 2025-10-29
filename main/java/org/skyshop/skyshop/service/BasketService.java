package org.skypro.skyshop.service;
import org.skypro.skyshop.model.search.product.Product;
import org.skyshop.skyshop.model.search.basket.ProductBasket;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private final org.skypro.skyshop.service.StorageService storageService;
    private final ProductBasket basketComponent;

    public BasketService(final org.skypro.skyshop.service.StorageService storageService,
                         final ProductBasket basketComponent) {
        if (storageService == null || basketComponent == null) {
            throw new NullPointerException("Arguments must not be null.");
        }
        this.storageService = storageService;
        this.basketComponent = basketComponent;
    }

    public void addToProductBasket(final UUID productId) {
        final Optional<Product> product = storageService.getProductById(productId);

        if (!product.isPresent()) {
            throw new NoSuchElementException("Продукт с таким ID не найден");
        }
        basketComponent.addItem(product.get());
    }

    public UserBasket getUserBasket() {
        Map<UUID, Integer> currentBasket = basketComponent.getCurrentBasket();
        List<org.skypro.skyshop.service.BasketItem> basketItems = currentBasket.entrySet().stream()
                .map(entry -> {
                    Product product = storageService.getProductById(entry.getKey())
                            .orElseThrow(() -> new IllegalStateException("Продукт с данным ID (" + entry.getKey() + ") отсутствует в магазине."));
                    return new org.skypro.skyshop.service.BasketItem(product, entry.getValue());
                })
                .collect(Collectors.toList());

        BigDecimal total = calculateTotal(basketItems);
        return new UserBasket(basketItems, total);
    }

    public BigDecimal calculateTotal(List<org.skypro.skyshop.service.BasketItem> basketItems) {
        return basketItems.stream()
                .map(item -> {
                    BigDecimal price = item.product().getPrice();
                    int quantity = item.quantity();
                    return price.multiply(BigDecimal.valueOf(quantity));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}



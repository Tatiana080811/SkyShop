package org.skyshop.skyshop.model.search.basket;

import org.skypro.skyshop.model.search.product.Product;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProductBasket {
    private final Map<UUID, Integer> basketItems;

    public ProductBasket() {
        this.basketItems = new ConcurrentHashMap<>();
    }

    public void addItem(Product product) {
        UUID productId = product.getId();
        basketItems.merge(productId, 1, Integer::sum);
    }

    public Map<UUID, Integer> getCurrentBasket() {
        return Collections.unmodifiableMap(basketItems);
    }
}




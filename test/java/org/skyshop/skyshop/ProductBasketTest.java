package org.skyshop.skyshop;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyshop.skyshop.model.search.basket.ProductBasket;
import org.skyshop.skyshop.model.search.product.Product;
import org.skyshop.skyshop.service.StorageService;
import org.skyshop.skyshop.exception.NoSuchProductException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProductBasketTest {

    private ProductBasket mockProductBasket;
    private StorageService storageService;
    private Product validProduct;
    private UUID invalidProductId;

    @BeforeEach
    void setUp() {
        mockProductBasket = mock(ProductBasket.class);
        storageService = new StorageService(new HashMap<>(), new HashMap<>());

        validProduct = new Product("Тестовый продукт", new BigDecimal("100.0"));
        storageService.initializeTestData();
        invalidProductId = UUID.randomUUID();
    }

    @Test
    void testAddNonExistingProductToBasket() {
        assertThrows(NoSuchProductException.class,
                () -> storageService.getProductById(invalidProductId));
    }

    @Test
    void testAddValidProductToBasket() {
        when(mockProductBasket.getCurrentBasket()).thenReturn(Collections.emptyMap());
        storageService.getProductById(validProduct.getId());
        mockProductBasket.addItem(validProduct);
        verify(mockProductBasket).addItem(validProduct);
    }

    @Test
    void testGetEmptyBasket() {
        when(mockProductBasket.getCurrentBasket()).thenReturn(Collections.emptyMap());
        Map<UUID, Integer> emptyBasket = mockProductBasket.getCurrentBasket();
        assertEquals(emptyBasket.size(), 0);
    }

    @Test
    void testGetNonEmptyBasket() {
        Map<UUID, Integer> itemsInBasket = new HashMap<>();
        itemsInBasket.put(validProduct.getId(), 1);
        when(mockProductBasket.getCurrentBasket()).thenReturn(itemsInBasket);
        Map<UUID, Integer> nonEmptyBasket = mockProductBasket.getCurrentBasket();
        assertEquals(nonEmptyBasket.size(), 1);
        assertEquals(nonEmptyBasket.get(validProduct.getId()), 1);
    }

    @Test
    void testUpdateQuantityInBasket() {
        Map<UUID, Integer> basketContent = new HashMap<>();
        basketContent.put(validProduct.getId(), 2);
        when(mockProductBasket.getCurrentBasket()).thenReturn(basketContent);
        mockProductBasket.getCurrentBasket().put(validProduct.getId(), 3);
        verify(mockProductBasket).getCurrentBasket();
    }

    @Test
    void testRemoveFromBasket() {

        Map<UUID, Integer> basketContent = new HashMap<>();
        basketContent.put(validProduct.getId(), 1);
        when(mockProductBasket.getCurrentBasket()).thenReturn(basketContent);


        mockProductBasket.getCurrentBasket().remove(validProduct.getId());
        verify(mockProductBasket).getCurrentBasket();
    }
}



package org.skyshop.skyshop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skyshop.skyshop.model.search.SearchResult;
import org.skyshop.skyshop.model.search.SearchService;
import org.skyshop.skyshop.model.search.Searchable;
import org.skyshop.skyshop.model.search.article.Article;
import org.skyshop.skyshop.model.search.product.Product;
import org.skyshop.skyshop.service.StorageService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

class SearchServiceTest {

    @InjectMocks
    private SearchService searchService;

    @Mock
    private StorageService storageService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchWithEmptyStorage() {
        when(storageService.getAllSearchables()).thenReturn(new ArrayList<>());

        List<SearchResult> results = searchService.search("Яблоко");
        assertTrue(results.isEmpty());
    }


    @Test
    void testSearchWithUnmatchedItems() {
        Product product = new Product("Груша", new BigDecimal("80.0"));
        Article article = new Article("Опишите ананас", UUID.randomUUID().toString());

        List<Searchable> items = new ArrayList<>();
        items.add(product);
        items.add(article);

        when(storageService.getAllSearchables()).thenReturn(items);

        List<SearchResult> results = searchService.search("Яблоко");
        assertTrue(results.isEmpty());
    }

    @Test
    void testSuccessfulSearch() {
        Product product = new Product("Яблоко", new BigDecimal("50.0"));
        Article article = new Article("Обзор яблок", UUID.randomUUID().toString());

        List<Searchable> items = new ArrayList<>();
        items.add(product);
        items.add(article);

        when(storageService.getAllSearchables()).thenReturn(items);

        List<SearchResult> results = searchService.search("Яблоко");
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
    }
}
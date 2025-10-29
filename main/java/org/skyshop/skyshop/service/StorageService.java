package org.skypro.skyshop.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

import org.skypro.skyshop.model.search.product.Product;
import org.skypro.skyshop.model.search.article.Article;
import org.skypro.skyshop.model.search.Searchable;

@Service
public class StorageService {
    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    @Autowired
    public StorageService(Map<UUID, Product> products, Map<UUID, Article> articles) {
        this.products = products;
        this.articles = articles;
        initializeTestData(); // Можно убрать, если хотите отделить тестовую инициализацию
    }

    public Collection<Product> getAllProducts() {
        return Collections.unmodifiableCollection(products.values());
    }

    public Collection<Article> getAllArticles() {
        return Collections.unmodifiableCollection(articles.values());
    }

    public Collection<Searchable> getAllSearchables() {
        List<Searchable> result = new ArrayList<>();
        result.addAll(products.values());
        result.addAll(articles.values());
        return result;
    }

    private void initializeTestData() {

        Product product1 = new Product("Яблоко", new BigDecimal("50.0"));
        Product product2 = new Product("Простой персик", new BigDecimal("100.0"));
        Product product3 = new Product("Киви", new BigDecimal("150.0"));

        products.put(product1.getId(), product1);
        products.put(product2.getId(), product2);
        products.put(product3.getId(), product3);

        Article article1 = new Article("Описание яблока", UUID.randomUUID().toString());
        Article article2 = new Article("Описание персика", UUID.randomUUID().toString());
        Article article3 = new Article("Описание киви", UUID.randomUUID().toString());

        articles.put(article1.getId(), article1);
        articles.put(article2.getId(), article2);
        articles.put(article3.getId(), article3);
    }

    public Optional<Product> getProductById(UUID productId) {
        return Optional.ofNullable(products.get(productId));
    }
}


package org.skypro.skyshop.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.skypro.skyshop.model.search.product.Product;

import java.math.BigDecimal;

public record BasketItem(@JsonProperty("product") Product product,
                         @JsonProperty("quantity") int quantity) {}

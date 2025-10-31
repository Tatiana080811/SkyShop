package org.skyshop.skyshop.model.search.product;

import org.skyshop.skyshop.model.search.Searchable;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Product implements Searchable {

    protected final String name;
    protected final BigDecimal basePrice;
    protected final UUID id;

    public Product(String name, BigDecimal basePrice) {
        if (basePrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Цена не может быть меньше или равной 0");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название не может быть пустой строкой");
        }
        this.name = name;
        this.basePrice = basePrice;
        this.id = UUID.randomUUID();
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getSearchTerm() {
        return name;
    }

    public String getContentType() {
        return "PRODUCT";
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Продукт: " + name;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public BigDecimal getPrice() {
        return basePrice;
    }

    public boolean isSpecial() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Product)) return false;
        Product other = (Product) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


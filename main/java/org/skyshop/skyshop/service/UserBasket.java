package org.skyshop.skyshop.service;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record UserBasket(@JsonProperty("items") List<org.skyshop.skyshop.service.BasketItem> items,
                         @JsonProperty("total") BigDecimal total) {}



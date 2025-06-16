package com.shop.products.application.ports.inbound;

import com.shop.products.domain.model.ProductRate;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FindProductRateUseCase {

    Optional<ProductRate> findProductRate(LocalDateTime applicationDate, Integer productId, Integer brandId);
}

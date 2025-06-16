package com.shop.products.application.ports.inbound.service;

import com.shop.products.application.ports.inbound.FindProductRateUseCase;
import com.shop.products.application.ports.outbound.persistence.ProductRepository;
import com.shop.products.domain.model.ProductRate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FindProductRateService implements FindProductRateUseCase {

    private final ProductRepository productRepository;

    @Override
    public Optional<ProductRate> findProductRate(LocalDateTime applicationDate, Integer productId, Integer brandId) {
        if(applicationDate == null || productId == null || brandId == null) {
            throw new IllegalArgumentException("applicationDate and productId and brandId must not be null");
        }
        Optional<List<ProductRate>> productRates = productRepository.findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(productId, brandId, applicationDate);
        return productRates.stream()
                .flatMap(List::stream)
                .max(Comparator.comparing(ProductRate::getPriority));
    }
}

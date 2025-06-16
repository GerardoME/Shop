package com.shop.products.adapters.outbound.persistence.repository;

import com.shop.products.adapters.outbound.persistence.PriceRateEntity;
import com.shop.products.adapters.outbound.persistence.PriceRateRepository;
import com.shop.products.adapters.outbound.persistence.mapper.PriceRateMapper;
import com.shop.products.application.ports.outbound.persistence.ProductRepository;
import com.shop.products.domain.model.ProductRate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class H2Repository implements ProductRepository {

    private final PriceRateRepository priceRateRepository;
    private final PriceRateMapper priceRateMapper;

    @Override
    public Optional<List<ProductRate>> findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(Integer productId, Integer brandId, LocalDateTime applicationDate) {
        List<PriceRateEntity> priceRateEntity =
                priceRateRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(productId, brandId, applicationDate, applicationDate);
        return Optional.ofNullable(priceRateEntity)
                .map(list -> list.stream()
                        .map(priceRateMapper::toProductRate)
                        .collect(Collectors.toList()));
    }
}

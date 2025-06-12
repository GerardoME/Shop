package com.shop.products.adapters.outbound.persistence.repository;

import com.shop.products.adapters.outbound.persistence.PriceRateEntity;
import com.shop.products.adapters.outbound.persistence.PriceRateRepository;
import com.shop.products.adapters.outbound.persistence.mapper.PriceRateMapper;
import com.shop.products.application.ports.outbound.persistence.ProductRepository;
import com.shop.products.domain.model.ProductRate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class H2Repository implements ProductRepository {

    private final PriceRateRepository priceRateRepository;
    private final PriceRateMapper priceRateMapper;
    @Override
    public Optional<ProductRate> findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(Long productId,
                                                                                                   Long brandId,
                                                                                                   LocalDate applicationDate) {
        PriceRateEntity priceRateEntity =
                priceRateRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(productId, brandId, applicationDate.atStartOfDay(), applicationDate.atStartOfDay());
        return Optional.ofNullable(priceRateEntity).map(priceRateMapper::toProductRate);
    }
}

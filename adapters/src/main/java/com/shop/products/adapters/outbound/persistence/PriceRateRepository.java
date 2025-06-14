package com.shop.products.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PriceRateRepository extends JpaRepository<PriceRateEntity, Long> {

    PriceRateEntity findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        Integer productId, Integer brandId, LocalDateTime applicationDateStart, LocalDateTime applicationDateEnd);
}
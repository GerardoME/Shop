package com.shop.products.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRateRepository extends JpaRepository<PriceRateEntity, Long> {

    List<PriceRateEntity> findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        Integer productId, Integer brandId, LocalDateTime applicationDateStart, LocalDateTime applicationDateEnd);
}
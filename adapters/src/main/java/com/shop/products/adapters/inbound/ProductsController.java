package com.shop.products.adapters.inbound;

import com.shop.products.adapters.inbound.model.AppliedRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Service
@Slf4j
public class ProductsController implements ProductsApiDelegate {

    @Override
    public ResponseEntity<AppliedRate> getApplicableRate(
            LocalDate applicationDate,
            Long productId,
            Long brandId) {

        log.info("Request received with date {}, product id {}, brand id {}", applicationDate, productId, brandId);

            AppliedRate product = new AppliedRate();
            product.setProductId(productId);
            product.setBrandId(brandId);
            product.setRateToApply("TARIFA_BASE_VERANO");
            product.setApplicationStartDate(OffsetDateTime.parse("2025-06-10T00:00:00Z"));
            product.setApplicationEndDate(OffsetDateTime.parse("2025-08-31T23:59:59Z"));
            product.setFinalPriceToApply(35.50F);

            return new ResponseEntity<>(product, HttpStatus.OK);

    }
}

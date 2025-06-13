package com.shop.products.application.ports.inbound.service;

import com.shop.products.application.ports.outbound.persistence.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class FindProductRateServiceTest {

    private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private FindProductRateService service = new FindProductRateService(productRepository);

    @Test
    void findProductRate() {
        service.findProductRate(LocalDate.now(), 1234, 1);
        verify(productRepository, times(1))
                .findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(any(), any(), any());
    }

}
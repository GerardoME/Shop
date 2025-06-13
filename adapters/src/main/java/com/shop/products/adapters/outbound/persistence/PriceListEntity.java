package com.shop.products.adapters.outbound.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceListEntity {

        @Id
        @Column(name = "rate_id")
        private Integer id;

        @Column(name = "name", unique = true, nullable = false)
        private String name;
}

package com.shop.products.adapters.outbound.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "brands")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandEntity {

    @Id
    @Column(name = "brand_id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @AllArgsConstructor
    @Getter
    public enum BrandEnum {
        ZARA(1L, "ZARA"),
        MASSIMO_DUTTI(2L, "Massimo Dutti"),
        BERSHKA(3L, "Bershka"),;

        private final Long id;
        private final String name;

        public static BrandEnum fromId(Long id) {
            for (BrandEnum brand : BrandEnum.values()) {
                if (brand.getId().equals(id)) {
                    return brand;
                }
            }
            throw new IllegalArgumentException("No brand with ID " + id);
        }
    }
}
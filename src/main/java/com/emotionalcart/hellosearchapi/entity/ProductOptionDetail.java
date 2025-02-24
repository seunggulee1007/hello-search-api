package com.emotionalcart.hellosearchapi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    @Column(nullable = false)
    private Integer quantity = 0;

    private Integer optionOrder = 1;

    private Integer additionalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id")
    @Setter
    private ProductOption productOption;

    private ProductOptionDetail(
            String value,
            Integer optionOrder,
            Integer additionalPrice,
            ProductOption productOption
    ) {
        this.value = value;
        this.optionOrder = optionOrder;
        this.additionalPrice = additionalPrice;
        this.productOption = productOption;
        this.quantity = 0;
    }

    public static ProductOptionDetail of(String value, Integer optionOrder, Integer additionalPrice, ProductOption productOption) {
        return new ProductOptionDetail(
                value,
                optionOrder,
                additionalPrice,
                productOption
        );
    }
}

package com.emotionalcart.hellosearchapi.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Integer price;

    private Long providerId;

    private Long categoryId;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductOption> options;

    private Product(
            String name,
            String description,
            Integer price,
            Long providerId,
            Long categoryId
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.providerId = providerId;
        this.categoryId = categoryId;
    }

    public static Product of(
            String name,
            String description,
            Integer price,
            Long providerId,
            Long categoryId
    ) {
        return new Product(
                name,
                description,
                price,
                providerId,
                categoryId
        );
    }

    public void setOptions(List<ProductOption> options) {
        this.options = options != null ? options : new ArrayList<>();
        for (ProductOption option : this.options) {
            option.setProduct(this);
        }
    }

}

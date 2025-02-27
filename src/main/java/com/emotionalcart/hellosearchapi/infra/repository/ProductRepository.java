package com.emotionalcart.hellosearchapi.infra.repository;

import com.emotionalcart.hellosearchapi.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}

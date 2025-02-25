package com.emotionalcart.hellosearchapi.repository;

import com.emotionalcart.hellosearchapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}

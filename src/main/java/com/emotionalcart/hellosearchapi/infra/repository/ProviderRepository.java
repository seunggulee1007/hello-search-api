package com.emotionalcart.hellosearchapi.infra.repository;

import com.emotionalcart.hellosearchapi.domain.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {

}

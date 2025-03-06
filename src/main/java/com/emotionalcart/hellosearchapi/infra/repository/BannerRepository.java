package com.emotionalcart.hellosearchapi.infra.repository;

import com.emotionalcart.hellosearchapi.domain.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {

}

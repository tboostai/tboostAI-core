package com.tboostAI_core.repository;

import com.tboostAI_core.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepo extends JpaRepository<SellerEntity, Long> {
}

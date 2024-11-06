package com.tboostAI_core.repository;

import com.tboostAI_core.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<PostEntity, Long> {
    PostEntity findByVehicleUuid(Long vehicleId);
}

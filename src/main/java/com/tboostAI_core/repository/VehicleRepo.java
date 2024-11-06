package com.tboostAI_core.repository;

import com.tboostAI_core.entity.VehicleBasicInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepo extends JpaRepository<VehicleBasicInfoEntity, Long>, JpaSpecificationExecutor<VehicleBasicInfoEntity> {

    @Query("SELECT v FROM VehicleBasicInfoEntity v " +
            "JOIN FETCH v.locationEntity " +
            "JOIN FETCH v.features " +
            "JOIN FETCH v.price WHERE v.uuid = :uuid")
    Optional<VehicleBasicInfoEntity> getVehicleByUuid(@Param("uuid") Long uuid);


}
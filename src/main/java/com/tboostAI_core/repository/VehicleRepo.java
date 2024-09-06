package com.tboostAI_core.repository;

import com.tboostAI_core.entity.VehicleBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepo extends JpaRepository<VehicleBasicInfo, Long>, JpaSpecificationExecutor<VehicleBasicInfo> {

    @Query("SELECT v FROM VehicleBasicInfo v JOIN FETCH v.location JOIN FETCH v.features WHERE v.uuid = :uuid")
    Optional<VehicleBasicInfo> getVehicleByUuid(@Param("uuid") Long uuid);


}
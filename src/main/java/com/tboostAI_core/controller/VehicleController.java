package com.tboostAI_core.controller;

import com.tboostAI_core.dto.VehicleBasicInfoDTO;
import com.tboostAI_core.entity.request_entity.SearchVehicleListRequest;
import com.tboostAI_core.service.VehicleBasicInfoService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/vehicles")
public class VehicleController {

    @Resource
    private VehicleBasicInfoService vehicleBasicInfoService;
    @Resource
    private PagedResourcesAssembler<VehicleBasicInfoDTO> pagedResourcesAssembler;

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);


    @GetMapping("/vehicle/{uuid}")
    public ResponseEntity<VehicleBasicInfoDTO> getVehicleByVin(@PathVariable Long uuid) {
        VehicleBasicInfoDTO vehicleDTO = vehicleBasicInfoService.getVehicleByUuid(uuid);
        return vehicleDTO != null ? ResponseEntity.ok(vehicleDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PagedModel<EntityModel<VehicleBasicInfoDTO>>> searchVehicles(
            @RequestParam String make,
            @RequestParam String model,
            @RequestParam Integer minYear,
            @RequestParam Integer maxYear,
            @RequestParam String trim,
            @RequestParam Integer mileage,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam String color,
            @RequestParam String bodyType,
            @RequestParam String engineType,
            @RequestParam String transmission,
            @RequestParam String drivetrain,
            @RequestParam String address,
            @RequestParam String condition,
            @RequestParam int capacity,
            @RequestParam List<String> features,
            @RequestParam(required = false, defaultValue = "20") int distance,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("listingDate").descending());

        Page<VehicleBasicInfoDTO> vehiclePage = vehicleBasicInfoService.searchVehicles(
                make, model, minYear, maxYear, trim, mileage, minPrice, maxPrice, color, bodyType, engineType,
                transmission, drivetrain, address, condition, capacity, features, distance, pageable);

        PagedModel<EntityModel<VehicleBasicInfoDTO>> vehicleBasicInfos = pagedResourcesAssembler.toModel(vehiclePage);
        return ResponseEntity.ok(vehicleBasicInfos);
    }

    @PostMapping("/search-by-llm")
    public ResponseEntity<PagedModel<EntityModel<VehicleBasicInfoDTO>>> searchVehicles(
            @RequestBody String content,  // content from user
            @RequestParam(required = false, defaultValue = "20") int distance,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("listingDate").descending());
        Page<VehicleBasicInfoDTO> vehiclePage = vehicleBasicInfoService.searchVehiclesByLLM(content, distance, pageable);
        PagedModel<EntityModel<VehicleBasicInfoDTO>> vehicleBasicInfos = pagedResourcesAssembler.toModel(vehiclePage);
        return ResponseEntity.ok(vehicleBasicInfos);
    }
}

package com.tboostAI_core.controller;

import com.tboostAI_core.dto.VehicleBasicInfoDTO;
import com.tboostAI_core.service.VehicleBasicInfoService;
import jakarta.annotation.Resource;
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

    @GetMapping("/vehicle/{uuid}")
    public ResponseEntity<VehicleBasicInfoDTO> getVehicleByVin(@PathVariable Long uuid) {
        VehicleBasicInfoDTO vehicleDTO = vehicleBasicInfoService.getVehicleByUuid(uuid);
        return vehicleDTO != null ? ResponseEntity.ok(vehicleDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PagedModel<EntityModel<VehicleBasicInfoDTO>>> searchVehicles(
            @RequestParam List<String> make,
            @RequestParam List<String> model,
            @RequestParam Integer minYear,
            @RequestParam Integer maxYear,
            @RequestParam List<String> trim,
            @RequestParam Integer mileage,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam List<String> color,
            @RequestParam List<String> bodyType,
            @RequestParam List<String> engineType,
            @RequestParam List<String> transmission,
            @RequestParam List<String> drivetrain,
            @RequestParam String address,
            @RequestParam List<String> condition,
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
            @RequestParam String address,
            @RequestHeader("sessionID") String sessionID,
            @RequestParam(required = false, defaultValue = "20") int distance,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("listingDate").descending());
        Page<VehicleBasicInfoDTO> vehiclePage = vehicleBasicInfoService.searchVehiclesByLLM(sessionID, content, address, distance, pageable);
        PagedModel<EntityModel<VehicleBasicInfoDTO>> vehicleBasicInfos = pagedResourcesAssembler.toModel(vehiclePage);
        return ResponseEntity.ok(vehicleBasicInfos);
    }
}

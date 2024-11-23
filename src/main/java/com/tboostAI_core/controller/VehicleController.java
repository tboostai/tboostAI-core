package com.tboostAI_core.controller;

import com.tboostAI_core.dto.VehicleBasicInfoDTO;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tboostAI_core.common.GeneralConstants.DEFAULT_DISTANCE;
import static com.tboostAI_core.common.GeneralConstants.DEFAULT_PAGE_SIZE;

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

    @GetMapping("/search-by-llm")
    public ResponseEntity<PagedModel<EntityModel<VehicleBasicInfoDTO>>> searchVehicles(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice,
            @RequestParam List<String> bodyType,
            @RequestParam List<String> engineType,
            @RequestParam String address,
            @RequestBody String content,
            @RequestHeader("sessionID") String sessionID,
            @RequestParam(required = false, defaultValue = DEFAULT_DISTANCE) int distance,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {

        // Collect parameters into a map
        Map<String, Object> params = new HashMap<>();
        params.put("sessionID", sessionID);
        params.put("minPrice", minPrice);
        params.put("maxPrice", maxPrice);
        params.put("bodyType", bodyType);
        params.put("engineType", engineType);
        params.put("address", address);
        params.put("content", content);
        params.put("distance", distance);
        params.put("page", page);
        params.put("pageSize", pageSize);

        // Log all parameters in one line
        logger.info("VehicleController - Request received for /search-by-llm with parameters: {}", params);

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("listingDate").descending());
        Page<VehicleBasicInfoDTO> vehiclePage = vehicleBasicInfoService.searchVehiclesByLLM(sessionID, minPrice, maxPrice,
                bodyType, engineType, content, address, distance, pageable);
        PagedModel<EntityModel<VehicleBasicInfoDTO>> vehicleBasicInfos = pagedResourcesAssembler.toModel(vehiclePage);
        return ResponseEntity.ok(vehicleBasicInfos);
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
            @RequestParam(required = false, defaultValue = DEFAULT_DISTANCE) int distance,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("listingDate").descending());

        Page<VehicleBasicInfoDTO> vehiclePage = vehicleBasicInfoService.searchVehicles(
                make, model, minYear, maxYear, trim, mileage, minPrice, maxPrice, color, bodyType, engineType,
                transmission, drivetrain, address, condition, capacity, features, distance, pageable);

        PagedModel<EntityModel<VehicleBasicInfoDTO>> vehicleBasicInfos = pagedResourcesAssembler.toModel(vehiclePage);
        return ResponseEntity.ok(vehicleBasicInfos);
    }
}

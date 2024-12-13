package com.tboostAI_core.controller;

import com.tboostAI_core.dto.SearchVehiclesResponse;
import com.tboostAI_core.dto.VehicleBasicInfoDTO;
import com.tboostAI_core.service.VehicleBasicInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tboostAI_core.common.GeneralConstants.DEFAULT_DISTANCE;
import static com.tboostAI_core.common.GeneralConstants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping(value = "/vehicles")
@Tag(name = "Vehicle info related APIs", description = "APIs about vehicle info")
public class VehicleController {

    @Resource
    private VehicleBasicInfoService vehicleBasicInfoService;

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Operation(
            summary = "Get vehicle details by UUID",
            description = "Fetches the details of a vehicle by its unique UUID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicle details retrieved successfully",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "make": "Toyota",
                                              "model": "Camry",
                                              "year": 2020,
                                              "trim": "LE",
                                              "vin": "1HGBH41JXMN109186",
                                              "mileage": 30000,
                                              "exteriorColor": "White",
                                              "interiorColor": "Black",
                                              "bodyType": "Sedan",
                                              "engineType": "Gasoline",
                                              "engineSize": 2.5,
                                              "cylinder": 4,
                                              "transmission": "Automatic",
                                              "drivetrain": "FWD",
                                              "location": {
                                                "country": "United States",
                                                "stateProvince": "California",
                                                "city": "Los Angeles",
                                                "postalCode": "90001"
                                              }
                                            }"""
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Vehicle not found for the given UUID")
    })
    @GetMapping("/vehicle/{uuid}")
    public ResponseEntity<VehicleBasicInfoDTO> getVehicleByVin(@PathVariable Long uuid) {
        VehicleBasicInfoDTO vehicleDTO = vehicleBasicInfoService.getVehicleByUuid(uuid);
        return vehicleDTO != null ? ResponseEntity.ok(vehicleDTO) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Search vehicles using LLM",
            description = "Search for vehicles based on user-provided parameters and content analyzed by LLM"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicles retrieved successfully",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "_embedded": {
                                                "vehicleBasicInfoDTOList": [
                                                  {
                                                    "make": "Toyota",
                                                    "model": "Camry",
                                                    "year": 2020,
                                                    "trim": "LE",
                                                    "price": 25000.00,
                                                    "currency": "USD"
                                                  },
                                                  {
                                                    "make": "Honda",
                                                    "model": "Accord",
                                                    "year": 2019,
                                                    "trim": "EX",
                                                    "price": 22000.00,
                                                    "currency": "USD"
                                                  }
                                                ]
                                              },
                                              "page": {
                                                "size": 10,
                                                "totalElements": 50,
                                                "totalPages": 5,
                                                "number": 0
                                              }
                                            }"""
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided")
    })
    @GetMapping("/search-by-llm")
    public ResponseEntity<SearchVehiclesResponse> searchVehicles(
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

        logger.info("VehicleController - Request received for /search-by-llm");

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("listingDate").descending());

        SearchVehiclesResponse searchVehiclesResponse = vehicleBasicInfoService.searchVehiclesByLLM(
                sessionID, minPrice, maxPrice, bodyType, engineType, content, address, distance, pageable);

        return searchVehiclesResponse != null ? ResponseEntity.ok(searchVehiclesResponse) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Search vehicles with detailed filters",
            description = "Search for vehicles using detailed parameters like make, model, year, price, and more"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicles retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided")
    })
    @GetMapping("/search")
    public ResponseEntity<SearchVehiclesResponse> searchVehicles(
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
        SearchVehiclesResponse searchVehiclesResponse = vehicleBasicInfoService.searchVehicles(
                make, model, minYear, maxYear, trim, mileage, minPrice, maxPrice, color, bodyType, engineType,
                transmission, drivetrain, address, condition, capacity, features, distance, pageable);

        return searchVehiclesResponse != null ? ResponseEntity.ok(searchVehiclesResponse) : ResponseEntity.notFound().build();
    }
}

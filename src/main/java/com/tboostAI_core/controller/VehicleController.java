package com.tboostAI_core.controller;

import com.tboostAI_core.common.SearchTypeEnum;
import com.tboostAI_core.dto.SearchVehiclesResponse;
import com.tboostAI_core.dto.VehicleBasicInfoDTO;
import com.tboostAI_core.service.impl.VehicleBasicInfoServiceImpl;
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
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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
    private VehicleBasicInfoServiceImpl vehicleBasicInfoServiceImpl;
    @Resource
    private PagedResourcesAssembler<VehicleBasicInfoDTO> pagedResourcesAssembler;

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    public record ApiResponseEntity(SearchTypeEnum searchTypeEnum, PagedModel<EntityModel<VehicleBasicInfoDTO>> pagedModel){}

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
                                              "uuid": 234,
                                              "make": "Cadillac",
                                              "model": "Escalade",
                                              "year": 2022,
                                              "trim": "4X4 ESV SPORT-EDITION(RARE PACKAGE)",
                                              "vin": "1GYS4PKL7NR232864",
                                              "mileage": 34449,
                                              "exteriorColor": "Black",
                                              "interiorColor": "Brown",
                                              "bodyType": "SUV",
                                              "engineType": "Gasoline",
                                              "engineSize": null,
                                              "cylinder": 8,
                                              "transmission": "Automatic",
                                              "drivetrain": "four wheel drive",
                                              "location": {
                                                "country": "US",
                                                "stateProvince": "Michigan",
                                                "city": "Redford",
                                                "street": null,
                                                "postalCode": "482**",
                                                "unit": null,
                                                "latitude": 42.3995939,
                                                "longitude": -83.2958857
                                              },
                                              "vehicleCondition": "Used",
                                              "engineInfo": null,
                                              "cylinderInfo": null,
                                              "warranty": "Vehicle has an existing warranty",
                                              "vehicleTitle": "Rebuilt, Rebuildable & Reconstructed",
                                              "capacity": 0,
                                              "doors": 0,
                                              "features": [
                                                "sunroof",
                                                "head-up display"
                                              ],
                                              "images": [
                                                {
                                                  "url": "https://i.ebayimg.com/images/g/VVgAAOSwWNRnAJc1/s-l1600.jpg",
                                                  "width": 1024,
                                                  "height": 683
                                                }
                                              ],
                                              "listingDate": "2024-10-09T21:50:48.000+00:00",
                                              "sourceId": 1,
                                              "description": "2022 Cadillac Escalade ESV Sport Vehicle Information VIN: 1GYS4PKL7NR232864 Stock: Mileage: 34,449 Color: Black Raven Trans: 10-Speed Automatic Engine: 6.2 LITER V8 ENGINE MPG: 14 City / 19 Highway Drivetrain: 4X4 Description ((HOLLYWOOD STYLE)) ((ONE OWNER)) PLEASE CLICK ON THE ABOVE 24 PICTURES IN ORDER TO VIEW IN FULL SCREEN MODE.", \s
                                              "aiDescription": [
                                                "2022 Cadillac Escalade ESV Sport in Black Raven color",
                                                " Equipped with a 10-Speed Automatic Transmission"
                                              ]
                                            }
                                           \s"""
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Vehicle not found for the given UUID")
    })
    @GetMapping("/vehicle/{uuid}")
    public ResponseEntity<VehicleBasicInfoDTO> getVehicleByVin(@PathVariable Long uuid) {
        logger.info("VehicleController:getVehicleByVin - Search vehicle details by vehicle ID.");
        VehicleBasicInfoDTO vehicleDTO = vehicleBasicInfoServiceImpl.getVehicleByUuid(uuid);
        return vehicleDTO != null ? ResponseEntity.ok(vehicleDTO) : ResponseEntity.notFound().build();
    }

//    @Operation(
//            summary = "Search vehicles using LLM",
//            description = "Search for vehicles based on user-provided parameters and content analyzed by LLM"
//    )
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Vehicles retrieved successfully",
//                    content = @io.swagger.v3.oas.annotations.media.Content(
//                            mediaType = "application/json",
//                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
//                                    value = """
//                                            {
//                                              "_embedded": {
//                                                "vehicleBasicInfoDTOList": [
//                                                  {
//                                                    "make": "Toyota",
//                                                    "model": "Camry",
//                                                    "year": 2020,
//                                                    "trim": "LE",
//                                                    "price": 25000.00,
//                                                    "currency": "USD"
//                                                  },
//                                                  {
//                                                    "make": "Honda",
//                                                    "model": "Accord",
//                                                    "year": 2019,
//                                                    "trim": "EX",
//                                                    "price": 22000.00,
//                                                    "currency": "USD"
//                                                  }
//                                                ]
//                                              },
//                                              "page": {
//                                                "size": 10,
//                                                "totalElements": 50,
//                                                "totalPages": 5,
//                                                "number": 0
//                                              }
//                                            }"""
//                            )
//                    )
//            ),
//            @ApiResponse(responseCode = "400", description = "Invalid parameters provided")
//    })
//    @GetMapping("/search-by-llm")
//    public ResponseEntity<ApiResponseEntity> searchVehicles(
//            @RequestParam Double minPrice,
//            @RequestParam Double maxPrice,
//            @RequestParam List<String> bodyType,
//            @RequestParam List<String> engineType,
//            @RequestParam String address,
//            @RequestBody String content,
//            @RequestHeader("sessionID") String sessionID,
//            @RequestParam(required = false, defaultValue = DEFAULT_DISTANCE) int distance,
//            @RequestParam(required = false, defaultValue = "0") Integer page,
//            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {
//
//        logger.info("VehicleController - Request received for /search-by-llm");
//
//        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("listingDate").descending());
//
//        SearchVehiclesResponse searchVehiclesResponse = vehicleBasicInfoServiceImpl.searchVehiclesByLLM(
//                sessionID, minPrice, maxPrice, bodyType, engineType, content, address, distance, pageable);
//        return getApiResponseEntityResponseEntity(searchVehiclesResponse);
//    }

    @Operation(
            summary = "Search vehicles with detailed filters",
            description = "Search for vehicles using detailed parameters like make, model, year, price, and more"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicles retrieved successfully",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                        {
                                          "searchType": "PRECISE",
                                          "pagedModel": {
                                            "page": {
                                              "size": 10,
                                              "totalElements": 100,
                                              "totalPages": 10,
                                              "number": 0
                                            },
                                            "content": [
                                              {
                                                "uuid": 234,
                                                "make": "Cadillac",
                                                "model": "Escalade",
                                                "year": 2022,
                                                "trim": "4X4 ESV SPORT-EDITION(RARE PACKAGE)",
                                                "vin": "1GYS4PKL7NR232864",
                                                "mileage": 34449,
                                                "exteriorColor": "Black",
                                                "interiorColor": "Brown",
                                                "bodyType": "SUV",
                                                "engineType": "Gasoline",
                                                "engineSize": null,
                                                "cylinder": 8,
                                                "transmission": "Automatic",
                                                "drivetrain": "Four Wheel Drive",
                                                "location": {
                                                  "country": "US",
                                                  "stateProvince": "Michigan",
                                                  "city": "Redford",
                                                  "street": null,
                                                  "postalCode": "482**",
                                                  "unit": null,
                                                  "latitude": 42.3995939,
                                                  "longitude": -83.2958857
                                                },
                                                "vehicleCondition": "Used",
                                                "engineInfo": null,
                                                "cylinderInfo": null,
                                                "warranty": "Vehicle has an existing warranty",
                                                "vehicleTitle": "Rebuilt, Rebuildable & Reconstructed",
                                                "capacity": 0,
                                                "doors": 0,
                                                "features": [
                                                  "Sunroof",
                                                  "Head-Up Display"
                                                ],
                                                "images": [
                                                  {
                                                    "url": "https://i.ebayimg.com/images/g/VVgAAOSwWNRnAJc1/s-l1600.jpg",
                                                    "width": 1024,
                                                    "height": 683
                                                  }
                                                ],
                                                "listingDate": "2024-10-09T21:50:48.000+00:00",
                                                "sourceId": 1,
                                                "description": "2022 Cadillac Escalade ESV Sport Vehicle Information VIN: 1GYS4PKL7NR232864 Stock: Mileage: 34,449 Color: Black Raven Trans: 10-Speed Automatic Engine: 6.2 LITER V8 ENGINE MPG: 14 City / 19 Highway Drivetrain: 4X4 Description ((HOLLYWOOD STYLE)) ((ONE OWNER)) PLEASE CLICK ON THE ABOVE 24 PICTURES IN ORDER TO VIEW IN FULL SCREEN MODE.",
                                                "aiDescription": [
                                                  "2022 Cadillac Escalade ESV Sport in Black Raven color",
                                                  "Equipped with a 10-Speed Automatic Transmission"
                                                ]
                                              }
                                            ]
                                          }
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided")
    })
    @GetMapping("/search")
    public ResponseEntity<ApiResponseEntity> searchVehicles(
            @RequestParam List<String> make,
            @RequestParam List<String> model,
            @RequestParam Integer minYear,
            @RequestParam Integer maxYear,
            @RequestParam List<String> trim,
            @RequestParam Double mileage,
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
            @RequestParam(required = false, defaultValue = DEFAULT_DISTANCE) Double distance,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {
        logger.info("VehicleController:searchVehicles - Search vehicle list by details.");

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("listingDate").descending());
        SearchVehiclesResponse searchVehiclesResponse = vehicleBasicInfoServiceImpl.searchVehicles(
                make, model, minYear, maxYear, trim, mileage, minPrice, maxPrice, color, bodyType, engineType,
                transmission, drivetrain, address, condition, capacity, features, distance, pageable);

        return getApiResponseEntityResponseEntity(searchVehiclesResponse);
    }

    private ResponseEntity<ApiResponseEntity> getApiResponseEntityResponseEntity(SearchVehiclesResponse searchVehiclesResponse) {
        if (searchVehiclesResponse.getVehicles().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        PagedModel<EntityModel<VehicleBasicInfoDTO>> vehicleBasicInfos = pagedResourcesAssembler.toModel(searchVehiclesResponse.getVehicles());
        ApiResponseEntity apiResponseEntity = new ApiResponseEntity(searchVehiclesResponse.getSearchType(), vehicleBasicInfos);
        return ResponseEntity.ok(apiResponseEntity);
    }
}

package com.tboostintelli_core.service;

import com.google.maps.model.LatLng;
import com.tboostintelli_core.dto.VehicleBasicInfoDTO;
import com.tboostintelli_core.entity.Location;
import com.tboostintelli_core.entity.VehicleBasicInfo;
import com.tboostintelli_core.entity.request_entity.SearchVehicleListRequest;
import com.tboostintelli_core.mapper.VehicleMapper;
import com.tboostintelli_core.repository.VehicleRepo;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static com.tboostintelli_core.common.GeneralConstants.*;

@Service
public class VehicleBasicInfoService {


    @Resource
    private VehicleRepo vehicleRepo;
    @Resource
    private GoogleGeocodingService googleGeocodingService;

    public VehicleBasicInfoDTO getVehicleByUuid(Long uuid) {
        Optional<VehicleBasicInfo> vehicle = vehicleRepo.getVehicleByUuid(uuid);
        return vehicle.map(VehicleMapper.INSTANCE::vehicleBasicInfoToVehicleBasicInfoDTO).orElse(null);
    }


    public Page<VehicleBasicInfoDTO> searchVehicles(String make, String model, Integer minYear, Integer maxYear, String trim, Integer mileage,
                                                    Double minPrice, Double maxPrice, String color, String bodyType, String engineType,
                                                    String transmission, String drivetrain, String address, String condition, Integer capacity,
                                                    List<String> features, int distance, Pageable pageable) {


        LatLng latLng = googleGeocodingService.getLatLngFromAddress(address).block();

        if (latLng == null) {
            throw new RuntimeException("Failed to extract longitude and latitude from Google Map Geocoding API response.");
        }

        SearchVehicleListRequest searchVehicleListRequest = new SearchVehicleListRequest(make, model, minYear, maxYear, trim, mileage, minPrice, maxPrice, color, bodyType,
                engineType, transmission, drivetrain, latLng.lng, latLng.lat, condition, capacity, features, distance);
        // Precise Search
        Page<VehicleBasicInfoDTO> result = executeSearch(searchVehicleListRequest, pageable);

        // Relaxed Search
        if (result.isEmpty()) {
            relaxSearchParams(searchVehicleListRequest);
            result = executeSearch(searchVehicleListRequest, pageable);
        }

        return result;
    }

    private void relaxSearchParams(SearchVehicleListRequest searchVehicleListRequest) {
        searchVehicleListRequest.setFeatures(null);
        searchVehicleListRequest.setCapacity(null);
        searchVehicleListRequest.setColor(ALL);
        searchVehicleListRequest.setModel(ALL);
        searchVehicleListRequest.setDistance(searchVehicleListRequest.getDistance() * RELAX_SEARCH_DISTANCE_RATE);
        searchVehicleListRequest.setMaxPrice(searchVehicleListRequest.getMaxPrice() * RELAX_SEARCH_MAX_PRICE_RATE);
        searchVehicleListRequest.setMinPrice(searchVehicleListRequest.getMinPrice() * RELAX_SEARCH_MIN_PRICE_RATE);
        searchVehicleListRequest.setMileage((int) (searchVehicleListRequest.getMileage() * RELAX_SEARCH_MILEAGE_RATE));
        searchVehicleListRequest.setTrim(ALL);
        searchVehicleListRequest.setMinYear(searchVehicleListRequest.getMinYear() - RELAX_SEARCH_YEAR_RATE);
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        searchVehicleListRequest.setMaxYear(Math.min(searchVehicleListRequest.getMaxYear() + RELAX_SEARCH_YEAR_RATE, currentYear));
    }

    private Page<VehicleBasicInfoDTO> executeSearch(SearchVehicleListRequest searchVehicleListRequest, Pageable pageable) {
        Specification<VehicleBasicInfo> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtering by vehicle_basic_info fields
            if (!ALL.equalsIgnoreCase(searchVehicleListRequest.getMake())) {
                predicates.add(cb.equal(root.get("make"), searchVehicleListRequest.getMake()));
            }
            if (!ALL.equalsIgnoreCase(searchVehicleListRequest.getModel())) {
                predicates.add(cb.equal(root.get("model"), searchVehicleListRequest.getModel()));
            }
            if (searchVehicleListRequest.getMinYear() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("year"), searchVehicleListRequest.getMinYear()));
            }
            if (searchVehicleListRequest.getMaxYear() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("year"), searchVehicleListRequest.getMaxYear()));
            }
            if (!ALL.equalsIgnoreCase(searchVehicleListRequest.getTrim())) {
                predicates.add(cb.equal(root.get("trim"), searchVehicleListRequest.getTrim()));
            }
            if (searchVehicleListRequest.getMileage() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("mileage"), searchVehicleListRequest.getMileage()));
            }
            if (searchVehicleListRequest.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), searchVehicleListRequest.getMinPrice()));
            }
            if (searchVehicleListRequest.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), searchVehicleListRequest.getMaxPrice()));
            }
            if (!ALL.equalsIgnoreCase(searchVehicleListRequest.getColor())) {
                predicates.add(cb.equal(root.get("color"), searchVehicleListRequest.getColor()));
            }
            if (!ALL.equalsIgnoreCase(searchVehicleListRequest.getBodyType())) {
                predicates.add(cb.equal(root.get("bodyType"), searchVehicleListRequest.getBodyType()));
            }
            if (!ALL.equalsIgnoreCase(searchVehicleListRequest.getEngineType())) {
                predicates.add(cb.equal(root.get("engineType"), searchVehicleListRequest.getEngineType()));
            }
            if (!ALL.equalsIgnoreCase(searchVehicleListRequest.getTransmission())) {
                predicates.add(cb.equal(root.get("transmission"), searchVehicleListRequest.getTransmission()));
            }
            if (!ALL.equalsIgnoreCase(searchVehicleListRequest.getDrivetrain())) {
                predicates.add(cb.equal(root.get("drivetrain"), searchVehicleListRequest.getDrivetrain()));
            }

            if (!ALL.equalsIgnoreCase(searchVehicleListRequest.getCondition())) {
                predicates.add(cb.equal(root.get("condition"), searchVehicleListRequest.getCondition()));
            }

            if (searchVehicleListRequest.getCapacity() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("capacity"), searchVehicleListRequest.getCapacity()));
            }

            // Join with features and filtering by features
            if (searchVehicleListRequest.getFeatures() != null && !searchVehicleListRequest.getFeatures().isEmpty()) {
                Join<Object, Object> featureJoin = root.join("features");
                predicates.add(featureJoin.get("name").in(searchVehicleListRequest.getFeatures()));
            }

            double maxDistanceInMeters = searchVehicleListRequest.getDistance() * KM2METER_RATE;
            // Join location table to calculate distance
            Join<VehicleBasicInfo, Location> locationJoin = root.join("location");
            Expression<Double> distanceExpression = cb.function("ST_Distance_Sphere", Double.class,
                    cb.function("POINT", Object.class, locationJoin.get("longitude"), locationJoin.get("latitude")),
                    cb.function("POINT", Object.class, cb.literal(searchVehicleListRequest.getLongitude()), cb.literal(searchVehicleListRequest.getLatitude()))
            );

            Predicate distancePredicate = cb.lessThanOrEqualTo(distanceExpression, maxDistanceInMeters);
            predicates.add(distancePredicate);

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<VehicleBasicInfo> vehicleBasicInfos = vehicleRepo.findAll(spec, pageable);

        return vehicleBasicInfos.map(VehicleMapper.INSTANCE::vehicleBasicInfoToVehicleBasicInfoDTO);
    }
}

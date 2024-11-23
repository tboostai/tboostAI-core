package com.tboostAI_core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.LatLng;
import com.tboostAI_core.dto.VehicleBasicInfoDTO;
import com.tboostAI_core.entity.LocationEntity;
import com.tboostAI_core.entity.VehicleBasicInfoEntity;
import com.tboostAI_core.entity.VehiclePriceEntity;
import com.tboostAI_core.entity.inner_model.VehicleBasicInfo;
import com.tboostAI_core.entity.request_entity.Message;
import com.tboostAI_core.entity.request_entity.SearchVehicleListRequest;
import com.tboostAI_core.mapper.SearchParamsMapper;
import com.tboostAI_core.mapper.VehicleInfoMapper;
import com.tboostAI_core.repository.VehicleRepo;
import com.tboostAI_core.utils.CommonUtils;
import com.tboostAI_core.utils.WebClientUtils;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

import static com.tboostAI_core.common.GeneralConstants.*;

@Slf4j
@Service
public class VehicleBasicInfoService {

    private final VehicleRepo vehicleRepo;
    private final GoogleGeocodingService googleGeocodingService;
    private final WebClientUtils webClientUtils;
    private final RedisServiceForOpenAI redisServiceForOpenAI;
    private final ObjectMapper objectMapper;

    @Value("${Spring.microserver.service.tboostai.llm.host}")
    private String tboostAILlmHost;

    @Autowired
    public VehicleBasicInfoService(VehicleRepo vehicleRepo, GoogleGeocodingService googleGeocodingService, WebClientUtils webClientUtils, RedisServiceForOpenAI redisServiceForOpenAI) {
        this.vehicleRepo = vehicleRepo;
        this.googleGeocodingService = googleGeocodingService;
        this.webClientUtils = webClientUtils;
        this.redisServiceForOpenAI = redisServiceForOpenAI;
        this.objectMapper = new ObjectMapper();
    }

    private static final Logger logger = LoggerFactory.getLogger(VehicleBasicInfoService.class);

    public VehicleBasicInfoDTO getVehicleByUuid(Long uuid) {
        Optional<VehicleBasicInfoEntity> vehicle = vehicleRepo.getVehicleByUuid(uuid);
        VehicleBasicInfo vehicleBasicInfo = vehicle.map(VehicleInfoMapper.INSTANCE::toVehicleBasicInfo).orElse(null);
        return VehicleInfoMapper.INSTANCE.toVehicleBasicInfoDTO(vehicleBasicInfo);
    }


    public Page<VehicleBasicInfoDTO> searchVehicles(List<String> make, List<String> model,
                                                    Integer minYear, Integer maxYear,
                                                    List<String> trim, Integer mileage,
                                                    Double minPrice, Double maxPrice,
                                                    List<String> color, List<String> bodyType, List<String> engineType,
                                                    List<String> transmission, List<String> drivetrain,
                                                    String address, List<String> condition, Integer capacity,
                                                    List<String> features, int distance, Pageable pageable) {


        LatLng latLng = googleGeocodingService.getLatLngFromAddress(address).block();

        if (latLng == null) {
            throw new RuntimeException("Failed to extract longitude and latitude from Google Map Geocoding API response.");
        }

        SearchVehicleListRequest searchVehicleListRequest = SearchVehicleListRequest.builder()
                .make(make).model(model)
                .minYear(minYear).maxYear(maxYear)
                .trim(trim).mileage(mileage)
                .minPrice(minPrice).maxPrice(maxPrice)
                .color(color).bodyType(bodyType)
                .engineType(engineType).transmission(transmission).drivetrain(drivetrain)
                .longitude(latLng.lng).latitude(latLng.lat)
                .condition(condition).capacity(capacity)
                .features(features).distance(distance)
                .build();

        logger.info("Search Vehicle list request: {}", searchVehicleListRequest);

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
        searchVehicleListRequest.setColor(null);
        searchVehicleListRequest.setModel(null);
        searchVehicleListRequest.setDistance(searchVehicleListRequest.getDistance() * RELAX_SEARCH_DISTANCE_RATE);
        searchVehicleListRequest.setMaxPrice(searchVehicleListRequest.getMaxPrice() * RELAX_SEARCH_MAX_PRICE_RATE);
        searchVehicleListRequest.setMinPrice(searchVehicleListRequest.getMinPrice() * RELAX_SEARCH_MIN_PRICE_RATE);
        searchVehicleListRequest.setMileage((int) (searchVehicleListRequest.getMileage() * RELAX_SEARCH_MILEAGE_RATE));
        searchVehicleListRequest.setTrim(null);
        searchVehicleListRequest.setMinYear(searchVehicleListRequest.getMinYear() - RELAX_SEARCH_YEAR_RATE);
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        searchVehicleListRequest.setMaxYear(Math.min(searchVehicleListRequest.getMaxYear() + RELAX_SEARCH_YEAR_RATE, currentYear));
    }

    private Page<VehicleBasicInfoDTO> executeSearch(SearchVehicleListRequest searchVehicleListRequest, Pageable pageable) {
        Specification<VehicleBasicInfoEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtering by vehicle_basic_info fields
            if (searchVehicleListRequest.getMake() != null && !searchVehicleListRequest.getMake().isEmpty()) {
                predicates.add(root.get("make").in(searchVehicleListRequest.getMake()));
            }
            if (searchVehicleListRequest.getModel() != null && !searchVehicleListRequest.getModel().isEmpty()) {
                predicates.add(root.get("model").in(searchVehicleListRequest.getModel()));
            }
            if (searchVehicleListRequest.getMinYear() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("year"), searchVehicleListRequest.getMinYear()));
            }
            if (searchVehicleListRequest.getMaxYear() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("year"), searchVehicleListRequest.getMaxYear()));
            }
            if (searchVehicleListRequest.getTrim() != null && !searchVehicleListRequest.getTrim().isEmpty()) {
                predicates.add(root.get("trim").in(searchVehicleListRequest.getTrim()));
            }
            if (searchVehicleListRequest.getMileage() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("mileage"), searchVehicleListRequest.getMileage()));
            }
            if (searchVehicleListRequest.getMinPrice() != null || searchVehicleListRequest.getMaxPrice() != null) {
                // 子查询获取 priceType = 1 的现价
                Subquery<Double> subqueryPriceType1 = Objects.requireNonNull(query).subquery(Double.class);
                Root<VehiclePriceEntity> priceRoot1 = subqueryPriceType1.from(VehiclePriceEntity.class);
                subqueryPriceType1.select(priceRoot1.get("price").as(Double.class))
                        .where(cb.equal(priceRoot1.get("vehicle"), root),
                                cb.equal(priceRoot1.get("priceType"), 1)); // priceType = 1

                // 子查询获取 priceType = 0 的车价
                Subquery<Double> subqueryPriceType0 = query.subquery(Double.class);
                Root<VehiclePriceEntity> priceRoot0 = subqueryPriceType0.from(VehiclePriceEntity.class);
                subqueryPriceType0.select(priceRoot0.get("price").as(Double.class))
                        .where(cb.equal(priceRoot0.get("vehicle"), root),
                                cb.equal(priceRoot0.get("priceType"), 0)); // priceType = 0

                // 使用 coalesce 函数，如果 priceType = 1 的现价不存在，则使用 priceType = 0 的车价
                Expression<Double> priceExpression = cb.coalesce(subqueryPriceType1.getSelection(), subqueryPriceType0.getSelection());

                // 应用最小价格过滤
                if (searchVehicleListRequest.getMinPrice() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(priceExpression, searchVehicleListRequest.getMinPrice()));
                }

                // 应用最大价格过滤
                if (searchVehicleListRequest.getMaxPrice() != null) {
                    predicates.add(cb.lessThanOrEqualTo(priceExpression, searchVehicleListRequest.getMaxPrice()));
                }
            }

            if (searchVehicleListRequest.getColor() != null && !searchVehicleListRequest.getColor().isEmpty()) {
                predicates.add(root.get("exteriorColor").in(searchVehicleListRequest.getColor()));
            }
            if (searchVehicleListRequest.getBodyType() != null && !searchVehicleListRequest.getBodyType().isEmpty()) {
                predicates.add(root.get("bodyType").in(searchVehicleListRequest.getBodyType()));
            }
            if (searchVehicleListRequest.getEngineType() != null && !searchVehicleListRequest.getEngineType().isEmpty()) {
                predicates.add(root.get("engineType").in(searchVehicleListRequest.getEngineType()));
            }
            if (searchVehicleListRequest.getTransmission() != null && !searchVehicleListRequest.getTransmission().isEmpty()) {
                predicates.add(root.get("transmission").in(searchVehicleListRequest.getTransmission()));
            }
            if (searchVehicleListRequest.getDrivetrain() != null && !searchVehicleListRequest.getDrivetrain().isEmpty()) {
                predicates.add(root.get("drivetrain").in(searchVehicleListRequest.getDrivetrain()));
            }

            if (searchVehicleListRequest.getCondition() != null && !searchVehicleListRequest.getCondition().isEmpty()) {
                predicates.add(root.get("vehicleCondition").in(searchVehicleListRequest.getCondition()));
            }

            if (searchVehicleListRequest.getCapacity() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("capacity"), searchVehicleListRequest.getCapacity()));
            }

            // Join with features and filtering by features
            if (searchVehicleListRequest.getFeatures() != null && !searchVehicleListRequest.getFeatures().isEmpty()) {
                Join<Object, Object> featureJoin = root.join("features");
                predicates.add(featureJoin.get("name").in(searchVehicleListRequest.getFeatures()));
            }

            if (searchVehicleListRequest.getDistance() != null && searchVehicleListRequest.getDistance() > 0) {

                double maxDistanceInMeters = searchVehicleListRequest.getDistance() * KM2METER_RATE;
                // Join location table to calculate distance
                Join<VehicleBasicInfoEntity, LocationEntity> locationJoin = root.join("locationEntity");
                Expression<Double> distanceExpression = cb.function("ST_Distance_Sphere", Double.class,
                        cb.function("POINT", Object.class, locationJoin.get("longitude"), locationJoin.get("latitude")),
                        cb.function("POINT", Object.class, cb.literal(searchVehicleListRequest.getLongitude()), cb.literal(searchVehicleListRequest.getLatitude()))
                );

                Predicate distancePredicate = cb.lessThanOrEqualTo(distanceExpression, maxDistanceInMeters);
                predicates.add(distancePredicate);
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        List<VehicleBasicInfoEntity> vehicleBasicInfoEntities = vehicleRepo.findAll(spec);

        List<VehicleBasicInfo> vehicleBasicInfos = vehicleBasicInfoEntities.stream().map(VehicleInfoMapper.INSTANCE::toVehicleBasicInfo).toList();
        List<VehicleBasicInfoDTO> vehicleBasicInfoDTOS = vehicleBasicInfos.stream().map(VehicleInfoMapper.INSTANCE::toVehicleBasicInfoDTO).toList();

        return CommonUtils.listToPage(vehicleBasicInfoDTOS, pageable);
    }

    public Page<VehicleBasicInfoDTO> searchVehiclesByLLM(String sessionId, Double minPrice, Double maxPrice, List<String> bodyType, List<String> engineType, String content, String address, int distance, Pageable pageable) {

        Result result = generateMultipleInfo(sessionId, content, address);

        return result.responseObj()
                .map(SearchParamsMapper.INSTANCE::mapWithDefaultValues)
                .flatMap(processedRequest -> {
                    try {
                        // Save user message history to Redis
                        redisServiceForOpenAI.saveMessageToList(sessionId, result.newUserMessage());
                        String processedRequestJsonStr = objectMapper.writeValueAsString(processedRequest);
                        logger.info("processedRequestJsonStr: {}", processedRequestJsonStr);

                        // Generate and save assistant message to Redis
                        Message assistantMessage = generateMessage(OPENAI_ASSISTANT, processedRequestJsonStr);
                        logger.info("assistantMessage : {}", assistantMessage);
                        redisServiceForOpenAI.saveMessageToList(sessionId, assistantMessage);
                    } catch (JsonProcessingException e) {
                        logger.error("Failed to convert processedRequest to Json string", e);
                    }

                    // Replace fields based on whether front-end input is present, or keep original values
                    SearchVehicleListRequest updatedRequest = processedRequest.toBuilder()
                            .minPrice(minPrice != null ? minPrice : processedRequest.getMinPrice())
                            .maxPrice(maxPrice != null ? maxPrice : processedRequest.getMaxPrice())
                            .bodyType(bodyType != null && !bodyType.isEmpty() ? bodyType : processedRequest.getBodyType())
                            .engineType(engineType != null && !engineType.isEmpty() ? engineType : processedRequest.getEngineType())
                            .longitude(result.latLng() != null ? result.latLng().lng : processedRequest.getLongitude())
                            .latitude(result.latLng() != null ? result.latLng().lat : processedRequest.getLatitude())
                            .distance(distance > 0 ? distance : processedRequest.getDistance())
                            .build();

                    logger.info("Updated request: {}", updatedRequest);

                    return Mono.fromCallable(() -> executeSearch(updatedRequest, pageable));
                })
                .doOnError(e -> logger.error("Error occurred: {}", e.getMessage()))
                .block();
    }

    @NotNull
    private Result generateMultipleInfo(String sessionId, String content, String address) {
        List<Message> messages = new ArrayList<>();
        List<Object> messagesHistory = redisServiceForOpenAI.getChatHistoryList(sessionId);

        for (Object message : messagesHistory) {
            if (message instanceof Message) {
                messages.add((Message) message);
            }
        }

        logger.info("Message History Size: {}", messages.size());
        Message newUserMessage = generateMessage(OPENAI_USER, content);
        messages.add(newUserMessage);

        Mono<SearchVehicleListRequest> responseObj = webClientUtils.sendPostRequestInternal(tboostAILlmHost, messages, SearchVehicleListRequest.class);

        LatLng latLng = googleGeocodingService.getLatLngFromAddress(address).block();
        return new Result(newUserMessage, responseObj, latLng);
    }

    private record Result(Message newUserMessage, Mono<SearchVehicleListRequest> responseObj, LatLng latLng) {
    }

    private Message generateMessage(String openAiRole, String content) {
        Message newMessage = new Message();
        newMessage.setContent(content);
        newMessage.setRole(openAiRole);

        return newMessage;
    }

    public String createNewSessionForChat() {

        return redisServiceForOpenAI.createNewSessionForChat();
    }

    public void deleteCurrentSessionForChat(String sessionId) {

        redisServiceForOpenAI.deleteSession(sessionId);
    }
}

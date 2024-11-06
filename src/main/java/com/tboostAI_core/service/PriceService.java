package com.tboostAI_core.service;

import com.tboostAI_core.dto.VehiclePriceDTO;
import com.tboostAI_core.entity.VehiclePriceEntity;
import com.tboostAI_core.entity.inner_model.VehiclePrice;
import com.tboostAI_core.mapper.VehiclePriceMapper;
import com.tboostAI_core.repository.PriceRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {

    private final PriceRepo priceRepo;

    public PriceService(PriceRepo priceRepo) {
        this.priceRepo = priceRepo;
    }

    public List<VehiclePriceDTO> findAllPricesByVehicleId(Long vehicleId) {
        List<VehiclePriceEntity> priceEntities = priceRepo.findAllPricesByVehicleUuid(vehicleId);
        List<VehiclePrice> vehiclePrices = VehiclePriceMapper.INSTANCE.toVehiclePriceList(priceEntities);

        return VehiclePriceMapper.INSTANCE.toVehiclePriceDTOList(vehiclePrices);
    }
}

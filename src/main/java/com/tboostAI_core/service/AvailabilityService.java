package com.tboostAI_core.service;

import com.tboostAI_core.dto.AvailabilityDTO;
import com.tboostAI_core.entity.AvailabilityEntity;
import com.tboostAI_core.entity.inner_model.Availability;
import com.tboostAI_core.mapper.AvailabilityMapper;
import com.tboostAI_core.repository.AvailabilityRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AvailabilityService {

    private final AvailabilityRepo availabilityRepo;

    public AvailabilityService(AvailabilityRepo availabilityRepo) {
        this.availabilityRepo = availabilityRepo;
    }

    public AvailabilityDTO getAvailabilityById(Long uuid) {
        Optional<AvailabilityEntity> availabilityEntity = availabilityRepo.findById(uuid);
        if (availabilityEntity.isPresent()) {
            Availability availability = AvailabilityMapper.INSTANCE.toAvailability(availabilityEntity.get());
            return AvailabilityMapper.INSTANCE.toAvailabilityDTO(availability);
        }
        return null;
    }
}

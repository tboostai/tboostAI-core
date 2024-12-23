package com.tboostAI_core.service;

import com.tboostAI_core.dto.AvailabilityDTO;

public interface AvailabilityService {
    AvailabilityDTO getAvailabilityById(Long uuid);
}

package com.tboostAI_core.service;

import com.tboostAI_core.dto.PostDTO;

public interface PostService {
    PostDTO getPostByVehicleId(Long vehicleId);
}

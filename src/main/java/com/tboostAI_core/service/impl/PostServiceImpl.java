package com.tboostAI_core.service.impl;

import com.tboostAI_core.dto.PostDTO;
import com.tboostAI_core.entity.PostEntity;
import com.tboostAI_core.entity.inner_model.VehiclePostInfo;
import com.tboostAI_core.mapper.VehiclePostInfoMapper;
import com.tboostAI_core.repository.PostRepo;
import com.tboostAI_core.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    public final PostRepo postRepo;
    public PostServiceImpl(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    public PostDTO getPostByVehicleId(Long vehicleId) {
        PostEntity postEntity = postRepo.findByVehicleUuid(vehicleId);
        VehiclePostInfo vehiclePostInfo = VehiclePostInfoMapper.INSTANCE.toVehiclePostInfo(postEntity);

        return VehiclePostInfoMapper.INSTANCE.toPostDTO(vehiclePostInfo);
    }
}

package com.tboostAI_core.service.impl;

import com.tboostAI_core.dto.SellerDTO;
import com.tboostAI_core.entity.SellerEntity;
import com.tboostAI_core.entity.inner_model.Seller;
import com.tboostAI_core.mapper.EbaySellerMapper;
import com.tboostAI_core.repository.SellerRepo;
import com.tboostAI_core.service.SellerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepo sellerRepo;

    public SellerServiceImpl(SellerRepo sellerRepo) {
        this.sellerRepo = sellerRepo;
    }

    public SellerDTO getSellerById(Long uuid) {
        Optional<SellerEntity> sellerEntity = sellerRepo.findById(uuid);
        if (sellerEntity.isPresent()) {
            Seller seller = EbaySellerMapper.INSTANCE.toSeller(sellerEntity.get());
            return EbaySellerMapper.INSTANCE.toSellerDTO(seller);
        }
        return null;
    }
}

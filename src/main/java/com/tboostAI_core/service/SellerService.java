package com.tboostAI_core.service;

import com.tboostAI_core.dto.SellerDTO;
import com.tboostAI_core.entity.SellerEntity;
import com.tboostAI_core.entity.inner_model.Seller;
import com.tboostAI_core.mapper.EbaySellerMapper;
import com.tboostAI_core.repository.SellerRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerService {

    private final SellerRepo sellerRepo;

    public SellerService(SellerRepo sellerRepo) {
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

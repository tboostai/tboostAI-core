package com.tboostAI_core.mapper;

import com.tboostAI_core.dto.TaxDTO;
import com.tboostAI_core.entity.TaxEntity;
import com.tboostAI_core.entity.inner_model.Tax;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        uses = {
                TaxJurisdictionMapper.class,
                RegionMapper.class
        }
)
public interface TaxMapper {

        @Mapping(target = "taxJurisdiction.taxJurisdictionId", source = "taxJurisdictionId")
        Tax toTax(TaxEntity taxEntity);
        List<Tax> toTaxList(List<TaxEntity> taxEntities);

        TaxDTO toTaxDTO(Tax tax);
        List<TaxDTO> toTaxDTOList(List<Tax> taxes);
}

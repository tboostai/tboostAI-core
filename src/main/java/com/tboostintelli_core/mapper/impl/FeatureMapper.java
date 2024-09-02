package com.tboostintelli_core.mapper.impl;

import com.tboostintelli_core.entity.VehicleFeature;
import com.tboostintelli_core.mapper.FeatureToStringMapping;

import java.util.List;
import java.util.stream.Collectors;

public class FeatureMapper {
    @FeatureToStringMapping
    public List<String> mapFeaturesToString(List<VehicleFeature> features) {
        return features.stream()
                .map(VehicleFeature::getName)
                .collect(Collectors.toList());
    }
}

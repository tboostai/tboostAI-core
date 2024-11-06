package com.tboostAI_core.controller;

import com.tboostAI_core.dto.AvailabilityDTO;
import com.tboostAI_core.service.AvailabilityService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Resource
    private AvailabilityService availabilityService;

    @GetMapping("/{uuid}")
    public ResponseEntity<AvailabilityDTO> getAvailabilityById(@PathVariable Long uuid) {
        AvailabilityDTO availabilityDTO = availabilityService.getAvailabilityById(uuid);
        return availabilityDTO != null ? ResponseEntity.ok(availabilityDTO) : ResponseEntity.notFound().build();
    }
}

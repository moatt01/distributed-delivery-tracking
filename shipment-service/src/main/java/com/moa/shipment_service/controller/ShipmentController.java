package com.moa.shipment_service.controller;

import com.moa.shipment_service.domain.ShipmentStatus;
import com.moa.shipment_service.dto.CreateShipmentRequest;
import com.moa.shipment_service.dto.ShipmentResponse;
import com.moa.shipment_service.entity.Shipment;
import com.moa.shipment_service.service.ShipmentService;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipments")
public class ShipmentController {

    private final ShipmentService service;

    public ShipmentController(ShipmentService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShipmentResponse create(@Valid @RequestBody CreateShipmentRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public ShipmentResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }
}


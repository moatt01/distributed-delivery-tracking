package com.moa.shipment_service.controller;

import com.moa.shipment_service.domain.ShipmentStatus;
import com.moa.shipment_service.dto.CreateShipmentRequest;
import com.moa.shipment_service.dto.PageResponse;
import com.moa.shipment_service.dto.ShipmentResponse;
import com.moa.shipment_service.entity.Shipment;
import com.moa.shipment_service.mapper.ShipmentMapper;
import com.moa.shipment_service.service.ShipmentService;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipments")
public class ShipmentController {

    private final ShipmentService service;
    private final ShipmentMapper shipmentMapper;

    public ShipmentController(ShipmentService service, ShipmentMapper shipmentMapper) {
        this.service = service;
        this.shipmentMapper = shipmentMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShipmentResponse create(@Valid @RequestBody CreateShipmentRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public ShipmentResponse getById(@PathVariable UUID id) {
        Shipment shipment = service.getById(id);
        return shipmentMapper.toResponse(shipment);
    }
    @GetMapping
    public PageResponse<ShipmentResponse> list(// @PageableDefault(size = 10, sort = "createdAt")
                                               // show pageable fields in swagger
                                               @ParameterObject Pageable pageable
    ) {
        return service.list(pageable);
    }

}
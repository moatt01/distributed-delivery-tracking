package com.moa.shipment_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ShipmentResponse(

        String trackingNumber,
        String status,
        LocalDateTime createdAt,
        String originCity,
        String destinationCity
) {}


package com.moa.shipment_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateShipmentRequest (
        @NotBlank
        @Size(max = 120)
        String senderName,

        @NotBlank
        @Size(max = 120)
        String receiverName,

        @NotBlank
        @Size(max = 120)
        String originCity,

        @NotBlank
        @Size(max = 120)
        String destinationCity)
{}

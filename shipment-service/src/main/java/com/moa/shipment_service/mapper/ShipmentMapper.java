package com.moa.shipment_service.mapper;

import com.moa.shipment_service.dto.CreateShipmentRequest;
import com.moa.shipment_service.dto.ShipmentResponse;
import com.moa.shipment_service.entity.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {

    // Entity -> Response DTO
    @Mapping(target = "status", expression = "java(shipment.getStatus().name())")
    ShipmentResponse toResponse(Shipment shipment);

    // Request DTO -> Entity (fields that come from client)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trackingNumber", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Shipment toEntity(CreateShipmentRequest request);
}

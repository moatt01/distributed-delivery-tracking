package com.moa.shipment_service.service;

import com.moa.shipment_service.domain.ShipmentStatus;
import com.moa.shipment_service.dto.CreateShipmentRequest;
import com.moa.shipment_service.dto.ShipmentResponse;
import com.moa.shipment_service.entity.Shipment;
import com.moa.shipment_service.mapper.ShipmentMapper;
import com.moa.shipment_service.repository.ShipmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class ShipmentService {
    private final ShipmentRepository ShipmentRepo;
    private final ShipmentMapper mapper;

    public ShipmentService(ShipmentRepository ShipmentRepo, ShipmentMapper mapper) {
        this.ShipmentRepo = ShipmentRepo;
        this.mapper = mapper;
    }

    public ShipmentResponse create(CreateShipmentRequest request) {

        Shipment shipment = mapper.toEntity(request);

        shipment.setTrackingNumber(UUID.randomUUID().toString());
        shipment.setStatus(ShipmentStatus.CREATED);
        shipment.setCreatedAt(LocalDateTime.now());

        Shipment saved = ShipmentRepo.save(shipment);

        return mapper.toResponse(saved);
    }

    public ShipmentResponse getById(UUID id) {
        Shipment shipment = ShipmentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found: " + id));

        return mapper.toResponse(shipment);
    }
    public Shipment updateStatus(UUID shipmentId, ShipmentStatus newStatus) {

        Shipment shipment = ShipmentRepo.findById(shipmentId)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found"));

        ShipmentStatus current = shipment.getStatus();

        if (!isValidTransition(current, newStatus)) {
            throw new IllegalStateException("Invalid status transition");
        }

        shipment.setStatus(newStatus);

        return ShipmentRepo.save(shipment);
    }
    private boolean isValidTransition(ShipmentStatus current, ShipmentStatus next) {

        return switch (current) {

            case CREATED -> next == ShipmentStatus.ASSIGNED || next == ShipmentStatus.CANCELLED;

            case ASSIGNED -> next == ShipmentStatus.PICKED_UP;

            case PICKED_UP -> next == ShipmentStatus.IN_TRANSIT;

            case IN_TRANSIT -> next == ShipmentStatus.OUT_FOR_DELIVERY;

            case OUT_FOR_DELIVERY -> next == ShipmentStatus.DELIVERED;

            default -> false;
        };
    }
}

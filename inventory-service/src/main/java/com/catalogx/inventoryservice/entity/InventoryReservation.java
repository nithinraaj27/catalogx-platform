package com.catalogx.inventoryservice.entity;

import com.catalogx.inventoryservice.dto.ReservationResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private Integer quantityReserved;

    @Column(nullable = false)
    private String orderId;

    private LocalDateTime reservedAt;

    public ReservationResponse toResponse(){
        return new ReservationResponse(
                id,
                sku,
                quantityReserved,
                orderId,
                reservedAt
        );
    }
}

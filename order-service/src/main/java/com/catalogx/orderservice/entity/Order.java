package com.catalogx.orderservice.entity;

import com.catalogx.orderservice.dto.OrderResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderResponse toResponse() {
        return new OrderResponse(
                this.orderId,
                this.sku,
                this.quantity,
                this.status.name(),
                this.createdAt,
                this.updatedAt
        );
    }
}

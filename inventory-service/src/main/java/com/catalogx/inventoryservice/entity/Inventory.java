package com.catalogx.inventoryservice.entity;

import com.catalogx.inventoryservice.dto.InventoryResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private Integer totalQuantity;

    @Column(nullable = false)
    private Integer reservedQuantity;

    private LocalDateTime updatedAt;

    public InventoryResponse toResponse()
    {
        int available = totalQuantity - reservedQuantity;

        return new InventoryResponse(
                sku,
                totalQuantity,
                reservedQuantity,
                available,
                updatedAt
        );
    }
}

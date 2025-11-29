package com.catalogx.inventoryservice.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(
        boolean success,
        String message,
        String path,
        int status
) {
}

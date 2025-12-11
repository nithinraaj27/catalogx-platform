package com.catalogx.searchservice.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeProjection {

    private Long attributeId;
    private String key;
    private String value;
}

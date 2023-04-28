package com.example.codelabsvc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

abstract class EntityBase {

    private String createdAt;

    private String updatedAt;

    private String createdBy;

    private String updateBy;
}

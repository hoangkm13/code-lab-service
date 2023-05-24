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

    public EntityBase(String createdAt, String updatedAt, String createdBy, String updateBy) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updateBy = updateBy;
    }
}

package com.samsoft.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseEntity {

    protected LocalDateTime createdOn;
    protected LocalDateTime updatedOn;
}

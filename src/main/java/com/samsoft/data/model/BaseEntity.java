package com.samsoft.data.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@EnableJdbcAuditing
public abstract class BaseEntity {

    @Id
    protected Long id;
    @Version
    protected int version;
    @CreatedDate
    protected LocalDate createdOn;
    @LastModifiedDate
    protected LocalDateTime updatedOn;
    protected String tenantId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
               "id=" + id +
               '}';
    }
}

package com.samsoft.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TenantAwareBaseEntity extends BaseEntity {

    protected String tenantId;
}

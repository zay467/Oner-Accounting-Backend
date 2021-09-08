package com.thamardaw.oner.entity.request;

import lombok.Data;

@Data
public class ServiceItemRequest {
    private String name;
    private long relationshipId;
    private String serviceType;
    private String incomeDepartment;
    private long charge;
}

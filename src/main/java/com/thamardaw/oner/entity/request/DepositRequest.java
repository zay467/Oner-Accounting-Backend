package com.thamardaw.oner.entity.request;

import lombok.Data;

@Data
public class DepositRequest {
    private Long patientId;
    private String status;
    private long amount;
}

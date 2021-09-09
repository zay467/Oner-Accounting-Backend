package com.thamardaw.oner.entity.request;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long patientId;
    private Long billId;
    private long amount;
}

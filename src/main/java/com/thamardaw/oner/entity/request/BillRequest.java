package com.thamardaw.oner.entity.request;

import lombok.Data;

@Data
public class BillRequest {
    private Long patientId;
    private long totalAmount;
    private long depositAmount;
    private long unpaidAmount;
}

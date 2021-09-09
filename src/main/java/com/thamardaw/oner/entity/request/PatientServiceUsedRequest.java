package com.thamardaw.oner.entity.request;

import lombok.Data;

@Data
public class PatientServiceUsedRequest {
    private long serviceItemId;
    private long patientId;
    private long quantity;
    private long charge;
    private long totalCharge;
    private String status;
    private String extra;
}

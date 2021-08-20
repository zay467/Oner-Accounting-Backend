package com.thamardaw.oner.entity.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class LedgerEntryRequest {
    private Timestamp date;
    private String reference;
    private String explanation;
    private long debit;
    private long credit;
    private long account_id;
}

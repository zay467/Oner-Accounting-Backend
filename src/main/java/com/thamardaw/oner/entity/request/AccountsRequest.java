package com.thamardaw.oner.entity.request;

import lombok.Data;

@Data
public class AccountsRequest {
    private String code;
    private String account_name;
    private String description;
    private long category_id;
    private long account_type_id;
}

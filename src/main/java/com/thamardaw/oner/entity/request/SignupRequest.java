package com.thamardaw.oner.entity.request;

import lombok.Data;

@Data
public class SignupRequest {
    private String fullName;
    private String userName;
    private String password;
}

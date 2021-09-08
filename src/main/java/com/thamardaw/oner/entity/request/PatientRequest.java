package com.thamardaw.oner.entity.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

@Data
public class PatientRequest {
    private String name;
    private int age;
    private String phone;
    private String nrc;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private String address;
    private String gender;
    private String status;
}

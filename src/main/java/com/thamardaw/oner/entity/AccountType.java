package com.thamardaw.oner.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "AccountType")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Timestamp createdTime;

    @Column
    private Timestamp updatedTime;
}

package com.thamardaw.oner.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String code;

    @Column
    private String accountName;

    @Column
    private String description;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    @Column(insertable = false,updatable = false)
    private Long categoryId;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountTypeId")
    private AccountType accountType;

    @Column(insertable = false,updatable = false)
    private Long accountTypeId;

    @Column
    private Timestamp createdTime;

    @Column
    private Timestamp updatedTime;
}

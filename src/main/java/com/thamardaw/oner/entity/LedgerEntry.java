package com.thamardaw.oner.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "LedgerEntry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LedgerEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private Timestamp date;

    @Column
    private String reference;

    @Column
    private String explanation;

    @Column
    private long debit;

    @Column
    private long credit;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountsId")
    private Accounts accounts;

    @Column(insertable = false,updatable = false)
    private long accountsId;

    @Column
    private Timestamp createdTime;

    @Column
    private Timestamp updatedTime;
}

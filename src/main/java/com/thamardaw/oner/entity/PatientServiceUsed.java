package com.thamardaw.oner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "PatientServiceUsed")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientServiceUsed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @JsonIgnore
    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceItemId")
    private ServiceItem serviceItem;

    @Column(insertable = false,updatable = false)
    private long serviceItemId;

    @JsonIgnore
    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId")
    private Patient patient;

    @Column(insertable = false,updatable = false)
    private long patientId;

    @Column
    private long quantity;

    @Column
    private long charge;

    @Column
    private long totalCharge;

    @Column
    private String status;

    @Column
    private String extra;

    @Column
    private long createdUserId;

    @Column
    private long updatedUserId;

    @Column
    private Timestamp createdTime;

    @Column
    private Timestamp updatedTime;

}

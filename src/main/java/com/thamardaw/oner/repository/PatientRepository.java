package com.thamardaw.oner.repository;

import com.thamardaw.oner.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}

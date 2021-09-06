package com.thamardaw.oner.repository;

import com.thamardaw.oner.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Long, Payment> {
}

package com.thamardaw.oner.repository;

import com.thamardaw.oner.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Long, Bill> {
}

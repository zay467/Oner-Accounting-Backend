package com.thamardaw.oner.repository;

import com.thamardaw.oner.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Long, Deposit> {
}

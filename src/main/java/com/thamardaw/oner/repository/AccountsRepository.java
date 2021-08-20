package com.thamardaw.oner.repository;

import com.thamardaw.oner.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Accounts,Long> {
}

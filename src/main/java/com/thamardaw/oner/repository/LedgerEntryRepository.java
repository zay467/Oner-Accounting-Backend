package com.thamardaw.oner.repository;

import com.thamardaw.oner.entity.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry,Long> {
}

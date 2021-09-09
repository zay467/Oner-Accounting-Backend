package com.thamardaw.oner.controller;

import com.thamardaw.oner.entity.Accounts;
import com.thamardaw.oner.entity.LedgerEntry;
import com.thamardaw.oner.entity.request.LedgerEntryRequest;
import com.thamardaw.oner.repository.AccountsRepository;
import com.thamardaw.oner.repository.LedgerEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ledgers")
public class LedgerEntryController {

    @Autowired
    private LedgerEntryRepository ledgerEntryRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    @GetMapping("")
    public ResponseEntity<?> get(){
        List<LedgerEntry> ledgerEntryList = ledgerEntryRepository.findAll(Sort.by("id").ascending());
        return ResponseEntity.ok(ledgerEntryList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id){
        Optional<LedgerEntry> ledgerEntry = ledgerEntryRepository.findById(id);
        if (ledgerEntry.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(ledgerEntry);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id,@RequestBody LedgerEntryRequest request){
        Optional<LedgerEntry> ledgerEntryOptional = ledgerEntryRepository.findById(id);
        if (ledgerEntryOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Accounts> accountsOptional = accountsRepository.findById(request.getAccount_id());
        if (accountsOptional.isEmpty()){
            return new ResponseEntity<>("Account doesn't exist.",HttpStatus.NOT_FOUND);
        }

        Accounts accounts = accountsOptional.get();
        LedgerEntry ledgerEntry = ledgerEntryOptional.get();
        ledgerEntry.setDate(request.getDate());
        ledgerEntry.setReference(request.getReference());
        ledgerEntry.setExplanation(request.getExplanation());
        ledgerEntry.setDebit(request.getDebit());
        ledgerEntry.setCredit(request.getCredit());
        ledgerEntry.setAccounts(accounts);
        ledgerEntry.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        ledgerEntryRepository.save(ledgerEntry);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        Optional<LedgerEntry> ledgerEntry = ledgerEntryRepository.findById(id);
        if (ledgerEntry.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ledgerEntryRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody LedgerEntryRequest request){
        Optional<Accounts> accountsOptional = accountsRepository.findById(request.getAccount_id());
        if(accountsOptional.isEmpty()){
            return new ResponseEntity<>("Account doesn't exist.",HttpStatus.NOT_FOUND);
        }

        Accounts accounts = accountsOptional.get();
        LedgerEntry ledgerEntry = new LedgerEntry();
        ledgerEntry.setDate(request.getDate());
        ledgerEntry.setReference(request.getReference());
        ledgerEntry.setExplanation(request.getExplanation());
        ledgerEntry.setDebit(request.getDebit());
        ledgerEntry.setCredit(request.getCredit());
        ledgerEntry.setAccounts(accounts);
        ledgerEntry.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        ledgerEntry.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        ledgerEntryRepository.save(ledgerEntry);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

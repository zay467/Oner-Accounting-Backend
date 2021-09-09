package com.thamardaw.oner.controller;

import com.thamardaw.oner.entity.AccountType;
import com.thamardaw.oner.entity.request.AccountTypeRequest;
import com.thamardaw.oner.repository.AccountTypeRepository;
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
@RequestMapping("/api/account_types")
public class AccountTypeController {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @GetMapping("")
    public ResponseEntity<?> get(){

        List<AccountType> accountTypeList = accountTypeRepository.findAll(Sort.by("id").ascending());
        return ResponseEntity.ok(accountTypeList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id){
        Optional<AccountType> accountType = accountTypeRepository.findById(id);
        if (accountType.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accountType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id,@RequestBody AccountTypeRequest request){
        Optional<AccountType> accountTypeOptional = accountTypeRepository.findById(id);
        if (accountTypeOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AccountType accountType = accountTypeOptional.get();
        accountType.setName(request.getName());
        accountType.setDescription(request.getDescription());
        accountType.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        accountTypeRepository.save(accountType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        Optional<AccountType> accountType = accountTypeRepository.findById(id);
        if (accountType.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountTypeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody AccountTypeRequest request){
        AccountType accountType = new AccountType();
        accountType.setName(request.getName());
        accountType.setDescription(request.getDescription());
        accountType.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        accountType.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        accountTypeRepository.save(accountType);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

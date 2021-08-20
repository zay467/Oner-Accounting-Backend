package com.thamardaw.oner.controller;

import com.thamardaw.oner.entity.AccountType;
import com.thamardaw.oner.entity.Accounts;
import com.thamardaw.oner.entity.Category;
import com.thamardaw.oner.entity.request.AccountsRequest;
import com.thamardaw.oner.repository.AccountTypeRepository;
import com.thamardaw.oner.repository.AccountsRepository;
import com.thamardaw.oner.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @GetMapping("")
    public ResponseEntity<?> get(){
        List<Accounts> accountsList = accountsRepository.findAll(Sort.by("id").ascending());
        return ResponseEntity.ok(accountsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id){
        Optional<Accounts> accounts = accountsRepository.findById(id);
        if (accounts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accounts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id,@RequestBody AccountsRequest request){
        Optional<Accounts> accountsOptional = accountsRepository.findById(id);
        if (accountsOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Category> categoryOptional = categoryRepository.findById(request.getCategory_id());
        if (categoryOptional.isEmpty()){
            return new ResponseEntity<>("Category doesn't exit.",HttpStatus.NOT_FOUND);
        }
        Optional<AccountType> accountTypeOptional = accountTypeRepository.findById(request.getAccount_type_id());
        if (accountTypeOptional.isEmpty()){
            return new ResponseEntity<>("Account Type doesn't exit.",HttpStatus.NOT_FOUND);
        }
        Accounts accounts = accountsOptional.get();
        Category category = categoryOptional.get();
        AccountType accountType = accountTypeOptional.get();
        accounts.setCode(request.getCode());
        accounts.setAccountName(request.getAccount_name());
        accounts.setDescription(request.getDescription());
        accounts.setCategory(category);
        accounts.setAccountType(accountType);
        accounts.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        accountsRepository.save(accounts);
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        Optional<Accounts> accounts = accountsRepository.findById(id);
        if (accounts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountsRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody AccountsRequest request){
        Optional<Category> categoryOptional = categoryRepository.findById(request.getCategory_id());
        if (categoryOptional.isEmpty()){
            return new ResponseEntity<>("Category doesn't exit.",HttpStatus.NOT_FOUND);
        }
        Optional<AccountType> accountTypeOptional = accountTypeRepository.findById(request.getAccount_type_id());
        if (accountTypeOptional.isEmpty()){
            return new ResponseEntity<>("Account Type doesn't exit.",HttpStatus.NOT_FOUND);
        }
        Category category = categoryOptional.get();
        AccountType accountType = accountTypeOptional.get();
        Accounts accounts = new Accounts();
        accounts.setCode(request.getCode());
        accounts.setAccountName(request.getAccount_name());
        accounts.setDescription(request.getDescription());
        accounts.setCategory(category);
        accounts.setAccountType(accountType);
        accounts.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        accounts.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        accountsRepository.save(accounts);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

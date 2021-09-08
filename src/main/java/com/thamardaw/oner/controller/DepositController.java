package com.thamardaw.oner.controller;

import com.thamardaw.oner.entity.Deposit;
import com.thamardaw.oner.entity.Patient;
import com.thamardaw.oner.entity.User;
import com.thamardaw.oner.entity.request.DepositRequest;
import com.thamardaw.oner.repository.DepositRepository;
import com.thamardaw.oner.repository.PatientRepository;
import com.thamardaw.oner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/deposit")
public class DepositController {

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("")
    public ResponseEntity<?> get(){
        List<Deposit> depositList = depositRepository.findAll(Sort.by("id").ascending());
        return ResponseEntity.ok(depositList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id){
        Optional<Deposit> deposit = depositRepository.findById(id);
        if (deposit.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(deposit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id,@RequestBody DepositRequest request,@AuthenticationPrincipal UserDetails userDetails){
        Optional<Deposit> depositOptional = depositRepository.findById(id);
        if (depositOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userRepository.findByUserName(userDetails.getUsername());
        if (userOptional.isEmpty()){
            return new ResponseEntity<>("Invalid user.",HttpStatus.UNAUTHORIZED);
        }
        Optional<Patient> patientOptional = patientRepository.findById(request.getPatientId());
        if(patientOptional.isEmpty()){
            return new ResponseEntity<>("Patient doesn't exist.",HttpStatus.UNAUTHORIZED);
        }
        User user = userOptional.get();
        Patient patient = patientOptional.get();
        Deposit deposit = depositOptional.get();
        deposit.setPatient(patient);
        deposit.setPatientId(patient.getId());
        deposit.setStatus(request.getStatus());
        deposit.setAmount(request.getAmount());
        deposit.setUpdatedUserId(user.getId());
        deposit.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        depositRepository.save(deposit);
        return ResponseEntity.ok(deposit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        Optional<Deposit> deposit = depositRepository.findById(id);
        if (deposit.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        depositRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody DepositRequest request, @AuthenticationPrincipal UserDetails userDetails){
        Optional<User> userOptional = userRepository.findByUserName(userDetails.getUsername());
        if (userOptional.isEmpty()){
            return new ResponseEntity<>("Invalid user.",HttpStatus.UNAUTHORIZED);
        }
        Optional<Patient> patientOptional = patientRepository.findById(request.getPatientId());
        if(patientOptional.isEmpty()){
            return new ResponseEntity<>("Patient doesn't exist.",HttpStatus.UNAUTHORIZED);
        }
        User user = userOptional.get();
        Patient patient = patientOptional.get();
        Deposit deposit = new Deposit();
        deposit.setPatient(patient);
        deposit.setStatus(request.getStatus());
        deposit.setAmount(request.getAmount());
        deposit.setCreatedUserId(user.getId());
        deposit.setUpdatedUserId(user.getId());
        deposit.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        deposit.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        depositRepository.save(deposit);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

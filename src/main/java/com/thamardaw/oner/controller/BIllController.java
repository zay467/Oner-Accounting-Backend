package com.thamardaw.oner.controller;

import com.thamardaw.oner.entity.Bill;
import com.thamardaw.oner.entity.Patient;
import com.thamardaw.oner.entity.User;
import com.thamardaw.oner.entity.request.BillRequest;
import com.thamardaw.oner.repository.BillRepository;
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
@RequestMapping("/api/bill")
public class BIllController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BillRepository billRepository;

    @GetMapping("")
    public ResponseEntity<?> get(){
        List<Bill> billList = billRepository.findAll(Sort.by("id").ascending());
        return ResponseEntity.ok(billList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id){
        Optional<Bill> bill = billRepository.findById(id);
        if(bill.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(bill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id,@RequestBody BillRequest request, @AuthenticationPrincipal UserDetails userDetails){
        Optional<Bill> billOptional = billRepository.findById(id);
        if(billOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userRepository.findByUserName(userDetails.getUsername());
        if (userOptional.isEmpty()){
            return new ResponseEntity<>("Invalid user.",HttpStatus.UNAUTHORIZED);
        }
        Optional<Patient> patientOptional = patientRepository.findById(request.getPatientId());
        if(patientOptional.isEmpty()){
            return new ResponseEntity<>("Patient doesn't exist.",HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        Patient patient = patientOptional.get();
        Bill bill = billOptional.get();
        bill.setPatient(patient);
        bill.setTotalAmount(request.getTotalAmount());
        bill.setDepositAmount(request.getDepositAmount());
        bill.setUnpaidAmount(request.getUnpaidAmount());
        bill.setUpdatedUserId(user.getId());
        bill.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        billRepository.save(bill);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        Optional<Bill> bill = billRepository.findById(id);
        if(bill.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        billRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody BillRequest request, @AuthenticationPrincipal UserDetails userDetails){
        Optional<User> userOptional = userRepository.findByUserName(userDetails.getUsername());
        if (userOptional.isEmpty()){
            return new ResponseEntity<>("Invalid user.",HttpStatus.UNAUTHORIZED);
        }
        Optional<Patient> patientOptional = patientRepository.findById(request.getPatientId());
        if(patientOptional.isEmpty()){
            return new ResponseEntity<>("Patient doesn't exist.",HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        Patient patient = patientOptional.get();
        Bill bill = new Bill();
        bill.setPatient(patient);
        bill.setTotalAmount(request.getTotalAmount());
        bill.setDepositAmount(request.getDepositAmount());
        bill.setUnpaidAmount(request.getUnpaidAmount());
        bill.setCreatedUserId(user.getId());
        bill.setUpdatedUserId(user.getId());
        bill.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        bill.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        billRepository.save(bill);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

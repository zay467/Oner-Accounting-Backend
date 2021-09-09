package com.thamardaw.oner.controller;

import com.thamardaw.oner.entity.Bill;
import com.thamardaw.oner.entity.Patient;
import com.thamardaw.oner.entity.Payment;
import com.thamardaw.oner.entity.User;
import com.thamardaw.oner.entity.request.PaymentRequest;
import com.thamardaw.oner.repository.BillRepository;
import com.thamardaw.oner.repository.PatientRepository;
import com.thamardaw.oner.repository.PaymentRepository;
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
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("")
    public ResponseEntity<?> get(){
        List<Payment> paymentList = paymentRepository.findAll(Sort.by("id").ascending());
        return ResponseEntity.ok(paymentList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id){
        Optional<Payment> payment = paymentRepository.findById(id);
        if(payment.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id,@RequestBody PaymentRequest request, @AuthenticationPrincipal UserDetails userDetails){
        Optional<User> userOptional = userRepository.findByUserName(userDetails.getUsername());
        if (userOptional.isEmpty()){
            return new ResponseEntity<>("Invalid user.",HttpStatus.UNAUTHORIZED);
        }
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Patient> patientOptional = patientRepository.findById(request.getPatientId());
        if(patientOptional.isEmpty()){
            return new ResponseEntity<>("Patient doesn't exist.",HttpStatus.NOT_FOUND);
        }
        Optional<Bill> billOptional = billRepository.findById(request.getBillId());
        if(billOptional.isEmpty()){
            return new ResponseEntity<>("Bill doesn't exist.",HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        Patient patient = patientOptional.get();
        Bill bill = billOptional.get();
        Payment payment = paymentOptional.get();
        payment.setPatient(patient);
        payment.setBill(bill);
        payment.setAmount(request.getAmount());
        payment.setUpdatedUserId(user.getId());
        payment.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        paymentRepository.save(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        Optional<Payment> payment = paymentRepository.findById(id);
        if(payment.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        paymentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody PaymentRequest request, @AuthenticationPrincipal UserDetails userDetails){
        Optional<User> userOptional = userRepository.findByUserName(userDetails.getUsername());
        if (userOptional.isEmpty()){
            return new ResponseEntity<>("Invalid user.",HttpStatus.UNAUTHORIZED);
        }
        Optional<Patient> patientOptional = patientRepository.findById(request.getPatientId());
        if(patientOptional.isEmpty()){
            return new ResponseEntity<>("Patient doesn't exist.",HttpStatus.NOT_FOUND);
        }
        Optional<Bill> billOptional = billRepository.findById(request.getBillId());
        if(billOptional.isEmpty()){
            return new ResponseEntity<>("Bill doesn't exist.",HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        Patient patient = patientOptional.get();
        Bill bill = billOptional.get();
        Payment payment = new Payment();
        payment.setPatient(patient);
        payment.setBill(bill);
        payment.setAmount(request.getAmount());
        payment.setCreatedUserId(user.getId());
        payment.setUpdatedUserId(user.getId());
        payment.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        payment.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        paymentRepository.save(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package com.thamardaw.oner.controller;

import com.thamardaw.oner.entity.Patient;
import com.thamardaw.oner.entity.User;
import com.thamardaw.oner.entity.request.PatientRequest;
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
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<?> get(){
        List<Patient> patientList = patientRepository.findAll(Sort.by("id").ascending());
        return ResponseEntity.ok(patientList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id){
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody PatientRequest request, @AuthenticationPrincipal UserDetails userDetails){
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if(patientOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userRepository.findByUserName(userDetails.getUsername());
        if (userOptional.isEmpty()){
            return new ResponseEntity<>("Invalid user.",HttpStatus.UNAUTHORIZED);
        }
        User user = userOptional.get();
        Patient patient = patientOptional.get();
        patient.setName(request.getName());
        patient.setAge(request.getAge());
        patient.setPhone(request.getPhone());
        patient.setNrc(request.getNrc());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setAddress(request.getAddress());
        patient.setGender(request.getGender());
        patient.setStatus(request.getStatus());
        patient.setUpdatedUserId(user.getId());
        patient.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        patientRepository.save(patient);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        patientRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody PatientRequest request, @AuthenticationPrincipal UserDetails userDetails){
        Optional<User> userOptional = userRepository.findByUserName(userDetails.getUsername());
        if (userOptional.isEmpty()){
            return new ResponseEntity<>("Invalid user.",HttpStatus.UNAUTHORIZED);
        }
        User user = userOptional.get();
        Patient patient = new Patient();
        patient.setName(request.getName());
        patient.setAge(request.getAge());
        patient.setPhone(request.getPhone());
        patient.setNrc(request.getNrc());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setAddress(request.getAddress());
        patient.setGender(request.getGender());
        patient.setStatus(request.getStatus());
        patient.setCreatedUserId(user.getId());
        patient.setUpdatedUserId(user.getId());
        patient.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        patient.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        patientRepository.save(patient);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

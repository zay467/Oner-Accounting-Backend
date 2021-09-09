package com.thamardaw.oner.controller;

import com.thamardaw.oner.entity.Patient;
import com.thamardaw.oner.entity.PatientServiceUsed;
import com.thamardaw.oner.entity.ServiceItem;
import com.thamardaw.oner.entity.User;
import com.thamardaw.oner.entity.request.PatientServiceUsedRequest;
import com.thamardaw.oner.repository.PatientRepository;
import com.thamardaw.oner.repository.PatientServiceUsedRepository;
import com.thamardaw.oner.repository.ServiceItemRepository;
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
@RequestMapping("/api/patientServiceUsed")
public class PatientServiceUsedController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientServiceUsedRepository patientServiceUsedRepository;

    @Autowired
    private ServiceItemRepository serviceItemRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<?> get(){
        List<PatientServiceUsed>  patientServiceUsedList = patientServiceUsedRepository.findAll(Sort.by("Id").ascending());
        return ResponseEntity.ok(patientServiceUsedList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id){
        Optional<PatientServiceUsed> patientServiceUsed = patientServiceUsedRepository.findById(id);
        if(patientServiceUsed.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(patientServiceUsed);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id,@RequestBody PatientServiceUsedRequest request, @AuthenticationPrincipal UserDetails userDetails){
        Optional<PatientServiceUsed> patientServiceUsedOptional = patientServiceUsedRepository.findById(id);
        if(patientServiceUsedOptional.isEmpty()){
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
        Optional<ServiceItem> serviceItemOptional = serviceItemRepository.findById(request.getServiceItemId());
        if(serviceItemOptional.isEmpty()){
            return new ResponseEntity<>("Service item doesn't exist.",HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        Patient patient = patientOptional.get();
        ServiceItem serviceItem = serviceItemOptional.get();
        PatientServiceUsed patientServiceUsed = patientServiceUsedOptional.get();
        patientServiceUsed.setServiceItem(serviceItem);
        patientServiceUsed.setPatient(patient);
        patientServiceUsed.setQuantity(request.getQuantity());
        patientServiceUsed.setCharge(request.getCharge());
        patientServiceUsed.setTotalCharge(request.getTotalCharge());
        patientServiceUsed.setStatus(request.getStatus());
        patientServiceUsed.setExtra(request.getExtra());
        patientServiceUsed.setUpdatedUserId(user.getId());
        patientServiceUsed.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        patientServiceUsedRepository.save(patientServiceUsed);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        Optional<PatientServiceUsed> patientServiceUsed = patientServiceUsedRepository.findById(id);
        if(patientServiceUsed.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        patientServiceUsedRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody PatientServiceUsedRequest request, @AuthenticationPrincipal UserDetails userDetails){
        Optional<User> userOptional = userRepository.findByUserName(userDetails.getUsername());
        if (userOptional.isEmpty()){
            return new ResponseEntity<>("Invalid user.",HttpStatus.UNAUTHORIZED);
        }
        Optional<Patient> patientOptional = patientRepository.findById(request.getPatientId());
        if(patientOptional.isEmpty()){
            return new ResponseEntity<>("Patient doesn't exist.",HttpStatus.NOT_FOUND);
        }
        Optional<ServiceItem> serviceItemOptional = serviceItemRepository.findById(request.getServiceItemId());
        if(serviceItemOptional.isEmpty()){
            return new ResponseEntity<>("Service item doesn't exist.",HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        Patient patient = patientOptional.get();
        ServiceItem serviceItem = serviceItemOptional.get();
        PatientServiceUsed patientServiceUsed = new PatientServiceUsed();
        patientServiceUsed.setServiceItem(serviceItem);
        patientServiceUsed.setPatient(patient);
        patientServiceUsed.setQuantity(request.getQuantity());
        patientServiceUsed.setCharge(request.getCharge());
        patientServiceUsed.setTotalCharge(request.getTotalCharge());
        patientServiceUsed.setStatus(request.getStatus());
        patientServiceUsed.setExtra(request.getExtra());
        patientServiceUsed.setCreatedUserId(user.getId());
        patientServiceUsed.setUpdatedUserId(user.getId());
        patientServiceUsed.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        patientServiceUsed.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        patientServiceUsedRepository.save(patientServiceUsed);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

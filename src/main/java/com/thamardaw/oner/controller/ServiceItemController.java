package com.thamardaw.oner.controller;

import com.thamardaw.oner.entity.ServiceItem;
import com.thamardaw.oner.entity.User;
import com.thamardaw.oner.entity.request.ServiceItemRequest;
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
@RequestMapping("/api/serviceItem")
public class ServiceItemController {

    @Autowired
    private ServiceItemRepository serviceItemRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<?> get(){
        List<ServiceItem> serviceItemList = serviceItemRepository.findAll(Sort.by("id").ascending());
        return ResponseEntity.ok(serviceItemList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id){
        Optional<ServiceItem> serviceItem = serviceItemRepository.findById(id);
        if(serviceItem.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(serviceItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id,@RequestBody ServiceItemRequest request, @AuthenticationPrincipal UserDetails userDetails){
        Optional<ServiceItem> serviceItemOptional = serviceItemRepository.findById(id);
        if(serviceItemOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userRepository.findByUserName(userDetails.getUsername());
        if (userOptional.isEmpty()){
            return new ResponseEntity<>("Invalid user.",HttpStatus.UNAUTHORIZED);
        }
        ServiceItem serviceItem = serviceItemOptional.get();
        User user = userOptional.get();
        serviceItem.setName(request.getName());
        serviceItem.setRelationshipId(request.getRelationshipId());
        serviceItem.setServiceType(request.getServiceType());
        serviceItem.setIncomeDepartment(request.getIncomeDepartment());
        serviceItem.setCharge(request.getCharge());
        serviceItem.setUpdatedUserId(user.getId());
        serviceItem.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        serviceItemRepository.save(serviceItem);
        return ResponseEntity.ok(serviceItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        Optional<ServiceItem> serviceItem = serviceItemRepository.findById(id);
        if(serviceItem.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        serviceItemRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ServiceItemRequest request, @AuthenticationPrincipal UserDetails userDetails){
        Optional<User> userOptional = userRepository.findByUserName(userDetails.getUsername());
        if (userOptional.isEmpty()){
            return new ResponseEntity<>("Invalid user.",HttpStatus.UNAUTHORIZED);
        }
        User user = userOptional.get();
        ServiceItem serviceItem = new ServiceItem();
        serviceItem.setName(request.getName());
        serviceItem.setRelationshipId(request.getRelationshipId());
        serviceItem.setServiceType(request.getServiceType());
        serviceItem.setIncomeDepartment(request.getIncomeDepartment());
        serviceItem.setCharge(request.getCharge());
        serviceItem.setCreatedUserId(user.getId());
        serviceItem.setUpdatedUserId(user.getId());
        serviceItem.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        serviceItem.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        serviceItemRepository.save(serviceItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

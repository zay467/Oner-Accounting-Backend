package com.thamardaw.oner.controller;

import com.thamardaw.oner.entity.User;
import com.thamardaw.oner.entity.request.LoginRequest;
import com.thamardaw.oner.entity.request.SignupRequest;
import com.thamardaw.oner.entity.response.LoginResponse;
import com.thamardaw.oner.entity.response.RefreshTokenResponse;
import com.thamardaw.oner.repository.UserRepository;
import com.thamardaw.oner.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOptional = userRepository.findByUserName(request.getUserName());
        if(userOptional.isEmpty()){
            return new ResponseEntity<>("Username not found.",HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())){
            return new ResponseEntity<>("Wrong password.",HttpStatus.FORBIDDEN);
        }
        String token = jwtTokenProvider.createToken(user.getUserName());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        Optional<User> userOptional = userRepository.findByUserName(request.getUserName());
        if (userOptional.isPresent()){
            return new ResponseEntity<>("Username already exist.", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setFullName(request.getFullName());
        user.setUserName(request.getUserName());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@AuthenticationPrincipal UserDetails userDetails) {
        String token = jwtTokenProvider.createToken(userDetails.getUsername());
        RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
        refreshTokenResponse.setToken(token);
        return ResponseEntity.ok(refreshTokenResponse);
    }
}

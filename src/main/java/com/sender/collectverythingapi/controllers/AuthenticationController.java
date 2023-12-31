package com.sender.collectverythingapi.controllers;

import com.sender.collectverythingapi.dto.AuthenticationRequest;
import com.sender.collectverythingapi.dto.AuthenticationResponse;
import com.sender.collectverythingapi.dto.UserDto;
import com.sender.collectverythingapi.services.UserService;
import com.sender.collectverythingapi.validators.ObjectValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        return userService.authenticate(authenticationRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserDto user) throws ObjectValidationException {
        return ResponseEntity.ok(userService.register(user));
    }

}
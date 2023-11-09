package com.sender.collectverythingapi.services;

import com.sender.collectverythingapi.dto.AuthenticationRequest;
import com.sender.collectverythingapi.dto.AuthenticationResponse;
import com.sender.collectverythingapi.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request);
    List<User> findAll();

    void deleteById(Integer userId);

}
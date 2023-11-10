package com.sender.collectverythingapi.services.impl;


import com.sender.collectverythingapi.config.JwtUtils;
import com.sender.collectverythingapi.dto.AuthenticationRequest;
import com.sender.collectverythingapi.dto.AuthenticationResponse;
import com.sender.collectverythingapi.dto.UserDto;
import com.sender.collectverythingapi.entity.User;
import com.sender.collectverythingapi.repository.UserRepository;
import com.sender.collectverythingapi.services.UserService;
import com.sender.collectverythingapi.validators.ObjectValidationException;
import com.sender.collectverythingapi.validators.ObjectValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private static final String ROLE_USER = "ROLE_USER";
    private final UserRepository repository;
    private final ObjectValidator<UserDto> validator;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {

        final User user;

        // CHECK MAIL
        try{
            user= (User) userRepository.findByEmail(request.getEmail()).get();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }

        // CHECK Auth Mdp
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        } catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }

        // Si ok
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("fullName", user.getFirstname() + " " + user.getLastname());
        final String token = jwtUtils.generateToken(user,claims);

        return ResponseEntity.ok( AuthenticationResponse.builder().token(token).build());
    }



    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public AuthenticationResponse register(UserDto userDto) throws ObjectValidationException {
        validator.validate(userDto);
        User user = UserDto.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("Mot de passe generé -> "+user.getPassword()); // Pour info

        user.setRole(ROLE_USER);

        var savedUser =  repository.save(user);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", savedUser.getId());
        claims.put("fullname", savedUser.getFirstname() + " " + savedUser.getLastname());

        String token = jwtUtils.generateToken(savedUser,claims);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public void deleteById(Integer userId) {
        userRepository.deleteById(userId);
    }
}
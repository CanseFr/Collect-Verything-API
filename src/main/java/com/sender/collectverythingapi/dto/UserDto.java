package com.sender.collectverythingapi.dto;

import com.sender.collectverythingapi.entity.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {

    private Integer id;


    @NotNull(message = "Le prenom ne doit pas etre null")
    @NotEmpty(message = "Le prenom ne doit pas etre vide")
    @NotBlank(message = "Le prenom ne doit pas contenir d'espace")
    private String firstname;

    @NotNull(message = "Le lastname ne peut pas etre null")
    @NotEmpty(message = "Le lastname ne peut pas etre vide")
    @NotBlank(message = "Le lastnamene doit pas contenir d'espace")
    private String lastname;

    @NotNull(message = "Le password ne peut pas etre null")
    @NotEmpty(message = "Le password ne peut pas etre vide")
    @NotBlank(message = "Le password ne doit pas contenir d'espace")
    @Email//(regexp = "Preciser ma regex si je le souhaite ")
    private String email;

    @NotNull(message = "Le password ne peut pas etre null")
    @NotEmpty(message = "Le password ne peut pas etre vide")
    @NotBlank(message = "Le password ne doit pas contenir d'espace")
    @Size(min = 8, max = 16, message = "Le password doit contenir 8 characteres min et 16 characteres max")
    private String password;

    private String iban;

    private boolean active;

    public static UserDto fromEntity(User user){
        return UserDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword())
                .active(user.isActive())
                .build();
    }

    public static User toEntity(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .firstname(userDto.getFirstname().trim().toLowerCase())
                .lastname(userDto.getLastname().trim().toLowerCase())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

}
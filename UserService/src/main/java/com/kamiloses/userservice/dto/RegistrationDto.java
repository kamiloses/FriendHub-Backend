package com.kamiloses.userservice.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

    @NotBlank(message = "Username cannot be blank.")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username is invalid")
    @UniqueUsername
    private String username;

    @NotBlank(message = "Password cannot be blank.")
    @Pattern(regexp = "^.{6,}$", message = "Password is invalid")
    private String password;

    @NotBlank(message = "First Name cannot be blank.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First Name is invalid")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last Name is invalid")
    private String lastName;
}



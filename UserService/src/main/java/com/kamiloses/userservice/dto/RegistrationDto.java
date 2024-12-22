package com.kamiloses.userservice.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

    @Pattern(regexp = "^(?!\\s*$)[a-zA-Z0-9_]+$", message = "Username cannot be blank and must be valid.")
    private String username;

    @Pattern(regexp = "^(?!\\s*$).{6,}$", message = "Password cannot be blank and must be at least 6 characters long.")
    private String password;

    @Pattern(regexp = "^(?!\\s*$)[a-zA-Z]+$", message = "First Name cannot be blank and must only contain letters.")
    private String firstName;

    @Pattern(regexp = "^(?!\\s*$)[a-zA-Z]+$", message = "Last Name cannot be blank and must only contain letters.")
    private String lastName;

}



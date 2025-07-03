package ait.cohort5860.accounting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRegisterDto {

    @NotBlank(message="Login is required")
    @Size(min=3, max = 20, message="Login must be between 3 and 20 characters")
    private String login;

    @NotBlank(message="Password is required")
    @Size(min=4, max = 20, message="Password must be between 4 and 20 characters")
    private String password;

    @NotBlank(message="First name is required")
    @Size(min=2, max = 20, message="First name must be between 2 and 20 characters")
    private String firstName;

    @NotBlank(message="Last name is required")
    @Size(min=2, max = 20, message="Last name must be between 2 and 20 characters")
    private String lastName;
}

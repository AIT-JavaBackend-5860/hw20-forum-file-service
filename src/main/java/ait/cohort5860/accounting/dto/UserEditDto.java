package ait.cohort5860.accounting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserEditDto {

    @NotBlank(message="First Name is required")
    @Size(min = 2, max = 50, message="First Name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message="Last Name is required")
    @Size(min = 2, max = 50, message="Last Name must be between 2 and 50 characters")
    private String lastName;
}

package ait.cohort5860.accounting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserEditDto {

    @Size(min = 2, max = 20, message="First Name must be between 2 and 20 characters")
    private String firstName;

    @Size(min = 2, max = 20, message="Last Name must be between 2 and 20 characters")
    private String lastName;
}

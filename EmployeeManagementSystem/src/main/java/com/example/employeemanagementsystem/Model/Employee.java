package com.example.employeemanagementsystem.Model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Employee {
    @NotEmpty(message = "Id should not be empty")
    @Size(min = 2,message = "Id must be at least 2 characters")
    private String id;
    @NotEmpty(message = "Name must not be empty")
    @Size(min = 4,message = "Name must be more than 4 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "Name should only contain letters")
    private String name;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "write a valid email")
    private String email;
    @Size(min = 10,max = 10,message = "Phone number must be 10 digits")
    @Pattern(regexp = "^05.*$",message = "Number should start with 05")
    @Pattern(regexp = "^[0-9]+$",message = "Phone number should be a number")
    private String phoneNumber;
    @NotNull(message = "Age must not be empty")
    @Min(value = 25,message = "Age must be 25 or older")
    private int age;
    @NotEmpty(message = "Position must not be empty")
    @Pattern(regexp = "supervisor|coordinator",message = "Position must be supervisor or coordinator")
    private String position;
    @Value("${props.boolean.onLeave:false}")
    private  boolean onLeave;
    @NotNull(message = "Hire date should not be empty")
    @PastOrPresent(message = "Hire date must be past or present")
    private LocalDateTime hireDate;
    @NotNull(message = "Annual leave should not be empty")
    @Positive(message = "Annual leave should be a positive number")
    private int annualLeave;

}

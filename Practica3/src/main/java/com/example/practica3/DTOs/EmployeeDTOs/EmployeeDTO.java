package com.example.practica3.DTOs.EmployeeDTOs;


import com.example.practica3.model.Practice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDTO {
    private int code;
    private String name;
    private String role;
    private String practice;
}


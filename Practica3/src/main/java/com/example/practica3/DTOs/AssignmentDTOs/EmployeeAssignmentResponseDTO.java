package com.example.practica3.DTOs.AssignmentDTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeAssignmentResponseDTO {
    private int code;
    private String name;
    private String role;
    private String practice;
}

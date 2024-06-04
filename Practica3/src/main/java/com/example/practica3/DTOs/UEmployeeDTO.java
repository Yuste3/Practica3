package com.example.practica3.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UEmployeeDTO {
    private String name;
    private String role;
    private String practice;
}
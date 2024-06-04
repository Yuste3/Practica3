package com.example.practica3.DTOs.AssignmentDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignmentResponseDTO {
    private EmployeeAssignmentResponseDTO employee;
    private SupervisorAssignmentResponseDTO supervisor;
    private String practiceName;
    private ProjectAssignmentDTO project;
    private AssignmentInfoResponseDTO assignmentInfo;



}

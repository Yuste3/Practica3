package com.example.practica3.DTOs;

import com.example.practica3.model.Employee;
import com.example.practica3.model.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


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

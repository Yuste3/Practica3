package com.example.practica3.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignmentDTO {
    private Integer employeeCode;
    private Integer supervisorCode;
    private AssignmentInfoDTO assignmentInfo;
    private ProjectAssignmentDTO project;
    private String practiceName;
}

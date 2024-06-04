package com.example.practica3.DTOs.AssignmentDTOs;

import com.example.practica3.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignmentInfoResponseDTO {
    private int code;
    private String remark;
    private String percentage;
    private String startDate;
    private String endDate;
}
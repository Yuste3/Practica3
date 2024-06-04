package com.example.practica3.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignmentInfoDTO {

    private String remark;
    private String percentage;
    private String startDate;
    private String endDate;
}

package com.example.practica3.Mappers;

import com.example.practica3.DTOs.AssignmentDTO;
import com.example.practica3.DTOs.AssignmentResponseDTO;
import com.example.practica3.DTOs.EmployeeDTO;
import com.example.practica3.DTOs.UEmployeeDTO;
import com.example.practica3.model.Assignment;
import com.example.practica3.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    @Mapping(source="employeeCode", target="employee.employeeID")
    @Mapping(source="supervisorCode", target="supervisor.employeeID")
    @Mapping(source="project.oldCode", target="oldProjectCode.projectCode")
    @Mapping(source="project.newCode", target="newProjectCode.projectCode")
    @Mapping(source="assignmentInfo.remark", target="RDGRemarks")
    @Mapping(source="assignmentInfo.percentage", target="allocation")
    @Mapping(source="assignmentInfo.startDate", target="allocationStartDate")
    @Mapping(source="assignmentInfo.endDate", target="allocationEndDate")
    @Mapping(source="practiceName", target="practice.name")
    Assignment assignmentDTOToAssignment(AssignmentDTO assignmentDTO);

    @Mapping(source="practice.name", target="practiceName")
    @Mapping(source="allocation", target="assignmentInfo.percentage")
    @Mapping(source="allocationStartDate", target="assignmentInfo.startDate")
    @Mapping(source="allocationEndDate", target="assignmentInfo.endDate")
    @Mapping(source="RDGRemarks", target="assignmentInfo.remark")
    @Mapping(source="assignmentID", target="assignmentInfo.code")
    @Mapping(source="oldProjectCode.projectCode", target="project.oldCode")
    @Mapping(source="newProjectCode.projectCode", target="project.newCode")
    AssignmentResponseDTO assignmentToResponseDTO(Assignment assignment);
}

package com.example.practica3.Mappers;

import com.example.practica3.DTOs.AssignmentDTOs.AssignmentDTO;
import com.example.practica3.DTOs.AssignmentDTOs.AssignmentResponseDTO;
import com.example.practica3.model.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
    @Mapping(source="employee.employeeID", target="employee.code")
    @Mapping(source="employee.name", target="employee.name")
    @Mapping(source="employee.role", target="employee.role")
    @Mapping(source="employee.practice.name", target="employee.practice")
    @Mapping(source="supervisor.employeeID", target="supervisor.code")
    @Mapping(source="supervisor.name", target="supervisor.name")
    AssignmentResponseDTO assignmentToResponseDTO(Assignment assignment);
}

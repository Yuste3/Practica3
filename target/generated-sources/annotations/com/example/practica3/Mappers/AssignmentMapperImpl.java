package com.example.practica3.Mappers;

import com.example.practica3.DTOs.AssignmentDTO;
import com.example.practica3.DTOs.AssignmentInfoDTO;
import com.example.practica3.DTOs.AssignmentInfoResponseDTO;
import com.example.practica3.DTOs.AssignmentResponseDTO;
import com.example.practica3.DTOs.ProjectAssignmentDTO;
import com.example.practica3.model.Assignment;
import com.example.practica3.model.Employee;
import com.example.practica3.model.Practice;
import com.example.practica3.model.Project;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T12:34:33+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class AssignmentMapperImpl implements AssignmentMapper {

    @Override
    public Assignment assignmentDTOToAssignment(AssignmentDTO assignmentDTO) {
        if ( assignmentDTO == null ) {
            return null;
        }

        Assignment assignment = new Assignment();

        assignment.setEmployee( assignmentDTOToEmployee( assignmentDTO ) );
        assignment.setSupervisor( assignmentDTOToEmployee1( assignmentDTO ) );
        assignment.setOldProjectCode( projectAssignmentDTOToProject( assignmentDTO.getProject() ) );
        assignment.setNewProjectCode( projectAssignmentDTOToProject1( assignmentDTO.getProject() ) );
        assignment.setPractice( assignmentDTOToPractice( assignmentDTO ) );
        assignment.setRDGRemarks( assignmentDTOAssignmentInfoRemark( assignmentDTO ) );
        assignment.setAllocation( assignmentDTOAssignmentInfoPercentage( assignmentDTO ) );
        assignment.setAllocationStartDate( assignmentDTOAssignmentInfoStartDate( assignmentDTO ) );
        assignment.setAllocationEndDate( assignmentDTOAssignmentInfoEndDate( assignmentDTO ) );

        return assignment;
    }

    @Override
    public AssignmentResponseDTO assignmentToResponseDTO(Assignment assignment) {
        if ( assignment == null ) {
            return null;
        }

        AssignmentResponseDTO assignmentResponseDTO = new AssignmentResponseDTO();

        assignmentResponseDTO.setAssignmentInfo( assignmentToAssignmentInfoResponseDTO( assignment ) );
        if ( assignment.getOldProjectCode() != null ) {
            if ( assignmentResponseDTO.getProject() == null ) {
                assignmentResponseDTO.setProject( new ProjectAssignmentDTO() );
            }
            projectToProjectAssignmentDTO( assignment.getOldProjectCode(), assignmentResponseDTO.getProject() );
        }
        if ( assignment.getNewProjectCode() != null ) {
            if ( assignmentResponseDTO.getProject() == null ) {
                assignmentResponseDTO.setProject( new ProjectAssignmentDTO() );
            }
            projectToProjectAssignmentDTO1( assignment.getNewProjectCode(), assignmentResponseDTO.getProject() );
        }
        assignmentResponseDTO.setPracticeName( assignmentPracticeName( assignment ) );
        assignmentResponseDTO.setEmployee( assignment.getEmployee() );
        assignmentResponseDTO.setSupervisor( assignment.getSupervisor() );

        return assignmentResponseDTO;
    }

    protected Employee assignmentDTOToEmployee(AssignmentDTO assignmentDTO) {
        if ( assignmentDTO == null ) {
            return null;
        }

        Employee.EmployeeBuilder employee = Employee.builder();

        if ( assignmentDTO.getEmployeeCode() != null ) {
            employee.employeeID( assignmentDTO.getEmployeeCode().longValue() );
        }

        return employee.build();
    }

    protected Employee assignmentDTOToEmployee1(AssignmentDTO assignmentDTO) {
        if ( assignmentDTO == null ) {
            return null;
        }

        Employee.EmployeeBuilder employee = Employee.builder();

        if ( assignmentDTO.getSupervisorCode() != null ) {
            employee.employeeID( assignmentDTO.getSupervisorCode().longValue() );
        }

        return employee.build();
    }

    protected Project projectAssignmentDTOToProject(ProjectAssignmentDTO projectAssignmentDTO) {
        if ( projectAssignmentDTO == null ) {
            return null;
        }

        Project.ProjectBuilder project = Project.builder();

        project.projectCode( projectAssignmentDTO.getOldCode() );

        return project.build();
    }

    protected Project projectAssignmentDTOToProject1(ProjectAssignmentDTO projectAssignmentDTO) {
        if ( projectAssignmentDTO == null ) {
            return null;
        }

        Project.ProjectBuilder project = Project.builder();

        project.projectCode( projectAssignmentDTO.getNewCode() );

        return project.build();
    }

    protected Practice assignmentDTOToPractice(AssignmentDTO assignmentDTO) {
        if ( assignmentDTO == null ) {
            return null;
        }

        Practice.PracticeBuilder practice = Practice.builder();

        practice.name( assignmentDTO.getPracticeName() );

        return practice.build();
    }

    private String assignmentDTOAssignmentInfoRemark(AssignmentDTO assignmentDTO) {
        if ( assignmentDTO == null ) {
            return null;
        }
        AssignmentInfoDTO assignmentInfo = assignmentDTO.getAssignmentInfo();
        if ( assignmentInfo == null ) {
            return null;
        }
        String remark = assignmentInfo.getRemark();
        if ( remark == null ) {
            return null;
        }
        return remark;
    }

    private String assignmentDTOAssignmentInfoPercentage(AssignmentDTO assignmentDTO) {
        if ( assignmentDTO == null ) {
            return null;
        }
        AssignmentInfoDTO assignmentInfo = assignmentDTO.getAssignmentInfo();
        if ( assignmentInfo == null ) {
            return null;
        }
        String percentage = assignmentInfo.getPercentage();
        if ( percentage == null ) {
            return null;
        }
        return percentage;
    }

    private String assignmentDTOAssignmentInfoStartDate(AssignmentDTO assignmentDTO) {
        if ( assignmentDTO == null ) {
            return null;
        }
        AssignmentInfoDTO assignmentInfo = assignmentDTO.getAssignmentInfo();
        if ( assignmentInfo == null ) {
            return null;
        }
        String startDate = assignmentInfo.getStartDate();
        if ( startDate == null ) {
            return null;
        }
        return startDate;
    }

    private String assignmentDTOAssignmentInfoEndDate(AssignmentDTO assignmentDTO) {
        if ( assignmentDTO == null ) {
            return null;
        }
        AssignmentInfoDTO assignmentInfo = assignmentDTO.getAssignmentInfo();
        if ( assignmentInfo == null ) {
            return null;
        }
        String endDate = assignmentInfo.getEndDate();
        if ( endDate == null ) {
            return null;
        }
        return endDate;
    }

    protected AssignmentInfoResponseDTO assignmentToAssignmentInfoResponseDTO(Assignment assignment) {
        if ( assignment == null ) {
            return null;
        }

        AssignmentInfoResponseDTO assignmentInfoResponseDTO = new AssignmentInfoResponseDTO();

        assignmentInfoResponseDTO.setPercentage( assignment.getAllocation() );
        assignmentInfoResponseDTO.setStartDate( assignment.getAllocationStartDate() );
        assignmentInfoResponseDTO.setEndDate( assignment.getAllocationEndDate() );
        assignmentInfoResponseDTO.setRemark( assignment.getRDGRemarks() );
        if ( assignment.getAssignmentID() != null ) {
            assignmentInfoResponseDTO.setCode( assignment.getAssignmentID().intValue() );
        }

        return assignmentInfoResponseDTO;
    }

    protected void projectToProjectAssignmentDTO(Project project, ProjectAssignmentDTO mappingTarget) {
        if ( project == null ) {
            return;
        }

        mappingTarget.setOldCode( project.getProjectCode() );
    }

    protected void projectToProjectAssignmentDTO1(Project project, ProjectAssignmentDTO mappingTarget) {
        if ( project == null ) {
            return;
        }

        mappingTarget.setNewCode( project.getProjectCode() );
    }

    private String assignmentPracticeName(Assignment assignment) {
        if ( assignment == null ) {
            return null;
        }
        Practice practice = assignment.getPractice();
        if ( practice == null ) {
            return null;
        }
        String name = practice.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}

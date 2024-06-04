package com.example.practica3.service;

import com.example.practica3.DTOs.AssignmentDTOs.AssignmentDTO;
import com.example.practica3.model.Assignment;
import com.example.practica3.model.Employee;
import com.example.practica3.repository.AssignmentRepository;
import com.example.practica3.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IAssignmentService {
    private final AssignmentRepository repository;
    private final EmployeeRepository employeeRepository;

    public IAssignmentService(AssignmentRepository repository, EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }

    public boolean insertNewAssignment(Assignment assignment) {
        if (repository == null || assignment == null) {
            return false;
        }

        try {
            Assignment assignmentResult = repository.save(assignment);
            System.out.println(assignmentResult);
            System.out.println(assignment);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    public List<Assignment> getAllAssignments() {
        return repository.findAll();
    }

    public boolean validateAssignment(AssignmentDTO assignmentDTO) {
        return assignmentDTO.getEmployeeCode() > 0 &&
                assignmentDTO.getSupervisorCode() > 0 &&
                assignmentDTO.getAssignmentInfo().getRemark() != null && !assignmentDTO.getAssignmentInfo().getRemark().isEmpty() &&
                assignmentDTO.getAssignmentInfo().getStartDate() != null && !assignmentDTO.getAssignmentInfo().getStartDate().isEmpty() &&
                assignmentDTO.getAssignmentInfo().getEndDate() != null && !assignmentDTO.getAssignmentInfo().getEndDate().isEmpty() &&
                assignmentDTO.getAssignmentInfo().getPercentage() != null && !assignmentDTO.getAssignmentInfo().getPercentage().isEmpty() &&
                assignmentDTO.getProject().getNewCode() != null && !assignmentDTO.getProject().getNewCode().isEmpty() &&
                assignmentDTO.getProject().getOldCode() != null && !assignmentDTO.getProject().getOldCode().isEmpty() &&
                assignmentDTO.getPracticeName() != null && !assignmentDTO.getPracticeName().isEmpty();
    }

    public Assignment getAssignment(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean refreshAssignment(Assignment oldAssignment, Assignment newAssignment) {
        try {
            Employee newEmployee = employeeRepository.findById(newAssignment.getEmployee().getEmployeeID()).orElse(null);
            System.out.println(newEmployee);
            if (newEmployee == null) {
                return false;
            }
            Employee newSupervisor = employeeRepository.findById(newAssignment.getSupervisor().getEmployeeID()).orElse(null);
            System.out.println(newSupervisor);
            if (newSupervisor == null) {
                return false;
            }

            oldAssignment.setEmployee(newEmployee);
            oldAssignment.setSupervisor(newSupervisor);
            oldAssignment.setAllocation(newAssignment.getAllocation());
            oldAssignment.setPractice(newAssignment.getPractice());
            oldAssignment.setAllocationEndDate(newAssignment.getAllocationEndDate());
            oldAssignment.setAllocationStartDate(newAssignment.getAllocationStartDate());
            oldAssignment.setOldProjectCode(newAssignment.getOldProjectCode());
            oldAssignment.setNewProjectCode(newAssignment.getNewProjectCode());
            oldAssignment.setRDGRemarks(newAssignment.getRDGRemarks());
            repository.save(oldAssignment);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean deleteAssignment(Long code) {
        try {
            repository.deleteById(code);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

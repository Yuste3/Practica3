package com.example.practica3.controller;

import com.example.practica3.DTOs.*;
import com.example.practica3.Mappers.AssignmentMapper;
import com.example.practica3.Mappers.EmployeeMapper;
import com.example.practica3.model.Assignment;
import com.example.practica3.model.Employee;
import com.example.practica3.service.IAssignmentService;
import com.example.practica3.service.IEmployeeService;
import com.example.practica3.service.IPracticeService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/assignment")
public class AssignmentAPI {

    private final IAssignmentService service;

    @Autowired
    private final AssignmentMapper assignmentMapper;

    @Autowired
    public AssignmentAPI(IAssignmentService service, AssignmentMapper assignmentMapper) {
        this.service = service;
        this.assignmentMapper = assignmentMapper;
    }

    @PostMapping()
    public ResponseEntity<ErrorResponse> insertEmployee(@RequestBody AssignmentDTO assignmentDTO) {
        boolean validatedAssignment = service.validateAssignment(assignmentDTO);
        if (validatedAssignment) {
            Assignment assignment = assignmentMapper.assignmentDTOToAssignment(assignmentDTO);
            boolean response = service.insertNewAssignment(assignment);
            if (response) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(new ErrorResponse(8, "Error inserting the assignment"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ErrorResponse(9, "Values for assignment are wrong"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<List<AssignmentResponseDTO>> getAllEmployees() {
        return new ResponseEntity<>(service.getAllAssignments().stream().map(assignmentMapper::assignmentToResponseDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getAssignment(@PathVariable Long code) {
        Assignment assignment = service.getAssignment(code);
        if (assignment == null) {
            return new ResponseEntity<>(new ErrorResponse(10, "Assignment not found."), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assignmentMapper.assignmentToResponseDTO(assignment), HttpStatus.OK);
        }
    }

    @PutMapping("/{code}")
    public ResponseEntity<ErrorResponse> refreshAssignment(@PathVariable Long code, @RequestBody AssignmentDTO assignmentDTO) {
        boolean validatedAssignment = service.validateAssignment(assignmentDTO);
        if (validatedAssignment) {
            Assignment newAssignment = assignmentMapper.assignmentDTOToAssignment(assignmentDTO);
            Assignment oldAssignment = service.getAssignment(code);
            if (oldAssignment == null) {
                return new ResponseEntity<>(new ErrorResponse(11, "Assignment not found"), HttpStatus.NOT_FOUND);
            } else {
                boolean response = service.refreshAssignment(oldAssignment, newAssignment);
                if (response) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(new ErrorResponse(12, "Error updating assignment"), HttpStatus.CONFLICT);
                }
            }
        } else {
            return new ResponseEntity<>(new ErrorResponse(13, "Error in the request"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<ErrorResponse> deleteAssignment(@PathVariable Long code) {
        boolean response = service.deleteAssignment(code);
        return response ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(new ErrorResponse(14, "Error deleting assignment"), HttpStatus.CONFLICT);
    }
}

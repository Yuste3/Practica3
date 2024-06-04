package com.example.practica3.controller;

import com.example.practica3.DTOs.EmployeeDTOs.EmployeeDTO;
import com.example.practica3.DTOs.ErrorResponse;
import com.example.practica3.DTOs.EmployeeDTOs.UEmployeeDTO;
import com.example.practica3.Mappers.EmployeeMapper;
import com.example.practica3.model.Employee;
import com.example.practica3.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/employees")
public class EmployeeAPI {
    private final IEmployeeService service;
    @Autowired
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeAPI(IEmployeeService service, EmployeeMapper employeeMapper) {
        this.service = service;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Inserts a new employee into the system.
     *
     * @param employeeDTO EmployeeDTO object representing the employee to insert, passed in the request body.
     * @return ResponseEntity containing the inserted employeeDTO and HTTP status 200 (OK) if successful,
     *         or HTTP status 400 (Bad Request) if insertion fails.
     */
    @PostMapping()
    public ResponseEntity<ErrorResponse> insertEmployee(@RequestBody EmployeeDTO employeeDTO) {
        boolean validatedEmployee = service.validateEmployee(employeeDTO);
        if (validatedEmployee) {
            Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
            boolean response = service.insertNewEmployee(employee);
            if (response) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(new ErrorResponse(1, "Error inserting employee"), HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(new ErrorResponse(2, "Error in the request"), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves all employees in the system.
     *
     * @return ResponseEntity containing a list of all employees and HTTP status 200 (OK).
     */
    @GetMapping()
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return new ResponseEntity<>(service.getAllEmployees().stream().map(employeeMapper::employeeToDTO).toList(), HttpStatus.OK);
    }

    /**
     * Retrieves a specific employee by ID.
     *
     * @param code The ID of the employee to retrieve, passed as a path variable.
     * @return ResponseEntity containing the requested employee and HTTP status 200 (OK) if found,
     *         or HTTP status 404 (Not Found) if the employee doesn't exist.
     */
    @GetMapping("/{code}")
    public ResponseEntity<?> getEmployee(@PathVariable Long code) {
        Employee employee = service.getEmployee(code);
        if (employee == null) {
            return new ResponseEntity<>(new ErrorResponse(3, "Employee not found."), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(employeeMapper.employeeToDTO(employee), HttpStatus.OK);
        }
    }

    /**
     * Updates an existing employee in the system.
     *
     * @param code The ID of the employee to update, passed as a path variable.
     * @param newEmployeeDTO EmployeeDTO with the updated information, passed in the request body.
     * @return ResponseEntity containing the updated employee and HTTP status 200 (OK) if successful,
     *         HTTP status 404 (Not Found) if the employee doesn't exist,
     *         or HTTP status 400 (Bad Request) if the update fails.
     */
    @PutMapping("/{code}")
    public ResponseEntity<ErrorResponse> refreshEmployee(@PathVariable Long code, @RequestBody UEmployeeDTO newEmployeeDTO) {
        boolean validatedEmployee = service.validateEmployee(newEmployeeDTO);
        if (validatedEmployee) {
            Employee newEmployee = employeeMapper.uEmployeeDTOToEmployee(newEmployeeDTO);
            Employee oldEmployee = service.getEmployee(code);
            if (oldEmployee == null) {
                return new ResponseEntity<>(new ErrorResponse(4, "Employee not found"), HttpStatus.NOT_FOUND);
            } else {
                boolean response = service.refreshEmployee(oldEmployee, newEmployee);
                if (response) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(new ErrorResponse(5, "Error updating employee"), HttpStatus.CONFLICT);
                }
            }
        } else {
            return new ResponseEntity<>(new ErrorResponse(6, "Values for employee are wrong"), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes an employee from the system.
     *
     * @param code The ID of the employee to delete, passed as a path variable.
     * @return ResponseEntity with HTTP status 200 (OK) if deletion is successful,
     *         or HTTP status 404 (Not Found) if the employee doesn't exist.
     */
    @DeleteMapping("/{code}")
    public ResponseEntity<ErrorResponse> deleteEmployee(@PathVariable Long code) {
        boolean response = service.deleteEmployee(code);
        return response ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(new ErrorResponse(7, "Error deleting employee"), HttpStatus.CONFLICT);
    }
}

package com.example.practica3.service;

import com.example.practica3.DTOs.EmployeeDTOs.EmployeeDTO;
import com.example.practica3.DTOs.EmployeeDTOs.UEmployeeDTO;
import com.example.practica3.model.Employee;
import com.example.practica3.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IEmployeeService {

    private final EmployeeRepository repository;

    public IEmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public boolean insertNewEmployee(Employee employee) {
        if (repository == null || employee == null) {
            return false;
        }
        Employee employeeInDDBB = repository.findById(employee.getEmployeeID()).orElse(null);
        if (employeeInDDBB == null) {
            try {
                Employee employeeResult = repository.save(employee);
                return employeeResult.getEmployeeID() == employee.getEmployeeID() &&
                        employeeResult.getName().equals(employee.getName()) &&
                        employeeResult.getRole().equals(employee.getRole());
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployee(Long id) {
        return repository.findById(id).orElse(null);
    }


    public boolean deleteEmployee(Long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean refreshEmployee(Employee oldEmployee, Employee newEmployee) {
        try {
            oldEmployee.setName(newEmployee.getName());
            oldEmployee.setRole(newEmployee.getRole());
            oldEmployee.setPractice(newEmployee.getPractice());
            Employee employeeResultado = repository.save(oldEmployee);
            return employeeResultado.getName().equals(oldEmployee.getName()) &&
                    employeeResultado.getRole().equals(oldEmployee.getRole()) &&
                    employeeResultado.getPractice() == (oldEmployee.getPractice());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateEmployee(EmployeeDTO employeeDTO) {
        return employeeDTO.getCode() > 0 &&
                employeeDTO.getName() != null && !employeeDTO.getName().isEmpty() &&
                employeeDTO.getRole() != null && !employeeDTO.getRole().isEmpty() &&
                employeeDTO.getPractice() != null && !employeeDTO.getPractice().isEmpty();
    }

    public boolean validateEmployee(UEmployeeDTO employeeDTO) {
        return employeeDTO.getName() != null && !employeeDTO.getName().isEmpty() &&
                employeeDTO.getRole() != null && !employeeDTO.getRole().isEmpty() &&
                employeeDTO.getPractice() != null && !employeeDTO.getPractice().isEmpty();
    }
}

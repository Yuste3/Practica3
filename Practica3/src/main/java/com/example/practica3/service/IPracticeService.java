package com.example.practica3.service;

import com.example.practica3.DTOs.EmployeeDTO;
import com.example.practica3.DTOs.PracticeDTO;
import com.example.practica3.DTOs.UPracticeDTO;
import com.example.practica3.model.Employee;
import com.example.practica3.model.Practice;
import com.example.practica3.repository.EmployeeRepository;
import com.example.practica3.repository.PracticeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IPracticeService {

    private final PracticeRepository repository;
    private final EmployeeRepository employeeRepository;

    public IPracticeService(PracticeRepository repository, EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }

    public boolean insertNewPractice(Practice practice) {
        if (repository == null || practice == null) {
            return false;
        }

        try {
            Practice practiceResult = repository.save(practice);
            return practiceResult.getName().equals(practice.getName());
        } catch (Exception e) {
            return false;
        }
    }

    public List<Practice> getAllPractices() {
        return repository.findAll();
    }

    public Practice getPractice(String id) {
        return repository.findById(id).orElse(null);
    }


    public boolean deletePractice(String id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean refreshPractice(Practice oldPractice, Practice newPractice) {
        try {
            Practice practiceResult = repository.save(newPractice);
            List<Employee> employeesWithPractice = employeeRepository.findByPractice(oldPractice);
            for (Employee employee : employeesWithPractice) {
                employee.setPractice(newPractice);
                employeeRepository.save(employee);
            }
            deletePractice(oldPractice.getName());
            return practiceResult.getName().equals(newPractice.getName());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validatePractice(PracticeDTO practiceDTO) {
        return practiceDTO.getCode() != null && !practiceDTO.getCode().isEmpty();
    }

    public boolean validatePractice(UPracticeDTO practiceDTO) {
        return practiceDTO.getNewCode() != null && !practiceDTO.getNewCode().isEmpty();
    }
}

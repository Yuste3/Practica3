package com.example.practica3.repository;

import com.example.practica3.model.Employee;
import com.example.practica3.model.Practice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByPractice(Practice practice);
}

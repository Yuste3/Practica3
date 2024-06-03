package com.example.practica3.Practica3.service;

import com.example.practica3.model.Employee;
import com.example.practica3.repository.EmployeeRepository;
import com.example.practica3.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IEmployeesServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private IEmployeeService service;

    @Test
    void shouldInsertNewEmployee() {
        Employee employee = mock(Employee.class);
        when(repository.save(employee)).thenReturn(employee);
        when(employee.getName()).thenReturn("employeeName");
        when(employee.getRole()).thenReturn("employeeRole");
        boolean result = service.insertNewEmployee(employee);

        assertTrue(result);
        verify(repository, times(1)).save(employee);
    }

    @Test
    void shouldNotInsertNewEmployee() {
        Employee employee = mock(Employee.class);
        when(repository.save(employee)).thenReturn(null);

        boolean result = service.insertNewEmployee(employee);

        assertFalse(result);
        verify(repository, times(1)).save(employee);
    }

    @Test
    void shouldGetAllEmployees() {
        List<Employee> employees = mock(List.class);
        when(repository.findAll()).thenReturn(employees);

        List<Employee> employeesResult = service.getAllEmployees();

        assertThat(employees).isNotNull();
        assertEquals(employees, employeesResult);
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldNotGetAllEmployees() {
        when(repository.findAll()).thenReturn(null);

        List<Employee> employeesResult = service.getAllEmployees();

        assertNull(employeesResult);
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldGetEmployee() {
        Long id = 1L;
        Employee employee = mock(Employee.class);
        when(repository.findById(id)).thenReturn(Optional.of(employee));

        Employee employeeResult = service.getEmployee(id);

        assertNotNull(employee);
        assertNotNull(employeeResult);
        assertEquals(employee, employeeResult);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void shouldDeleteEmployee() {
        Long id = 1L;
        doNothing().when(repository).deleteById(id);

        boolean result = service.deleteEmployee(id);

        assertTrue(result);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void shouldNotDeleteEmployee() {
        Long id = 1L;
        doThrow(new RuntimeException()).when(repository).deleteById(id);

        boolean result = service.deleteEmployee(id);

        assertFalse(result);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void shouldRefreshEmployee() {
        Employee oldEmployee = mock(Employee.class);
        Employee newEmployee = mock(Employee.class);
        when(repository.save(oldEmployee)).thenReturn(oldEmployee);
        when(oldEmployee.getName()).thenReturn("employeeName");
        when(oldEmployee.getRole()).thenReturn("employeeRole");

        boolean result = service.refreshEmployee(oldEmployee, newEmployee);

        assertTrue(result);
        verify(repository, times(1)).save(oldEmployee);
    }

    @Test
    void shouldNotRefreshEmployee() {
        Employee oldEmployee = mock(Employee.class);
        Employee newEmployee = mock(Employee.class);
        when(repository.save(oldEmployee)).thenReturn(null);

        boolean result = service.refreshEmployee(oldEmployee, newEmployee);

        assertFalse(result);
        verify(repository, times(1)).save(oldEmployee);
    }
}

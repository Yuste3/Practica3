package com.example.practica3.Practica3.controller;

import com.example.practica3.DTOs.EmployeeDTOs.EmployeeDTO;
import com.example.practica3.DTOs.ErrorResponse;
import com.example.practica3.DTOs.EmployeeDTOs.UEmployeeDTO;
import com.example.practica3.Mappers.EmployeeMapper;
import com.example.practica3.controller.EmployeeAPI;
import com.example.practica3.model.Employee;
import com.example.practica3.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EmployeeAPITest {

    @Mock
    private IEmployeeService service;

    @Mock
    private EmployeeMapper mapper;

    @InjectMocks
    private EmployeeAPI controller;

    @Test
    public void shouldInsertEmployeeInDDBBWhenEmployeeIsSuccess() {
        EmployeeDTO employeeDTO = mock(EmployeeDTO.class);
        Employee employee = mock(Employee.class);

        when(service.validateEmployee(employeeDTO)).thenReturn(true);
        when(mapper.employeeDTOToEmployee(employeeDTO)).thenReturn(employee);
        when(service.insertNewEmployee(employee)).thenReturn(true);

        ResponseEntity<ErrorResponse> response = controller.insertEmployee(employeeDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).insertNewEmployee(employee);
    }

    @Test
    public void shouldNotInsertEmployeeInDDBBWhenEmployeeIsNotSuccess() {
        EmployeeDTO employeeDTO = mock(EmployeeDTO.class);
        Employee employee = mock(Employee.class);

        when(service.validateEmployee(employeeDTO)).thenReturn(true);
        when(mapper.employeeDTOToEmployee(employeeDTO)).thenReturn(employee);
        when(service.insertNewEmployee(employee)).thenReturn(false);

        ResponseEntity<ErrorResponse> response = controller.insertEmployee(employeeDTO);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(service, times(1)).insertNewEmployee(employee);
    }

    @Test
    public void shouldNotInsertEmployeeInDDBBWhenEmployeeIsNotValid() {
        EmployeeDTO employeeDTO = mock(EmployeeDTO.class);

        when(service.validateEmployee(employeeDTO)).thenReturn(false);

        ResponseEntity<ErrorResponse> response = controller.insertEmployee(employeeDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldGetAllEmployees() {
        List<Employee> employeesList = mock(ArrayList.class);
        when(service.getAllEmployees()).thenReturn(employeesList);

        ResponseEntity<List<EmployeeDTO>> response = controller.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).getAllEmployees();
    }

    @Test
    public void shouldGetEmployeeWanted() {
        Long id = 1L;
        Employee employee = new Employee();
        when(service.getEmployee(id)).thenReturn(employee);
        when(mapper.employeeToDTO(employee)).thenReturn(new EmployeeDTO());

        ResponseEntity<?> response = controller.getEmployee(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).getEmployee(id);
    }

    @Test
    public void shouldNotGetEmployeeWanted() {
        Long id = 1L;
        when(service.getEmployee(id)).thenReturn(null);

        ResponseEntity<?> response = controller.getEmployee(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(service, times(1)).getEmployee(id);
    }

    @Test
    public void shouldDeleteEmployeeWanted() {
        Long id = 1L;
        when(service.deleteEmployee(id)).thenReturn(true);

        ResponseEntity<ErrorResponse> response = controller.deleteEmployee(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deleteEmployee(id);
    }

    @Test
    public void shouldNotDeleteEmployeeWanted() {
        Long id = 1L;
        when(service.deleteEmployee(id)).thenReturn(false);

        ResponseEntity<ErrorResponse> response = controller.deleteEmployee(id);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(service, times(1)).deleteEmployee(id);
    }

    @Test
    public void shouldRefreshEmployeeWantedWhenEmployeeIsSuccess() {
        Long id = 1L;
        UEmployeeDTO newEmployeeDTO = new UEmployeeDTO();
        Employee newEmployee = new Employee();
        Employee oldEmployee = new Employee();

        when(service.validateEmployee(newEmployeeDTO)).thenReturn(true);
        when(service.getEmployee(id)).thenReturn(oldEmployee);
        when(mapper.uEmployeeDTOToEmployee(newEmployeeDTO)).thenReturn(newEmployee);
        when(service.refreshEmployee(oldEmployee, newEmployee)).thenReturn(true);

        ResponseEntity<ErrorResponse> response = controller.refreshEmployee(id, newEmployeeDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).refreshEmployee(oldEmployee, newEmployee);
    }

    @Test
    public void shouldNotRefreshEmployeeWantedBecauseEmployeeNotFound() {
        Long id = 1L;
        UEmployeeDTO newEmployeeDTO = new UEmployeeDTO();
        Employee newEmployee = new Employee();

        when(service.validateEmployee(newEmployeeDTO)).thenReturn(true);
        when(service.getEmployee(id)).thenReturn(null);
        when(mapper.uEmployeeDTOToEmployee(newEmployeeDTO)).thenReturn(newEmployee);

        ResponseEntity<ErrorResponse> response = controller.refreshEmployee(id, newEmployeeDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(service, times(0)).refreshEmployee(any(), any());
    }

    @Test
    public void shouldNotRefreshEmployeeWantedBecauseBadRequest() {
        Long id = 1L;
        UEmployeeDTO newEmployeeDTO = new UEmployeeDTO();
        Employee newEmployee = new Employee();
        Employee oldEmployee = new Employee();

        when(service.validateEmployee(newEmployeeDTO)).thenReturn(true);
        when(service.getEmployee(id)).thenReturn(oldEmployee);
        when(mapper.uEmployeeDTOToEmployee(newEmployeeDTO)).thenReturn(newEmployee);
        when(service.refreshEmployee(oldEmployee, newEmployee)).thenReturn(false);

        ResponseEntity<ErrorResponse> response = controller.refreshEmployee(id, newEmployeeDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(service, times(1)).refreshEmployee(oldEmployee, newEmployee);
    }
}

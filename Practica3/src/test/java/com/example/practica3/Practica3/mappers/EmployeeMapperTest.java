package com.example.practica3.Practica3.mappers;

import com.example.practica3.DTOs.EmployeeDTOs.EmployeeDTO;
import com.example.practica3.DTOs.EmployeeDTOs.UEmployeeDTO;
import com.example.practica3.Mappers.EmployeeMapper;
import com.example.practica3.model.Employee;
import com.example.practica3.model.Practice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeMapperTest {

    @Mock
    private EmployeeMapper mapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnEmployeeWhenEmployeeDTOIsMapped() {
        EmployeeDTO employeeDTO = mock(EmployeeDTO.class);
        Employee employee = mock(Employee.class);
        Practice practice = mock(Practice.class);

        when(mapper.employeeDTOToEmployee(any(EmployeeDTO.class))).thenReturn(employee);
        when(employee.getPractice()).thenReturn(practice);
        when(practice.getName()).thenReturn("Engineering");

        Employee result = mapper.employeeDTOToEmployee(employeeDTO);

        assertEquals("Engineering", result.getPractice().getName());
    }

    @Test
    public void shouldReturnEmployeeDTOWhenEmployeeIsMapped() {
        Employee employee = mock(Employee.class);
        EmployeeDTO employeeDTO = mock(EmployeeDTO.class);

        when(mapper.employeeToDTO(any(Employee.class))).thenReturn(employeeDTO);
        when(employee.getEmployeeID()).thenReturn(1L);
        when(employeeDTO.getCode()).thenReturn(1);
        when(employeeDTO.getPractice()).thenReturn("Engineering");

        EmployeeDTO result = mapper.employeeToDTO(employee);

        assertEquals(1, result.getCode());
        assertEquals("Engineering", result.getPractice());
    }

    @Test
    public void shouldReturnEmployeeWhenUEmployeeDTOIsMapped() {
        UEmployeeDTO uEmployeeDTO = mock(UEmployeeDTO.class);
        Employee employee = mock(Employee.class);
        Practice practice = mock(Practice.class);

        when(mapper.uEmployeeDTOToEmployee(any(UEmployeeDTO.class))).thenReturn(employee);
        when(employee.getPractice()).thenReturn(practice);
        when(practice.getName()).thenReturn("Engineering");

        Employee result = mapper.uEmployeeDTOToEmployee(uEmployeeDTO);

        assertEquals("Engineering", result.getPractice().getName());
    }
}

package com.example.practica3.Practica3.service;

import com.example.practica3.model.Assignment;
import com.example.practica3.model.Employee;
import com.example.practica3.repository.AssignmentRepository;
import com.example.practica3.repository.EmployeeRepository;
import com.example.practica3.service.IAssignmentService;
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
class IAssignmentServiceTest {

    @Mock
    private AssignmentRepository repository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private IAssignmentService service;

    @Test
    void shouldInsertNewAssignment() {
        Assignment assignment = mock(Assignment.class);
        when(repository.save(assignment)).thenReturn(assignment);
        boolean result = service.insertNewAssignment(assignment);

        assertTrue(result);
        verify(repository, times(1)).save(assignment);
    }

    @Test
    void shouldNotInsertNewAssignment() {
        Assignment assignment = mock(Assignment.class);
        when(repository.save(assignment)).thenReturn(null);

        boolean result = service.insertNewAssignment(assignment);

        assertFalse(result);
        verify(repository, times(1)).save(assignment);
    }

    @Test
    void shouldGetAllAssignment() {
        List<Assignment> assignments = mock(List.class);
        when(repository.findAll()).thenReturn(assignments);

        List<Assignment> assignmentResult = service.getAllAssignments();

        assertThat(assignments).isNotNull();
        assertEquals(assignments, assignmentResult);
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldNotGetAllAssignment() {
        when(repository.findAll()).thenReturn(null);

        List<Assignment> assignmentResult = service.getAllAssignments();

        assertNull(assignmentResult);
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldGetAssignment() {
        Long id = 1L;
        Assignment assignment = mock(Assignment.class);
        when(repository.findById(id)).thenReturn(Optional.of(assignment));

        Assignment assignmentResult = service.getAssignment(id);

        assertNotNull(assignment);
        assertNotNull(assignmentResult);
        assertEquals(assignment, assignmentResult);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void shouldDeleteAssignment() {
        Long id = 1L;
        doNothing().when(repository).deleteById(id);

        boolean result = service.deleteAssignment(id);

        assertTrue(result);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void shouldNotDeleteAssignment() {
        Long id = 1L;
        doThrow(new RuntimeException()).when(repository).deleteById(id);

        boolean result = service.deleteAssignment(id);

        assertFalse(result);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void shouldRefreshAssignment() {
        Assignment oldAssignment = mock(Assignment.class);
        Assignment newAssignment = mock(Assignment.class);
        Employee employee = mock(Employee.class);
        when(repository.save(oldAssignment)).thenReturn(oldAssignment);
        when(newAssignment.getEmployee()).thenReturn(employee);
        when(newAssignment.getSupervisor()).thenReturn(employee);
        when(employeeRepository.findById(employee.getEmployeeID())).thenReturn(Optional.of(employee));
        boolean result = service.refreshAssignment(oldAssignment, newAssignment);

        assertTrue(result);
        verify(repository, times(1)).save(oldAssignment);
    }

    @Test
    void shouldNotRefreshAssignment() {
        Assignment oldAssignment= mock(Assignment.class);
        Assignment newAssignment = mock(Assignment.class);
        Employee employee = mock(Employee.class);

        when(repository.save(oldAssignment)).thenReturn(null);
        when(newAssignment.getEmployee()).thenReturn(employee);
        when(newAssignment.getSupervisor()).thenReturn(employee);
        when(employeeRepository.findById(employee.getEmployeeID())).thenReturn(Optional.of(employee));
        boolean result = service.refreshAssignment(oldAssignment, newAssignment);

        assertFalse(result);
    }
}

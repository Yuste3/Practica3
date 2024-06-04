package com.example.practica3.Practica3.controller;

import com.example.practica3.DTOs.AssignmentDTOs.AssignmentDTO;
import com.example.practica3.DTOs.AssignmentDTOs.AssignmentResponseDTO;
import com.example.practica3.DTOs.ErrorResponse;
import com.example.practica3.Mappers.AssignmentMapper;
import com.example.practica3.controller.AssignmentAPI;
import com.example.practica3.model.Assignment;
import com.example.practica3.service.IAssignmentService;
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
public class AssignmentAPITest {

    @Mock
    private IAssignmentService service;

    @Mock
    private AssignmentMapper mapper;

    @InjectMocks
    private AssignmentAPI controller;

    @Test
    public void shouldInsertAssignmentInDDBBWhenAssignmentIsSuccess() {
        AssignmentDTO assignmentDTO = mock(AssignmentDTO.class);
        Assignment assignment = mock(Assignment.class);

        when(service.validateAssignment(assignmentDTO)).thenReturn(true);
        when(mapper.assignmentDTOToAssignment(assignmentDTO)).thenReturn(assignment);
        when(service.insertNewAssignment(assignment)).thenReturn(true);

        ResponseEntity<ErrorResponse> response = controller.insertAssignment(assignmentDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).insertNewAssignment(assignment);
    }

    @Test
    public void shouldNotInsertAssignmentInDDBBWhenAssignmentIsNotSuccess() {
        AssignmentDTO assignmentDTO = mock(AssignmentDTO.class);
        Assignment assignment = mock(Assignment.class);

        when(service.validateAssignment(assignmentDTO)).thenReturn(true);
        when(mapper.assignmentDTOToAssignment(assignmentDTO)).thenReturn(assignment);
        when(service.insertNewAssignment(assignment)).thenReturn(false);

        ResponseEntity<ErrorResponse> response = controller.insertAssignment(assignmentDTO);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(service, times(1)).insertNewAssignment(assignment);
    }

    @Test
    public void shouldNotInsertAssignmentInDDBBWhenAssignmentIsNotValid() {
        AssignmentDTO assignmentDTO = mock(AssignmentDTO.class);

        when(service.validateAssignment(assignmentDTO)).thenReturn(false);

        ResponseEntity<ErrorResponse> response = controller.insertAssignment(assignmentDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldGetAllAssignments() {
        List<Assignment> assignmentList = mock(ArrayList.class);
        when(service.getAllAssignments()).thenReturn(assignmentList);

        ResponseEntity<List<AssignmentResponseDTO>> response = controller.getAllAssignments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).getAllAssignments();
    }

    @Test
    public void shouldGetAssignmentWanted() {
        Long id = 1L;
        Assignment assignment = mock(Assignment.class);
        when(service.getAssignment(id)).thenReturn(assignment);
        when(mapper.assignmentToResponseDTO(assignment)).thenReturn(new AssignmentResponseDTO());

        ResponseEntity<?> response = controller.getAssignment(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).getAssignment(id);
    }

    @Test
    public void shouldNotGetAssignmentWanted() {
        Long id = 1L;
        when(service.getAssignment(id)).thenReturn(null);

        ResponseEntity<?> response = controller.getAssignment(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(service, times(1)).getAssignment(id);
    }

    @Test
    public void shouldDeleteAssignmentWanted() {
        Long id = 1L;
        when(service.deleteAssignment(id)).thenReturn(true);

        ResponseEntity<ErrorResponse> response = controller.deleteAssignment(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deleteAssignment(id);
    }

    @Test
    public void shouldNotDeleteAssignmentWanted() {
        Long id = 1L;
        when(service.deleteAssignment(id)).thenReturn(false);

        ResponseEntity<ErrorResponse> response = controller.deleteAssignment(id);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(service, times(1)).deleteAssignment(id);
    }

    @Test
    public void shouldRefreshAssignmentWantedWhenAssignmentIsSuccess() {
        Long id = 1L;
        AssignmentDTO newAssigmentDTO = mock(AssignmentDTO.class);
        Assignment newAssignment = mock(Assignment.class);
        Assignment oldAssignment = mock(Assignment.class);

        when(service.validateAssignment(newAssigmentDTO)).thenReturn(true);
        when(service.getAssignment(id)).thenReturn(oldAssignment);
        when(mapper.assignmentDTOToAssignment(newAssigmentDTO)).thenReturn(newAssignment);
        when(service.refreshAssignment(oldAssignment, newAssignment)).thenReturn(true);

        ResponseEntity<ErrorResponse> response = controller.refreshAssignment(id, newAssigmentDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).refreshAssignment(oldAssignment, newAssignment);
    }

    @Test
    public void shouldNotRefreshAssignmentWantedBecauseAssignmentNotFound() {
        Long id = 1L;
        AssignmentDTO newAssignmentDTO = mock(AssignmentDTO.class);
        Assignment newAssigment = mock(Assignment.class);

        when(service.validateAssignment(newAssignmentDTO)).thenReturn(true);
        when(service.getAssignment(id)).thenReturn(null);
        when(mapper.assignmentDTOToAssignment(newAssignmentDTO)).thenReturn(newAssigment);

        ResponseEntity<ErrorResponse> response = controller.refreshAssignment(id, newAssignmentDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(service, times(0)).refreshAssignment(any(), any());
    }

    @Test
    public void shouldNotRefreshEmployeeWantedBecauseBadRequest() {
        Long id = 1L;
        AssignmentDTO newAssignmentDTO = mock(AssignmentDTO.class);
        Assignment newAssignment = mock(Assignment.class);
        Assignment oldAssignment = mock(Assignment.class);

        when(service.validateAssignment(newAssignmentDTO)).thenReturn(true);
        when(service.getAssignment(id)).thenReturn(oldAssignment);
        when(mapper.assignmentDTOToAssignment(newAssignmentDTO)).thenReturn(newAssignment);
        when(service.refreshAssignment(oldAssignment, newAssignment)).thenReturn(false);

        ResponseEntity<ErrorResponse> response = controller.refreshAssignment(id, newAssignmentDTO);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(service, times(1)).refreshAssignment(oldAssignment, newAssignment);
    }
}

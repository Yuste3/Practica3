package com.example.practica3.Practica3.mappers;

import com.example.practica3.DTOs.AssignmentDTOs.AssignmentDTO;
import com.example.practica3.DTOs.AssignmentDTOs.AssignmentResponseDTO;
import com.example.practica3.DTOs.EmployeeDTOs.EmployeeDTO;
import com.example.practica3.DTOs.EmployeeDTOs.UEmployeeDTO;
import com.example.practica3.Mappers.AssignmentMapper;
import com.example.practica3.model.Assignment;
import com.example.practica3.model.Employee;
import com.example.practica3.model.Practice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AssignmentMapperTest {

    @Mock
    private AssignmentMapper mapper;


    @Test
    public void shouldReturnAssignmentWhenAssignmentDTOIsMapped() {
        AssignmentDTO assignmentDTO = mock(AssignmentDTO.class);
        Assignment assignment = mock(Assignment.class);

        when(mapper.assignmentDTOToAssignment(any(AssignmentDTO.class))).thenReturn(assignment);


        Assignment result = mapper.assignmentDTOToAssignment(assignmentDTO);

        assertEquals(result, assignment);
    }

    @Test
    public void shouldReturnAssignmentDTOWhenAssignmentIsMapped() {
        Assignment assignment = mock(Assignment.class);
        AssignmentResponseDTO assignmentDTO = mock(AssignmentResponseDTO.class);

        when(mapper.assignmentToResponseDTO(any(Assignment.class))).thenReturn(assignmentDTO);

        AssignmentResponseDTO result = mapper.assignmentToResponseDTO(assignment);

        assertEquals(result, assignmentDTO);
    }

}

package com.example.practica3.Practica3.controller;

import com.example.practica3.DTOs.PracticeDTO;
import com.example.practica3.DTOs.UPracticeDTO;
import com.example.practica3.Mappers.PracticeMapper;
import com.example.practica3.controller.PracticeAPI;
import com.example.practica3.model.Practice;
import com.example.practica3.service.IPracticeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PracticeAPITest {

    @Mock
    private IPracticeService service;

    @Mock
    private PracticeMapper mapper;

    @InjectMocks
    private PracticeAPI controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldInsertPracticeWhenPracticeIsValid() {
        PracticeDTO practiceDTO = new PracticeDTO();
        Practice practice = new Practice();

        when(service.validatePractice(practiceDTO)).thenReturn(true);
        when(mapper.practiceDTOToPractice(practiceDTO)).thenReturn(practice);
        when(service.insertNewPractice(practice)).thenReturn(true);

        ResponseEntity<PracticeDTO> response = controller.insertPractice(practiceDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).insertNewPractice(practice);
    }

    @Test
    public void shouldNotInsertPracticeWhenServiceFails() {
        PracticeDTO practiceDTO = new PracticeDTO();
        Practice practice = new Practice();

        when(service.validatePractice(practiceDTO)).thenReturn(true);
        when(mapper.practiceDTOToPractice(practiceDTO)).thenReturn(practice);
        when(service.insertNewPractice(practice)).thenReturn(false);

        ResponseEntity<PracticeDTO> response = controller.insertPractice(practiceDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(service, times(1)).insertNewPractice(practice);
    }

    @Test
    public void shouldNotInsertPracticeWhenPracticeIsInvalid() {
        PracticeDTO practiceDTO = new PracticeDTO();

        when(service.validatePractice(practiceDTO)).thenReturn(false);

        ResponseEntity<PracticeDTO> response = controller.insertPractice(practiceDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldGetAllPractices() {
        List<Practice> practices = new ArrayList<>();
        practices.add(new Practice());
        when(service.getAllPractices()).thenReturn(practices);
        when(mapper.practiceToDTO(any(Practice.class))).thenReturn(new PracticeDTO());

        ResponseEntity<List<PracticeDTO>> response = controller.getAllPractices();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(practices.size(), response.getBody().size());
    }

    @Test
    void shouldGetPracticeByCode() {
        String code = "1";
        Practice practice = new Practice();
        when(service.getPractice(code)).thenReturn(practice);
        when(mapper.practiceToDTO(practice)).thenReturn(new PracticeDTO());

        ResponseEntity<PracticeDTO> response = controller.getPractice(code);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void shouldNotGetPracticeWhenNotFound() {
        String code = "1";
        when(service.getPractice(code)).thenReturn(null);

        ResponseEntity<PracticeDTO> response = controller.getPractice(code);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldRefreshPracticeWhenPracticeIsValid() {
        String code = "1";
        UPracticeDTO newPracticeDTO = new UPracticeDTO();
        Practice newPractice = new Practice();
        Practice oldPractice = new Practice();
        when(service.validatePractice(newPracticeDTO)).thenReturn(true);
        when(mapper.uPracticeDTOToPractice(newPracticeDTO)).thenReturn(newPractice);
        when(service.getPractice(code)).thenReturn(oldPractice);
        when(service.refreshPractice(oldPractice, newPractice)).thenReturn(true);

        ResponseEntity<Practice> response = controller.refreshPractice(code, newPracticeDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).refreshPractice(oldPractice, newPractice);
    }

    @Test
    void shouldNotRefreshPracticeWhenServiceFails() {
        String code = "1";
        UPracticeDTO newPracticeDTO = new UPracticeDTO();
        Practice newPractice = new Practice();
        Practice oldPractice = new Practice();
        when(service.validatePractice(newPracticeDTO)).thenReturn(true);
        when(mapper.uPracticeDTOToPractice(newPracticeDTO)).thenReturn(newPractice);
        when(service.getPractice(code)).thenReturn(oldPractice);
        when(service.refreshPractice(oldPractice, newPractice)).thenReturn(false);

        ResponseEntity<Practice> response = controller.refreshPractice(code, newPracticeDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldNotRefreshPracticeWhenNotFound() {
        String code = "1";
        UPracticeDTO newPracticeDTO = new UPracticeDTO();
        when(service.getPractice(code)).thenReturn(null);
        when(service.validatePractice(newPracticeDTO)).thenReturn(true);

        ResponseEntity<Practice> response = controller.refreshPractice(code, newPracticeDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldDeletePractice() {
        String code = "1";
        when(service.deletePractice(code)).thenReturn(true);

        ResponseEntity<HttpStatus> response = controller.deletePractice(code);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldNotDeletePractice() {
        String code = "1";
        when(service.deletePractice(code)).thenReturn(false);

        ResponseEntity<HttpStatus> response = controller.deletePractice(code);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}

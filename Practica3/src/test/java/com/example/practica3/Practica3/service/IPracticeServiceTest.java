package com.example.practica3.Practica3.service;

import com.example.practica3.model.Practice;
import com.example.practica3.repository.PracticeRepository;
import com.example.practica3.repository.EmployeeRepository;
import com.example.practica3.service.IPracticeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IPracticeServiceTest {

    @Mock
    private PracticeRepository repository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private IPracticeService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldInsertNewPracticeWhenPracticeIsSuccess() {
        Practice practice = mock(Practice.class);
        when(repository.save(practice)).thenReturn(practice);
        when(practice.getName()).thenReturn("practiceCode");
        boolean result = service.insertNewPractice(practice);
        assertTrue(result);
        verify(repository, times(1)).save(practice);
    }

    @Test
    void shouldNotInsertNewPractice() {
        Practice practice = mock(Practice.class);
        when(repository.save(practice)).thenReturn(null);
        boolean result = service.insertNewPractice(practice);
        assertFalse(result);
        verify(repository, times(1)).save(practice);
    }

    @Test
    void shouldGetAllPracticesInSystem() {
        List<Practice> practices = mock(List.class);
        when(repository.findAll()).thenReturn(practices);
        List<Practice> practicesResult = service.getAllPractices();
        assertThat(practicesResult).isNotNull();
        assertEquals(practices, practicesResult);
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldNotGetAllPracticesInSystem() {
        when(repository.findAll()).thenReturn(null);
        List<Practice> practicesResult = service.getAllPractices();
        assertNull(practicesResult);
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldGetPracticeWanted() {
        String id = "1";
        Practice practice = mock(Practice.class);
        when(repository.findById(id)).thenReturn(Optional.ofNullable(practice));
        Practice practiceResult = service.getPractice(id);
        assertNotNull(practiceResult);
        assertEquals(practiceResult, practice);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void shouldDeletePracticeWanted() {
        String id = "1";
        doNothing().when(repository).deleteById(id);
        boolean result = service.deletePractice(id);
        assertTrue(result);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void shouldNotDeletePracticeWanted() {
        String id = "1";
        doThrow(new RuntimeException()).when(repository).deleteById(id);
        boolean result = service.deletePractice(id);
        assertFalse(result);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void shouldRefreshPracticeWanted() {
        Practice oldPractice = mock(Practice.class);
        Practice newPractice = mock(Practice.class);
        when(repository.save(newPractice)).thenReturn(newPractice);
        when(newPractice.getName()).thenReturn("practiceCode");
        boolean result = service.refreshPractice(oldPractice, newPractice);
        assertTrue(result);
        verify(repository, times(1)).save(newPractice);
    }

    @Test
    void shouldNotRefreshPracticeWanted() {
        Practice oldPractice = mock(Practice.class);
        Practice newPractice = mock(Practice.class);
        when(repository.save(newPractice)).thenReturn(null);
        boolean result = service.refreshPractice(oldPractice, newPractice);
        assertFalse(result);
        verify(repository, times(1)).save(newPractice);
    }
}

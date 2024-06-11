package com.example.practica3.Practica3.mappers;

import com.example.practica3.DTOs.PracticeDTOs.PracticeDTO;
import com.example.practica3.DTOs.PracticeDTOs.UPracticeDTO;
import com.example.practica3.Mappers.PracticeMapper;
import com.example.practica3.model.Practice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PracticeMapperTest {

    @InjectMocks
    private PracticeMapper mapper = Mappers.getMapper(PracticeMapper.class);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnPracticeWhenPracticeDTOIsMapped() {
        PracticeDTO practiceDTO = mock(PracticeDTO.class);
        Practice practice = new Practice();
        practice.setName("testName");

        when(practiceDTO.getCode()).thenReturn("testCode");

        Practice result = mapper.practiceDTOToPractice(practiceDTO);

        assertEquals("testCode", result.getName());
    }

    @Test
    public void shouldReturnPracticeDTOWhenPracticeIsMapped() {
        Practice practice = mock(Practice.class);
        PracticeDTO practiceDTO = new PracticeDTO();
        practiceDTO.setCode("testCode");

        when(practice.getName()).thenReturn("testCode");

        PracticeDTO result = mapper.practiceToDTO(practice);

        assertEquals("testCode", result.getCode());
    }

    @Test
    public void shouldReturnPracticeWhenUPracticeDTOIsMapped() {
        UPracticeDTO uPracticeDTO = mock(UPracticeDTO.class);
        Practice practice = new Practice();
        practice.setName("newTestName");

        when(uPracticeDTO.getNewCode()).thenReturn("newTestCode");

        Practice result = mapper.uPracticeDTOToPractice(uPracticeDTO);

        assertEquals("newTestCode", result.getName());
    }
}

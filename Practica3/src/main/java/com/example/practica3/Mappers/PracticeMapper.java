package com.example.practica3.Mappers;

import com.example.practica3.DTOs.PracticeDTOs.PracticeDTO;
import com.example.practica3.DTOs.PracticeDTOs.UPracticeDTO;
import com.example.practica3.model.Practice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PracticeMapper {

    @Mapping(source="code", target="name")
    Practice practiceDTOToPractice(PracticeDTO practiceDTO);

    @Mapping(source="name", target="code")
    PracticeDTO practiceToDTO(Practice practice);

    @Mapping(source="newCode", target="name")
    Practice uPracticeDTOToPractice(UPracticeDTO uPracticeDTO);

}

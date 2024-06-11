package com.example.practica3.Mappers;

import com.example.practica3.DTOs.PracticeDTOs.PracticeDTO;
import com.example.practica3.DTOs.PracticeDTOs.UPracticeDTO;
import com.example.practica3.model.Practice;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-11T09:35:43+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class PracticeMapperImpl implements PracticeMapper {

    @Override
    public Practice practiceDTOToPractice(PracticeDTO practiceDTO) {
        if ( practiceDTO == null ) {
            return null;
        }

        Practice.PracticeBuilder practice = Practice.builder();

        practice.name( practiceDTO.getCode() );

        return practice.build();
    }

    @Override
    public PracticeDTO practiceToDTO(Practice practice) {
        if ( practice == null ) {
            return null;
        }

        PracticeDTO practiceDTO = new PracticeDTO();

        practiceDTO.setCode( practice.getName() );

        return practiceDTO;
    }

    @Override
    public Practice uPracticeDTOToPractice(UPracticeDTO uPracticeDTO) {
        if ( uPracticeDTO == null ) {
            return null;
        }

        Practice.PracticeBuilder practice = Practice.builder();

        practice.name( uPracticeDTO.getNewCode() );

        return practice.build();
    }
}

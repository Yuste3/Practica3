package com.example.practica3.Mappers;

import com.example.practica3.DTOs.PracticeDTO;
import com.example.practica3.DTOs.ProjectDTO;
import com.example.practica3.DTOs.UPracticeDTO;
import com.example.practica3.DTOs.UProjectDTO;
import com.example.practica3.model.Practice;
import com.example.practica3.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(source="code", target="projectCode")
    Project projectDTOToProject(ProjectDTO projectDTO);

    @Mapping(source="projectCode", target="code")
    ProjectDTO projectToDTO(Project project);

    @Mapping(source="newCode", target="projectCode")
    Project uProjectDTOToProject(UProjectDTO uProjectDTO);

}

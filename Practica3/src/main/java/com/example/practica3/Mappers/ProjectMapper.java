package com.example.practica3.Mappers;

import com.example.practica3.DTOs.ProjectDTO;
import com.example.practica3.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(source="projectCode", target="projectCode")
    Project projectDTOToProject(ProjectDTO projectDTO);
}

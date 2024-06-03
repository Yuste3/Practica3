package com.example.practica3.Mappers;

import com.example.practica3.DTOs.ProjectDTO;
import com.example.practica3.model.Project;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-28T10:11:49+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public Project projectDTOToProject(ProjectDTO projectDTO) {
        if ( projectDTO == null ) {
            return null;
        }

        Project.ProjectBuilder project = Project.builder();

        project.projectCode( projectDTO.getProjectCode() );

        return project.build();
    }
}

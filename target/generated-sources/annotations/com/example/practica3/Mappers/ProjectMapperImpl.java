package com.example.practica3.Mappers;

import com.example.practica3.DTOs.ProjectDTO;
import com.example.practica3.DTOs.UProjectDTO;
import com.example.practica3.model.Project;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T13:37:53+0200",
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

        project.projectCode( projectDTO.getCode() );

        return project.build();
    }

    @Override
    public ProjectDTO projectToDTO(Project project) {
        if ( project == null ) {
            return null;
        }

        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setCode( project.getProjectCode() );

        return projectDTO;
    }

    @Override
    public Project uProjectDTOToProject(UProjectDTO uProjectDTO) {
        if ( uProjectDTO == null ) {
            return null;
        }

        Project.ProjectBuilder project = Project.builder();

        project.projectCode( uProjectDTO.getNewCode() );

        return project.build();
    }
}

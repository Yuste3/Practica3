package com.example.practica3.Practica3.mappers;

import com.example.practica3.DTOs.ProjectDTO;
import com.example.practica3.DTOs.UProjectDTO;
import com.example.practica3.Mappers.ProjectMapper;
import com.example.practica3.model.Project;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProjectMapperTest {

    @InjectMocks
    private ProjectMapper mapper = Mappers.getMapper(ProjectMapper.class);

    @Test
    public void shouldReturnProjectWhenProjectDTOIsMapped() {
        ProjectDTO projectDTO = mock(ProjectDTO.class);
        Project project = new Project();
        project.setProjectCode("testName");

        when(projectDTO.getCode()).thenReturn("testCode");

        Project result = mapper.projectDTOToProject(projectDTO);

        assertEquals("testCode", result.getProjectCode());
    }

    @Test
    public void shouldReturnProjectDTOWhenProjectIsMapped() {
        Project project = mock(Project.class);
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setCode("testCode");

        when(project.getProjectCode()).thenReturn("testCode");

        ProjectDTO result = mapper.projectToDTO(project);

        assertEquals("testCode", result.getCode());
    }

    @Test
    public void shouldReturnProjectWhenUProjectDTOIsMapped() {
        UProjectDTO uProjectDTO = mock(UProjectDTO.class);
        Project project = new Project();
        project.setProjectCode("newTestName");

        when(uProjectDTO.getNewCode()).thenReturn("newTestCode");

        Project result = mapper.uProjectDTOToProject(uProjectDTO);

        assertEquals("newTestCode", result.getProjectCode());
    }
}

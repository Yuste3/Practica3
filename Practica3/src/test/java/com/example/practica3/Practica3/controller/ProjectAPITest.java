package com.example.practica3.Practica3.controller;

import com.example.practica3.DTOs.ProjectDTO;
import com.example.practica3.DTOs.UProjectDTO;
import com.example.practica3.Mappers.ProjectMapper;
import com.example.practica3.controller.ProjectAPI;
import com.example.practica3.model.Project;
import com.example.practica3.service.IProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProjectAPITest {

    @Mock
    private IProjectService service;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectAPI controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldInsertProjectInDDBBWhenProjectIsSuccess() {
        ProjectDTO projectDTO = new ProjectDTO();
        Project project = new Project();

        when(service.validateProject(projectDTO)).thenReturn(true);
        when(projectMapper.projectDTOToProject(projectDTO)).thenReturn(project);
        when(service.insertNewProject(project)).thenReturn(true);

        ResponseEntity<ProjectDTO> response = controller.insertProject(projectDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void shouldNotInsertProjectInDDBBWhenProjectIsNotSuccess() {
        ProjectDTO projectDTO = new ProjectDTO();
        Project project = new Project();

        when(service.validateProject(projectDTO)).thenReturn(true);
        when(projectMapper.projectDTOToProject(projectDTO)).thenReturn(project);
        when(service.insertNewProject(project)).thenReturn(false);

        ResponseEntity<ProjectDTO> response = controller.insertProject(projectDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldNotInsertProjectInDDBBWhenProjectIsNotValid() {
        ProjectDTO projectDTO = new ProjectDTO();

        when(service.validateProject(projectDTO)).thenReturn(false);

        ResponseEntity<ProjectDTO> response = controller.insertProject(projectDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldGetAllProjects() {
        List<Project> projects = new ArrayList<>();
        List<ProjectDTO> projectDTOs = new ArrayList<>();

        when(service.getAllProjects()).thenReturn(projects);
        when(projectMapper.projectToDTO(any())).thenReturn(null);

        ResponseEntity<List<ProjectDTO>> response = controller.getAllProjects();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldGetProjectWanted() {
        String code = "1";
        Project project = new Project();
        ProjectDTO projectDTO = new ProjectDTO();

        when(service.getProject(code)).thenReturn(project);
        when(projectMapper.projectToDTO(project)).thenReturn(projectDTO);

        ResponseEntity<ProjectDTO> response = controller.getProject(code);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldNotGetProjectWanted() {
        String code = "1";

        when(service.getProject(code)).thenReturn(null);

        ResponseEntity<ProjectDTO> response = controller.getProject(code);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldRefreshProjectWanted() {
        String code = "1";
        UProjectDTO newProjectDTO = new UProjectDTO();
        Project newProject = new Project();
        Project oldProject = new Project();

        when(service.validateProject(newProjectDTO)).thenReturn(true);
        when(projectMapper.uProjectDTOToProject(newProjectDTO)).thenReturn(newProject);
        when(service.getProject(code)).thenReturn(oldProject);
        when(service.refreshProject(oldProject, newProject)).thenReturn(true);

        ResponseEntity<Project> response = controller.refreshProject(code, newProjectDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldNotRefreshProjectWantedBecauseNotFound() {
        String code = "1";
        UProjectDTO newProjectDTO = new UProjectDTO();

        when(service.validateProject(newProjectDTO)).thenReturn(true);
        when(service.getProject(code)).thenReturn(null);

        ResponseEntity<Project> response = controller.refreshProject(code, newProjectDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldNotRefreshProjectWanted() {
        String code = "1";
        UProjectDTO newProjectDTO = new UProjectDTO();
        Project newProject = new Project();
        Project oldProject = new Project();

        when(service.validateProject(newProjectDTO)).thenReturn(true);
        when(projectMapper.uProjectDTOToProject(newProjectDTO)).thenReturn(newProject);
        when(service.getProject(code)).thenReturn(oldProject);
        when(service.refreshProject(oldProject, newProject)).thenReturn(false);

        ResponseEntity<Project> response = controller.refreshProject(code, newProjectDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldDeleteProjectWanted() {
        String code = "1";

        when(service.deleteProject(code)).thenReturn(true);

        ResponseEntity<HttpStatus> response = controller.deleteProject(code);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldNotDeleteProjectWanted() {
        String code = "1";

        when(service.deleteProject(code)).thenReturn(false);

        ResponseEntity<HttpStatus> response = controller.deleteProject(code);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}

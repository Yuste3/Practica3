package com.example.practica3.Practica3.service;

import com.example.practica3.model.Project;
import com.example.practica3.repository.ProjectRepository;
import com.example.practica3.service.IProjectService;
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

class IProjectServiceTest {

    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private IProjectService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldInsertNewProjectWhenProjectIsSuccess() {
        Project project = mock(Project.class);
        when(project.getProjectCode()).thenReturn("projectName");
        when(repository.save(project)).thenReturn(project);
        boolean result = service.insertNewProject(project);
        assertTrue(result);
        verify(repository, times(1)).save(project);
    }

    @Test
    void shouldNotInsertNewProject() {
        Project project = mock(Project.class);
        when(repository.save(project)).thenReturn(null);
        boolean result = service.insertNewProject(project);
        assertFalse(result);
        verify(repository, times(1)).save(project);
    }

    @Test
    void shouldGetAllProjectsInSystem() {
        List<Project> projects = mock(List.class);
        when(repository.findAll()).thenReturn(projects);
        List<Project> projectsResult = service.getAllProjects();
        assertThat(projects).isNotNull();
        assertEquals(projects, projectsResult);
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldNotGetAllProjectsInSystem() {
        when(repository.findAll()).thenReturn(null);
        List<Project> projectsResult = service.getAllProjects();
        assertNull(projectsResult);
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldGetProjectWanted() {
        String id = "1";
        Project projectWanted = mock(Project.class);
        when(repository.findById(id)).thenReturn(Optional.ofNullable(projectWanted));
        Project projectResult = service.getProject(id);
        assertThat(projectWanted).isNotNull();
        assertEquals(projectWanted, projectResult);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void shouldDeleteProjectWanted() {
        String id = "1";
        doNothing().when(repository).deleteById(id);
        boolean result = service.deleteProject(id);
        assertTrue(result);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void shouldNotDeleteProjectWanted() {
        String id = "1";
        doThrow(new RuntimeException()).when(repository).deleteById(id);
        boolean result = service.deleteProject(id);
        assertFalse(result);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void shouldRefreshProjectWanted() {
        Project oldProject = mock(Project.class);
        Project newProject = mock(Project.class);
        when(repository.save(newProject)).thenReturn(newProject);
        when(oldProject.getProjectCode()).thenReturn("testCode");
        when(newProject.getProjectCode()).thenReturn("testCode");
        boolean result = service.refreshProject(oldProject, newProject);
        assertTrue(result);
        verify(repository, times(1)).save(newProject);
        verify(repository, times(1)).deleteById("testCode");
    }

    @Test
    void shouldNotRefreshProjectWanted() {
        Project oldProject = mock(Project.class);
        Project newProject = mock(Project.class);
        when(repository.save(newProject)).thenReturn(null);
        boolean result = service.refreshProject(oldProject, newProject);
        assertFalse(result);
        verify(repository, times(1)).save(newProject);
    }
}

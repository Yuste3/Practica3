package com.example.practica3.service;

import com.example.practica3.DTOs.ProjectDTO;
import com.example.practica3.model.Project;
import com.example.practica3.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IProjectService {

    private final ProjectRepository repository;

    public IProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public boolean insertNewProject(Project project) {
        if (repository == null || project == null) {
            return false;
        }

        try {
            Project projectResult = repository.save(project);
            return projectResult.getProjectCode().equals(projectResult.getProjectCode());
        } catch (Exception e) {
            return false;
        }
    }

    public List<Project> getAllProjects() {
        return repository.findAll();
    }

    public Project getProject(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean deleteProject(Long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean refreshProject(Project oldProject, Project newProject) {
        try {
            oldProject.setProjectCode(newProject.getProjectCode());
            Project projectResult = repository.save(oldProject);
            return projectResult.getProjectCode().equals(oldProject.getProjectCode());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateProject(ProjectDTO projectDTO) {
        return projectDTO.getProjectCode() != null && !projectDTO.getProjectCode().isEmpty();
    }
}

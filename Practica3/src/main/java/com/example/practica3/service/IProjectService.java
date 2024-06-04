package com.example.practica3.service;

import com.example.practica3.DTOs.EmployeeDTO;
import com.example.practica3.DTOs.ProjectDTO;
import com.example.practica3.DTOs.UProjectDTO;
import com.example.practica3.model.Employee;
import com.example.practica3.model.Project;
import com.example.practica3.repository.EmployeeRepository;
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
        Project projectInDDBB = repository.findById(project.getProjectCode()).orElse(null);
        if (projectInDDBB == null) {
            try {
                Project projectResult = repository.save(project);
                return projectResult.getProjectCode().equals(project.getProjectCode());
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public List<Project> getAllProjects() {
        return repository.findAll();
    }

    public Project getProject(String id) {
        return repository.findById(id).orElse(null);
    }

    public boolean deleteProject(String id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean refreshProject(Project oldProject, Project newProject) {
        try {
            Project projectResult = repository.save(newProject);
            repository.deleteById(oldProject.getProjectCode());
            return projectResult.getProjectCode().equals(newProject.getProjectCode());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateProject(ProjectDTO projectDTO) {
        return projectDTO.getCode() != null && !projectDTO.getCode().isEmpty();
    }

    public boolean validateProject(UProjectDTO projectDTO) {
        return projectDTO.getNewCode() != null && !projectDTO.getNewCode().isEmpty();
    }
}

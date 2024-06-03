package com.example.practica3.controller;

import com.example.practica3.DTOs.ProjectDTO;
import com.example.practica3.Mappers.ProjectMapper;
import com.example.practica3.model.Project;
import com.example.practica3.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/aplicacionKinan/projects")
public class ProjectAPI {
    private final IProjectService service;
    @Autowired
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectAPI(IProjectService service, ProjectMapper projectMapper) {
        this.service = service;
        this.projectMapper = projectMapper;
    }

    /**
     * Inserts a new project into the system.
     *
     * @param projectDTO ProjectDTO object representing the project to insert, passed in the request body.
     * @return ResponseEntity containing the inserted projectDTO and HTTP status 200 (OK) if successful,
     *         or HTTP status 400 (Bad Request) if insertion fails.
     */
    @PostMapping("/insertProject")
    public ResponseEntity<ProjectDTO> insertProject(@RequestBody ProjectDTO projectDTO) {
        boolean validatedProject = service.validateProject(projectDTO);
        if (validatedProject) {
            Project project = projectMapper.projectDTOToProject(projectDTO);
            boolean response = service.insertNewProject(project);
            if (response) {
                return new ResponseEntity<>(projectDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves all projects in the system.
     *
     * @return ResponseEntity containing a list of all projects and HTTP status 200 (OK).
     */
    @GetMapping("/getAllProjects")
    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(service.getAllProjects(), HttpStatus.OK);
    }

    /**
     * Retrieves a specific project by ID.
     *
     * @param id The ID of the project to retrieve, passed as a path variable.
     * @return ResponseEntity containing the requested project and HTTP status 200 (OK) if found,
     *         or HTTP status 404 (Not Found) if the project doesn't exist.
     */
    @GetMapping("/getProject/{code}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        Project project = service.getProject(id);
        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(project, HttpStatus.OK);
        }
    }

    /**
     * Updates an existing project in the system.
     *
     * @param id The ID of the project to update, passed as a path variable.
     * @param newProjectDTO ProjectDTO with the updated information, passed in the request body.
     * @return ResponseEntity containing the updated project and HTTP status 200 (OK) if successful,
     *         HTTP status 404 (Not Found) if the project doesn't exist,
     *         or HTTP status 400 (Bad Request) if the update fails.
     */
    @PutMapping("/refreshProject/{code}")
    public ResponseEntity<Project> refreshProject(@PathVariable Long id, @RequestBody ProjectDTO newProjectDTO) {
        boolean validatedProject = service.validateProject(newProjectDTO);
        if (validatedProject) {
            Project newProject = projectMapper.projectDTOToProject(newProjectDTO);
            Project oldProject = service.getProject(id);
            if (oldProject == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {
                boolean response = service.refreshProject(oldProject, newProject);
                if (response) {
                    return new ResponseEntity<>(service.getProject(id), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes a project from the system.
     *
     * @param id The ID of the project to delete, passed as a path variable.
     * @return ResponseEntity with HTTP status 200 (OK) if deletion is successful,
     *         or HTTP status 404 (Not Found) if the project doesn't exist.
     */
    @DeleteMapping("/deleteProject/{code}")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable Long id) {
        boolean response = service.deleteProject(id);
        return response ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

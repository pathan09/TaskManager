package com.pathan.taskmanager.controller;

import com.pathan.taskmanager.exception.ResourceNotFoundException;
import com.pathan.taskmanager.model.Project;
import com.pathan.taskmanager.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Pathan on 10-Feb-21.
 */
@RestController
@RequestMapping("api/projects")
@Api(value = "Projects",
description = "Projects Resources")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date - dd/MM/yyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @GetMapping
    @ApiOperation(value = "Get All Project",
            notes = "Projects for logged in user")
    public List<Project> getProjects() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
        if (role.equals("ADMIN")){
            return projectService.findAll();
        }else
            return projectService.findAllByUser(userName);
    }

    @GetMapping("/{projectId}")
    @ApiOperation(value = "Get Project",
            notes = "Project by id")
    public Project getProject(@PathVariable("projectId") Integer projectId){

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
        Project project = null;
        if (role.equals("ADMIN")){
            project = projectService.findById(projectId);
        }else{
            project = projectService.findByIdAndUserName(projectId,userName);
        }
        if (project != null){
            return project;
        }else {
            throw new ResourceNotFoundException("Project Id " + projectId + " not found");
        }
    }

    @PostMapping
    @ApiOperation(value = "Create New Project",
            notes = "Projects for logged in user")
    public Project createProject(@Valid @RequestBody Project project) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        project.setUserName(userName);
        project.setCreatedAt(new Date());
        return projectService.save(project);
    }

    @PutMapping("/{projectId}")
    public Project updateProject(@PathVariable("projectId") Integer projectId,
                                 @Valid @RequestBody Project projectRequest) {
        if(!projectService.existsById(projectId)) {
            throw new ResourceNotFoundException("Project Id " + projectId + " not found");
        }
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (projectService.findByIdAndUserName(projectId, userName) != null){
            return projectService.update(projectId, projectRequest);
        }else {
            throw new ResourceNotFoundException("You can not update Project Id: " + projectId);
        }

    }


    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable("projectId") Integer projectId) {
        if(!projectService.existsById(projectId)) {
            throw new ResourceNotFoundException("Project Id " + projectId + " not found");
        }
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
        Project project = projectService.findById(projectId);
        if (project.getUserName().equals(userName) || role.equals("ADMIN")){
            projectService.delete(project);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}

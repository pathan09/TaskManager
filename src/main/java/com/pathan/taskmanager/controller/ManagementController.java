package com.pathan.taskmanager.controller;

import com.pathan.taskmanager.exception.ResourceNotFoundException;
import com.pathan.taskmanager.model.Project;
import com.pathan.taskmanager.model.Task;
import com.pathan.taskmanager.service.ProjectService;
import com.pathan.taskmanager.service.TaskService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Pathan on 13-Feb-21.
 */
@RestController
@RequestMapping("api/management")
@Api(value = "Management",
        description = "Management used resources")
public class ManagementController {
    @Autowired
    ProjectService projectService;
    @Autowired
    TaskService taskService;

    @GetMapping("/projects/{userName}")
    public List<Project> getAllProjectByUser(@PathVariable("userName") String userName) {
//        String authUser = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
//        System.out.println("========role=="+authUser);
        return projectService.findAllByUser(userName);
    }

    @GetMapping("/tasks/{userName}")
    public List<Task> getAllTaskByUser(@PathVariable("userName") String userName) {

        return taskService.findAllByUser(userName);
    }

}

package com.pathan.taskmanager.controller;

import com.pathan.taskmanager.exception.ResourceNotFoundException;
import com.pathan.taskmanager.model.Project;
import com.pathan.taskmanager.model.Task;
import com.pathan.taskmanager.service.ProjectService;
import com.pathan.taskmanager.service.TaskService;
import com.pathan.taskmanager.specification.TaskSpec;
import com.pathan.taskmanager.utils.PagingHeaders;
import com.pathan.taskmanager.utils.PagingResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Pathan on 11-Feb-21.
 */
@RestController
@RequestMapping("api/projects/{projectId}/tasks")
@Api(value = "Tasks",
        description = "Tasks management Resources")
public class TaskController {
    @Autowired
    ProjectService projectService;
    @Autowired
    TaskService taskService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date - dd/MM/yyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @GetMapping
    public List<Task> getTasks(@PathVariable("projectId") Integer projectId) {
        if(!projectService.existsById(projectId)) {
            throw new ResourceNotFoundException("Project Id " + projectId + " not found");
        }
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
        if (role.equals("ADMIN"))
            return taskService.findAllByProjectId(projectId);
        else
            return taskService.findAllByProjectIdAndUserName(projectId,userName);
    }

    @GetMapping("/{taskId}")
    public Task getTask(@PathVariable("projectId") Integer projectId,
                           @PathVariable("taskId") Integer taskId){
        if(!projectService.existsById(projectId)) {
            throw new ResourceNotFoundException("Project Id " + projectId + " not found");
        }
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
        Task task = null;
        if (role.equals("ADMIN")) task = taskService.findById(taskId);
        else task = taskService.findByIdAndUserName(taskId, userName);

        if (task !=null) return task;
        else throw new ResourceNotFoundException("Project Id " + projectId + " not found");
    }

    @PostMapping
    public Task createTask(@PathVariable("projectId") Integer projectId, @Valid @RequestBody Task task) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!projectService.existsById(projectId)) {
            throw new ResourceNotFoundException("Project Id " + projectId + " not found");
        }
        task.setProject(projectService.findById(projectId));
        task.setUserName(userName);
        task.setCreatedAt(new Date());
        return taskService.save(task);
    }

    @PutMapping("/{taskId}")
    public Task updateTask(@PathVariable("projectId") Integer projectId,
                                 @PathVariable("taskId") Integer taskId,
                                 @Valid @RequestBody Task taskRequest) {
        if(!projectService.existsById(projectId)) {
            throw new ResourceNotFoundException("Project Id " + projectId + " not found");
        }
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Task taskUpdate = taskService.findByIdAndUserName(taskId, userName);
        if (taskUpdate == null || taskUpdate.getStatus().toUpperCase().equals("CLOSED")) {
            throw new ResourceNotFoundException("Task " + taskId + " can not update.");
        }
        taskUpdate.setDescription(taskRequest.getDescription());
        taskUpdate.setStatus(taskRequest.getStatus());
        taskUpdate.setDueDate(taskUpdate.getDueDate());

        return taskService.save(taskUpdate);
    }


    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable("projectId") Integer projectId,
                                           @PathVariable("taskId") Integer taskId) {
        if(!projectService.existsById(projectId)) {
            throw new ResourceNotFoundException("Project Id " + projectId + " not found");
        }
        if(!taskService.existsById(taskId)) {
            throw new ResourceNotFoundException("Task Id " + taskId + " not found");
        }
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Task task = taskService.findByIdAndUserName(taskId, userName);
        if (task.getUserName().equals(userName)){
            taskService.delete(task);
            return ResponseEntity.ok().build();
        }else
            return ResponseEntity.notFound().build();
    }


}

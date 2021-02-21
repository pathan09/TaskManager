package com.pathan.taskmanager.service;

import com.pathan.taskmanager.model.Project;

import java.util.List;

/**
 * Created by Pathan on 10-Feb-21.
 */
public interface ProjectService {
    List<Project> findAll();
    List<Project> findAllByUser(String userName);
    Project findByIdAndUserName(Integer id,String userName);
    Project save(Project project);
    Project update(Integer projectId, Project projectRequest);
    Project findById(Integer id);
    void delete(Project project);
    Boolean existsById(Integer projectId);
}

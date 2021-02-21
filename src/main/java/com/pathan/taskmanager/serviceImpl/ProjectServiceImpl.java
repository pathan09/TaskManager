package com.pathan.taskmanager.serviceImpl;

import com.pathan.taskmanager.model.Project;
import com.pathan.taskmanager.repository.ProjectRepository;
import com.pathan.taskmanager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Pathan on 10-Feb-21.
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> findAllByUser(String userName) {
        return projectRepository.findAllByUserName(userName);
    }

    @Override
    public Project findByIdAndUserName(Integer id, String userName) {
        return projectRepository.findByIdAndUserName(id,userName);
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project update(Integer projectId, Project projectReq) {
        Project projectUpdate = projectRepository.getOne(projectId);
        projectUpdate.setName(projectReq.getName());

        return projectRepository.save(projectUpdate);
    }

    @Override
    public Project findById(Integer id) {
        return projectRepository.getOne(id);
    }

    @Override
    public void delete(Project project) {
        projectRepository.delete(project);
    }

    @Override
    public Boolean existsById(Integer projectId) {
        return projectRepository.existsById(projectId);
    }
}

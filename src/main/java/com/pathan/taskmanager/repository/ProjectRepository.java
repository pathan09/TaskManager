package com.pathan.taskmanager.repository;

import com.pathan.taskmanager.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Pathan on 10-Feb-21.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findAllByUserName(String userName);
    Project findByIdAndUserName(Integer id,String userName);
}

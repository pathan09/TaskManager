package com.pathan.taskmanager.repository;

import com.pathan.taskmanager.model.Project;
import com.pathan.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;


/**
 * Created by Pathan on 10-Feb-21.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {

    List<Task> findAllByUserName(String userName);
    List<Task> findAllByProjectIdAndUserName(Integer projectId, String userName);
    List<Task> findAllByProjectId(Integer projectId);
    Task findByIdAndUserName(Integer id, String userName);
}

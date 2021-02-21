package com.pathan.taskmanager.service;

import com.pathan.taskmanager.model.Project;
import com.pathan.taskmanager.model.Task;
import com.pathan.taskmanager.utils.PagingResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;

import java.util.List;

/**
 * Created by Pathan on 10-Feb-21.
 */
public interface TaskService {
    List<Task> findAllByUser(String userName);
    List<Task> findAllByProjectIdAndUserName(Integer projectId, String userName);
    List<Task> findAllByProjectId(Integer projectId);
    Task save(Task task);
    Task findByIdAndUserName(Integer id, String userName);
    Task findById(Integer id);
    void delete(Task task);
    Boolean existsById(Integer taskId);
    PagingResponse get(Specification<Task> spec, HttpHeaders headers, Sort sort);
    PagingResponse get(Specification<Task> spec, Pageable pageable);
    List<Task> get(Specification<Task> spec, Sort sort);
}

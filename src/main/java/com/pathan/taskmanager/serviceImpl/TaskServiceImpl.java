package com.pathan.taskmanager.serviceImpl;

import com.pathan.taskmanager.model.Project;
import com.pathan.taskmanager.model.Task;
import com.pathan.taskmanager.repository.TaskRepository;
import com.pathan.taskmanager.service.TaskService;
import com.pathan.taskmanager.utils.PagingHeaders;
import com.pathan.taskmanager.utils.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Pathan on 10-Feb-21.
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;


    @Override
    public List<Task> findAllByUser(String userName) {
        return taskRepository.findAllByUserName(userName);
    }

    @Override
    public List<Task> findAllByProjectIdAndUserName(Integer projectId, String userName) {
        return taskRepository.findAllByProjectIdAndUserName(projectId, userName);
    }

    @Override
    public List<Task> findAllByProjectId(Integer projectId) {
        return taskRepository.findAllByProjectId(projectId);
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task findByIdAndUserName(Integer id, String userName) {
        return taskRepository.findByIdAndUserName(id, userName);
    }

    @Override
    public Task findById(Integer id) {
        return taskRepository.getOne(id);
    }

    @Override
    public void delete(Task task) {
        taskRepository.delete(task);
    }

    @Override
    public Boolean existsById(Integer taskId) {
        return taskRepository.existsById(taskId);
    }

    /**
     * get element using Criteria.
     *
     * @param spec    *
     * @param headers pagination data
     * @param sort    sort criteria
     * @return retrieve elements with pagination
     */
    public PagingResponse get(Specification<Task> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            List<Task> entities = get(spec, sort);
            return new PagingResponse((long) entities.size(), 0L, 0L, 0L, 0L, entities);
        }
    }

    private boolean isRequestPaged(HttpHeaders headers) {
        return headers.containsKey(PagingHeaders.PAGE_NUMBER.getName()) && headers.containsKey(PagingHeaders.PAGE_SIZE.getName());
    }

    private Pageable buildPageRequest(HttpHeaders headers, Sort sort) {
        int page = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_NUMBER.getName())).get(0));
        int size = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_SIZE.getName())).get(0));
        return PageRequest.of(page, size, sort);
    }

    /**
     * get elements using Criteria.
     *
     * @param spec     *
     * @param pageable pagination data
     * @return retrieve elements with pagination
     */
    public PagingResponse get(Specification<Task> spec, Pageable pageable) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<Task> page = taskRepository.findAll(spec, pageable);
        List<Task> content = page.getContent()
                .stream()
                .filter(task -> task.getUserName().equals(userName))
                .collect(Collectors.toList());
        return new PagingResponse(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), content);
    }

    /**
     * get elements using Criteria.
     *
     * @param spec *
     * @return elements
     */
    public List<Task> get(Specification<Task> spec, Sort sort) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskRepository.findAll(spec, sort)
                .stream()
                .filter(task -> task.getUserName().equals(userName))
                .collect(Collectors.toList());

//        taskRepository.findAll(spec, sort).removeIf(task -> )
    }
}

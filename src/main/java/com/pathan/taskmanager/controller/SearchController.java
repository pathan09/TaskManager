package com.pathan.taskmanager.controller;

import com.pathan.taskmanager.model.Task;
import com.pathan.taskmanager.service.TaskService;
import com.pathan.taskmanager.specification.TaskSpec;
import com.pathan.taskmanager.utils.PagingHeaders;
import com.pathan.taskmanager.utils.PagingResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Pathan on 14-Feb-21.
 */
//@Api(value = "SearchController", description = "REST APIs related to searching tasks")
@RestController
@RequestMapping("api/search")
@Api(value = "Search",
        description = "Search criteria")
public class SearchController {

    @Autowired
    TaskService taskService;


    @GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Task>> get(TaskSpec spec, Sort sort, @RequestHeader HttpHeaders headers) {
        final PagingResponse response = taskService.get(spec, headers, sort);
        return new ResponseEntity<>((List<Task>) response.getElements(), returnHttpHeaders(response), HttpStatus.OK);
    }

    public HttpHeaders returnHttpHeaders(PagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }
}

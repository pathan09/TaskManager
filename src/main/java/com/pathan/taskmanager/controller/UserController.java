package com.pathan.taskmanager.controller;

import com.pathan.taskmanager.model.User;
import com.pathan.taskmanager.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    @PreAuthorize("hasAuthority(\"ADMIN\")")
    @ApiOperation(value = "User List",
    notes = "Only used by ADMIN ROLE")
    public List<User> getAllUser(Authentication authentication) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        ((((UserDetails) authentication.getPrincipal()).getAuthorities()).toArray()[0]).toString().equals("ADMIN")
        return userService.findAll();
    }

    /*@GetMapping
    public User getUser(){
        String authUser = SecurityContextHolder.getContext().getAuthentication().getName();
            return userService.findUserByUserName(authUser);
    }*/

    @PostMapping
    public User createUser(@RequestBody User user) {

        return userService.saveUser(user);
    }


}

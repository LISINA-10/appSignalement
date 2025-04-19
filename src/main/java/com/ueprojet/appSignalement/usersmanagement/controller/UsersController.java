package com.ueprojet.appSignalement.usersmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ueprojet.appSignalement.usersmanagement.model.Users;
import com.ueprojet.appSignalement.usersmanagement.service.UsersService;



@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @GetMapping("/getUsers")
    public List<Users> getUsers(){
        return usersService.getAllUsers();
    }

    @DeleteMapping("/deleteUsers")
    public void deleteUsers(){
        usersService.deleteAllUsers();
    }
    
}

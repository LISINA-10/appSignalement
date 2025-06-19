package com.ueprojet.appSignalement.usersmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ueprojet.appSignalement.usersmanagement.enumeration.Role;
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

    @GetMapping("/getUserById")
    public Users getUserById(@RequestParam Long id){
        return usersService.getUserById(id);
    }

    @PutMapping("/changePassword")
    public void changePassword(@RequestParam Long userId, 
                                @RequestParam String oldPassword, 
                                @RequestParam String newPassword) {
        usersService.changePassword(userId, oldPassword, newPassword);
    }

    @PutMapping("/changeEmail")
    public void changeEmail(@RequestParam Long userId, 
                                @RequestParam String oldEmail, 
                                @RequestParam String newEmail) {
        usersService.changePassword(userId, oldEmail, newEmail);
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
    }

    @GetMapping("/role/{role}")
    public List<Users> getUsersByRole(@PathVariable Role role) {
        return usersService.getUsersByRole(role);
    }



    
}

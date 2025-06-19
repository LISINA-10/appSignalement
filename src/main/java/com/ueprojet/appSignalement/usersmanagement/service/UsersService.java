package com.ueprojet.appSignalement.usersmanagement.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ueprojet.appSignalement.usersmanagement.enumeration.Role;
import com.ueprojet.appSignalement.usersmanagement.model.Agent;
import com.ueprojet.appSignalement.usersmanagement.model.Citizen;
import com.ueprojet.appSignalement.usersmanagement.model.Users;
import com.ueprojet.appSignalement.usersmanagement.repositorie.UsersRepository;

@Service
public class UsersService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users registerUser(Users user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username is already taken: " + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDateDeCreation(LocalDate.now());
    
        return userRepository.save(user);
    }

    public Agent registerAgent(Agent agent) {
    if (userRepository.existsByUsername(agent.getUsername())) {
        throw new IllegalArgumentException("Username is already taken: " + agent.getUsername());
    }
    agent.setPassword(passwordEncoder.encode(agent.getPassword()));
    
    return userRepository.save(agent);
}

public Citizen registerCitizen(Citizen citizen) {
    if (userRepository.existsByUsername(citizen.getUsername())) {
        throw new IllegalArgumentException("Username is already taken: " + citizen.getUsername());
    }
    citizen.setPassword(passwordEncoder.encode(citizen.getPassword()));
    if(citizen.getRole() == null){
        citizen.setRole(Role.CITIZEN);
    }
    return userRepository.save(citizen);
}


    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

    public Users getUserById(Long id){
      return  userRepository.findById(id).orElseThrow();
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        // Vérifier le mot de passe actuel
        if (!user.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("Le mot de passe actuel est incorrect");
        }

        // Mettre à jour le mot de passe
        user.setPassword(newPassword);
        userRepository.save(user);
    }

     public void changeEmail(Long userId, String oldEmail, String newEmail) {
        Citizen user =( Citizen) userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        // Vérifier le mot de passe actuel
        if (!user.getPassword().equals(oldEmail)) {
            throw new IllegalArgumentException("Le mot de passe actuel est incorrect");
        }

        // Mettre à jour le mot de passe
        user.setEmail(newEmail);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public List<Users> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }





    
}

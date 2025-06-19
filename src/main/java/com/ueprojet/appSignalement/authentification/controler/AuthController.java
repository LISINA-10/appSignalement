package com.ueprojet.appSignalement.authentification.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ueprojet.appSignalement.authentification.AuthResponse;
import com.ueprojet.appSignalement.authentification.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.ueprojet.appSignalement.usersmanagement.enumeration.Role;
import com.ueprojet.appSignalement.usersmanagement.model.Agent;
import com.ueprojet.appSignalement.usersmanagement.model.Citizen;
import com.ueprojet.appSignalement.usersmanagement.model.Users;
import com.ueprojet.appSignalement.usersmanagement.repositorie.UsersRepository;
import com.ueprojet.appSignalement.usersmanagement.service.UsersService;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private UsersService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepository usersRepository;


    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user) {
        try {
            Users savedUser = userService.registerUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PostMapping("/register/citizen")
    public ResponseEntity<?> registerCitizen(@RequestBody Citizen user) {
        try {
            Users savedUser = userService.registerUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PostMapping("/register/agent")
    public ResponseEntity<?> registerAgent(@RequestBody Agent user) {
        try {
            Users savedUser = userService.registerUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
       try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
          
           // Récupérer l'utilisateur authentifié
            Users authenticatedUser = usersRepository.findByUsername(authentication.getName());
           
            String token;
            if (authenticatedUser instanceof Agent) {
                
                Agent agent = (Agent) authenticatedUser;
                token = jwtUtil.createToken(agent.getUsername(), agent.getId(), agent.getArrondissementId());
            } else if (authenticatedUser instanceof Citizen) {
                Citizen citizen = (Citizen) authenticatedUser;
                token = jwtUtil.createToken(citizen.getUsername(), citizen.getId(), citizen.getEmail(), citizen.getArrondissementId());
            
            } else if (authenticatedUser.getRole() == Role.ADMIN) {
                    token = jwtUtil.generateToken(authenticatedUser.getUsername(), authenticatedUser.getId());
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User type not recognized");
            }

            return ResponseEntity.ok(new AuthResponse(token));

           
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}

package com.projetospring.controllers.api;

import javax.validation.Valid;

import com.projetospring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projetospring.jwt.JwtTokenUtil;
import com.projetospring.models.AuthRequest;
import com.projetospring.models.AuthResponse;
import com.projetospring.models.User;

@RestController
public class ApiAuthController {

    @Autowired AuthenticationManager authManager;
    @Autowired JwtTokenUtil jwtUtil;
    @Autowired
    UserRepository userRepository;
    
    @CrossOrigin
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword())
            );
             
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getUsername(), accessToken);
             
            return ResponseEntity.ok().body(response);
             
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    } 
    
    @CrossOrigin
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody User user) {
    	try {
    		// criar usuario no banco de dados
    		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    		String password = passwordEncoder.encode(user.getPassword());
    		
    		user.setPassword(password);
    		
    		userRepository.save(user);
    		return new ResponseEntity<User>(user, HttpStatus.CREATED);
    		
    	} catch(RuntimeException ex) {
    		
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    	}
    }
	
}


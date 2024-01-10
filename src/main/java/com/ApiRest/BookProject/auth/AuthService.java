
package com.ApiRest.BookProject.auth;

import com.ApiRest.BookProject.jwt.JwtService;
import com.ApiRest.BookProject.persistence.Role;
import com.ApiRest.BookProject.persistence.User;
import com.ApiRest.BookProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    
    
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    
    
    public AuthResponse login(LoginRequest request){
        
        
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        UserDetails user = userRepository.findUserByUsername(request.getUsername()).orElseThrow();
        
        String token = jwtService.getToken(user);
        
        return AuthResponse.builder()
                .token(token)
                .build();
    }
    
    

        public AuthResponse register(RegisterRequest request){
        
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .country(request.getCountry())
                .role(Role.USER)
                .build();
        
        userRepository.save(user);
        
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
        
    }
    
    
}


package com.ApiRest.BookProject.config;

import com.ApiRest.BookProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ApplicationConfig {
    
    
    private final UserRepository userRepository;
    
    
    @Bean
    public AuthenticationManager  authenticationManager(AuthenticationConfiguration config) throws Exception{
        
        return config.getAuthenticationManager();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider(){
        
        DaoAuthenticationProvider authentication = new DaoAuthenticationProvider();
        
        authentication.setPasswordEncoder(passwordEncoder());
        authentication.setUserDetailsService(userDetailsService());
        
        return authentication;
        
        
    }
    
    
    @Bean
    public UserDetailsService userDetailsService(){
         
        return username -> userRepository.findUserByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Use not found"));
        
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        
        return new BCryptPasswordEncoder();
    }
    
    
    
}

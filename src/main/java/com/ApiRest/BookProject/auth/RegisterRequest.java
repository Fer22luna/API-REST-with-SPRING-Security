
package com.ApiRest.BookProject.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    
    String username;
    String password;
    String lastname;
    String firstname;
    String country;
    
}

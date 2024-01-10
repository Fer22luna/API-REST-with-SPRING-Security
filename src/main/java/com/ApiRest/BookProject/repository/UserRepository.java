
package com.ApiRest.BookProject.repository;

import com.ApiRest.BookProject.persistence.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
    
    Optional<User> findUserByUsername(String username); 
}

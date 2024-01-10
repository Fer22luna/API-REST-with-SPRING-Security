
package com.ApiRest.BookProject.repository;

import com.ApiRest.BookProject.persistence.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
    
    
}

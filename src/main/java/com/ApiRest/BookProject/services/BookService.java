
package com.ApiRest.BookProject.services;

import com.ApiRest.BookProject.persistence.Book;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface BookService {
    
    
    List<Book> findAllBooks();

    Book findById(Integer id);

    Book createNewBook(Book book);

    Book updateBook(Book book, Integer id);

    void deleteBook(Integer id);
    
}

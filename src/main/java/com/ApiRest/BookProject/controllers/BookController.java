
package com.ApiRest.BookProject.controllers;

import com.ApiRest.BookProject.persistence.Book;
import com.ApiRest.BookProject.services.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/books")
@RequiredArgsConstructor
public class BookController {
    
        @Autowired
        private BookService bookService;
    
    
        @GetMapping( value = "/")
        public  ResponseEntity<List<Book>> listAllBooks(){
            return ResponseEntity.ok(bookService.findAllBooks());
        }
        
        @GetMapping(value = "/{id}")
        public ResponseEntity<Book> listBookById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(bookService.findById(id));     
    }
    

        @PostMapping(value = "/create")
        public ResponseEntity<Book> createBook(@RequestBody Book book){
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createNewBook(book));
        }
        
        
        @PutMapping(value = "/update/{id}")
        public ResponseEntity<Book> updateBook(@PathVariable Integer id,@RequestBody Book book){
            
            return ResponseEntity.ok(bookService.updateBook(book, id));
        }
        
        
        @DeleteMapping(value = "/delete/{id}")
        public ResponseEntity<Object> deleteBookById(@PathVariable("id") Integer id){
            
              bookService.deleteBook(id);
            return ResponseEntity.status(HttpStatus.OK).body("Book was deleted successfully.  ");
               
        }
        
    
}

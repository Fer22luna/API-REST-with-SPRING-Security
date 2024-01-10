
package com.ApiRest.BookProject.services;

import com.ApiRest.BookProject.controllers.BookController;
import com.ApiRest.BookProject.persistence.Book;
import com.ApiRest.BookProject.repository.BookRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    
    private final BookRepository bookRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    

    @Override
    public List<Book> findAllBooks() {

        List<Book> books = bookRepository.findAll();
        
                books
                .forEach(p -> p.add(linkTo(methodOn(BookController.class)
                        .listBookById(p.getId()))
                        .withSelfRel()));
        logger.info("List all Books {} ", books);
        return books;
        
            
    }

    @Override
    public Book findById(Integer id) {

           Book book =  bookRepository.findById(id)
                   .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + id));

                   // Hacemos esto para manejar el Optional que retorna el metodo .findById;
           
           logger.info("Find the book witd id : {}", book);
           return book;
    }

    @Override
    public Book createNewBook(Book book) {

       return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book, Integer id) {


        if (!bookRepository.existsById(id)) {
            logger.error("The Book with id {} ", id + " doesn't exist");

        }

        Book saved = bookRepository.save(
                new Book(
                        id,
                        book.getAuthor(),
                        book.getPrice(),
                        book.getTitle()
                ));
        logger.info("Book Updated With Success");
        return saved;
    }

    @Override
    public void deleteBook(Integer id) {

//        Optional<Book> book = bookRepository.findById(id);
//        book.ifPresent(bookRepository::delete);
        
        bookRepository.deleteById(id);
        logger.info("Book with Id {}", id + " deleted with success");
        
    }
    
    
    
}


package com.ApiRest.BookProject.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;


@Entity
@Table(name = "tb_books")
@NoArgsConstructor
@JsonPropertyOrder({"id", "author", "price", "title"})
public class Book extends RepresentationModel<Book> {
    
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;
    
//    @NotBlank
    @Column(nullable = false,
            length = 512)
    private String author;
    
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false,
            length = 200
    )
    private String title;

    public Book(Integer id, String author, Double price, String title) {
        this.id = id;
        this.author = author;
        this.price = price;
        this.title = title;
    }

        
    public Book(String author, Double price, String title) {
        this.author = author;
        this.price = price;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + id +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                "} " + super.toString();
    }
    
    
    
}

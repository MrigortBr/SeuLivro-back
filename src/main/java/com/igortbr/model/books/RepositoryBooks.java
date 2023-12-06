package com.igortbr.model.books;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.igortbr.entitys.books.Books;


public interface RepositoryBooks extends JpaRepository<Books, UUID>{
    List<Books> findByTitleContainingAllIgnoreCaseAndEnabledTrue(String partialTitle);
    List<Books> findByGenreContainingAllIgnoreCaseAndEnabledTrue(String partialGenre);
    @Query("SELECT b FROM book b WHERE b.enabled = TRUE ORDER BY RANDOM() LIMIT 3")
    List<Books> findRandomBooks();
    
}

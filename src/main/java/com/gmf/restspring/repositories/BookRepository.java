package com.gmf.restspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmf.restspring.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}

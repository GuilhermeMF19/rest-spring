package com.gmf.restspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmf.restspring.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}

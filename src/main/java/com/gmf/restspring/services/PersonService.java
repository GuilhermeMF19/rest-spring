package com.gmf.restspring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmf.restspring.exceptions.ResourceNotFoundException;
import com.gmf.restspring.model.Person;
import com.gmf.restspring.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository repository;
	
	public List<Person> findAll() {
		return repository.findAll();
	}

	public Person findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
	}
	
	public Person create(Person person) {
		return repository.save(person);
	}
	
	public Person update(Person person) {
		Person entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		updateData(entity, person);
		return repository.save(entity);
	}
	
	private void updateData(Person entity, Person obj) {
		entity.setFirstName(obj.getFirstName());
		entity.setLastName(obj.getLastName());
		entity.setAddress(obj.getAddress());
		entity.setGender(obj.getGender());
	}
	
	public void delete(Long id) {
		Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		repository.delete(entity);
	}
}

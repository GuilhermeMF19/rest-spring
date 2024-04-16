package com.gmf.restspring.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gmf.restspring.data.vo.v1.BookVO;
import com.gmf.restspring.exceptions.RequiredObjectIsNullException;
import com.gmf.restspring.model.Book;
import com.gmf.restspring.repositories.BookRepository;
import com.gmf.restspring.services.BookService;
import com.gmf.restspring.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	MockBook input;
	
	@InjectMocks
	private BookService service;
	
	@Mock
	BookRepository repository;
	
	@BeforeEach
	void setUp() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAll() {
		List<Book> list = input.mockEntityList();
		
		when(repository.findAll()).thenReturn(list);
		
		var people = service.findAll();
		
		assertNotNull(people);
		assertEquals(14, people.size());
		
		var personOne = people.get(1);
		assertNotNull(personOne);
		assertNotNull(personOne.getKey());
		assertNotNull(personOne.getLinks());
		
		assertTrue(personOne.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", personOne.getAuthor());
		assertEquals("First Name Test1", personOne.getLaunchDate());
		assertEquals("Last Name Test1", personOne.getPrice());
		assertEquals("Female", personOne.getTitle());

		var personFour = people.get(4);
		assertNotNull(personFour);
		assertNotNull(personFour.getKey());
		assertNotNull(personFour.getLinks());
		
		assertTrue(personFour.toString().contains("links: [</api/book/v1/4>;rel=\"self\"]"));
		assertEquals("Addres Test4", personFour.getAuthor());
		assertEquals("First Name Test4", personFour.getLaunchDate());
		assertEquals("Last Name Test4", personFour.getPrice());
		assertEquals("Male", personFour.getTitle());
		
		var personSeven = people.get(7);
		assertNotNull(personSeven);
		assertNotNull(personSeven.getKey());
		assertNotNull(personSeven.getLinks());
		
		assertTrue(personSeven.toString().contains("links: [</api/book/v1/7>;rel=\"self\"]"));
		assertEquals("Addres Test7", personSeven.getAuthor());
		assertEquals("First Name Test7", personSeven.getLaunchDate());
		assertEquals("Last Name Test7", personSeven.getPrice());
		assertEquals("Female", personSeven.getTitle());
	}

	@Test
	void testFindById() {
		Book entity = input.mockEntity(1); 
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getAuthor());
		assertEquals("First Name Test1", result.getLaunchDate());
		assertEquals("Last Name Test1", result.getPrice());
		assertEquals("Female", result.getTitle());
	}

	@Test
	void testCreate() {
		Book entity = input.mockEntity(1); 
		Book persisted = entity;
		persisted.setId(1L);
		
		BookVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.create(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getAuthor());
		assertEquals("First Name Test1", result.getLaunchDate());
		assertEquals("Last Name Test1", result.getPrice());
		assertEquals("Female", result.getTitle());
	}

	@Test
	void testCreateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate() {
		Book entity = input.mockEntity(1); 
		entity.setId(1L);
		
		Book persisted = entity;
		persisted.setId(1L);
		
		BookVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.update(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getAuthor());
		assertEquals("First Name Test1", result.getLaunchDate());
		assertEquals("Last Name Test1", result.getPrice());
		assertEquals("Female", result.getTitle());
	}
	
	@Test
	void testUpdateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testDelete() {
		Book entity = input.mockEntity(1); 
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}

}

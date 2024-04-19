package com.gmf.restspring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import com.gmf.restspring.exceptions.RequiredObjectIsNullException;
import com.gmf.restspring.exceptions.ResourceNotFoundException;
import com.gmf.restspring.mapper.DozerMapper;
import com.gmf.restspring.model.Book;
import com.gmf.restspring.controllers.BookController;
import com.gmf.restspring.data.vo.v1.BookVO;
import com.gmf.restspring.repositories.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository repository;
	
	@Autowired
	private PagedResourcesAssembler<BookVO> assembler;
	
	public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {
		var bookPage = repository.findAll(pageable);
		var bookVoPage = bookPage.map(b -> DozerMapper.parseObject(b, BookVO.class));
		
		bookVoPage.map(p -> p.add(
				linkTo(methodOn(BookController.class)
						.findById(p.getKey())).withSelfRel()));
		
		Link link = linkTo(methodOn(BookController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(bookVoPage, link);
	}

	public BookVO findById(Long id) {
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public BookVO create(BookVO book) {
		if (book == null) throw new RequiredObjectIsNullException();
		
		var entity = DozerMapper.parseObject(book, Book.class);
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public BookVO update(BookVO book) {
		if (book == null) throw new RequiredObjectIsNullException();
		
		var entity = repository.findById(book.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		repository.delete(entity);
	}
}

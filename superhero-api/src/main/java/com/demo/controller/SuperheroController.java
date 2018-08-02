/*
 * Copyright 2018 the original author.
 *
 * Superhero API is build for interview @Payworks.
 *
 */
package com.payworks.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.payworks.exceptionhandling.EntityNotFoundException;
import com.payworks.model.Superhero;
import com.payworks.service.SuperheroService;
import com.payworks.specifications.SuperheroNameLikeIgnoreCaseSpecification;
import com.payworks.specifications.SuperheroPseudonymEqualIgnoreCaseSpecification;
import com.payworks.specifications.SuperheroPublisherEqualIgnoreCaseSpecification;

/**
 * Superheros API Controller.
 * 
 * @author Sandeep Kumar
 */

@RestController
@RequestMapping("superheros")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class SuperheroController {

	@Autowired
	private SuperheroService service;

	@GetMapping
	public Iterable<Superhero> find() {
		return service.findAllSuperheros();
	}

	@GetMapping(params = { "publisher" })
	public Iterable<Superhero> find(SuperheroPublisherEqualIgnoreCaseSpecification spec) {
		return service.findAllSuperheros(spec);
	}

	@GetMapping(params = { "pseudonym" })
	public Iterable<Superhero> find(SuperheroPseudonymEqualIgnoreCaseSpecification spec) {
		return service.findAllSuperheros(spec);
	}

	@GetMapping(params = { "name" })
	public Iterable<Superhero> find(SuperheroNameLikeIgnoreCaseSpecification spec) {
		return service.findAllSuperheros(spec);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Superhero add(@RequestBody @Valid Superhero superhero) {
		return service.saveSuperhero(superhero);
	}

	@GetMapping(value = "/{id}")
	public Superhero findById(@PathVariable("id") Long id) throws EntityNotFoundException {
		return service.findSuperheroById(id);
	}

	@GetMapping(value = "/byPseudonym/{pseudonym}")
	public Superhero findByPseudonym(@PathVariable("pseudonym") String pseudonym) throws EntityNotFoundException {
		return service.findSuperheroByPseudonym(pseudonym);
	}

}

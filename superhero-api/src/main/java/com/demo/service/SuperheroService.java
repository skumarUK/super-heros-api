/*
 * Copyright 2018 the original author.
 *
 * Superhero API is build for interview @Payworks.
 *
 */
package com.payworks.service;

import org.springframework.data.jpa.domain.Specification;

import com.payworks.exceptionhandling.EntityNotFoundException;
import com.payworks.model.Superhero;

/**
 * Superhero API Service for DAO layer interactions.
 * 
 * @author Sandeep Kumar
 */
public interface SuperheroService {

	/**
	 * Returns all instances of the type.
	 * 
	 * @return all entities
	 */

	Iterable<Superhero> findAllSuperheros();

	/**
	 * Returns all entities matching the given {@link Specification<Superhero>}.
	 *
	 * @param spec
	 *            can be {@literal null}.
	 * @return never {@literal null}.
	 */

	Iterable<Superhero> findAllSuperheros(Specification<Superhero> spec);

	/**
	 * Saves a given entity.
	 * 
	 * @param entity
	 *            must not be {@literal null}.
	 * 
	 * @return the saved entity will never be {@literal null}.
	 * 
	 */
	Superhero saveSuperhero(Superhero superhero);

	/**
	 * Retrieves an entity by its id.
	 * 
	 * @param id
	 *            must not be {@literal null}.
	 * @return the entity with the given id or
	 *         {@literal throws EntityNotFoundException} if none found
	 * @throws IllegalArgumentException
	 *             if {@code id} is {@literal null}.
	 * @throws EntityNotFoundException
	 *             if {@literal Optional#empty()} if none found.
	 */
	Superhero findSuperheroById(Long id) throws EntityNotFoundException, IllegalArgumentException;

	/**
	 * Retrieves an entity by its pseudonym.
	 * 
	 * @param pseudonym
	 * @return the entity with the given pseudonym or
	 *         {@literal throws EntityNotFoundException} if none found
	 * @throws EntityNotFoundException
	 *             if none found.
	 * @throws IllegalArgumentException
	 *             if {@code pseudonym} is {@literal null}.
	 */
	Superhero findSuperheroByPseudonym(String pseudonym) throws EntityNotFoundException, IllegalArgumentException;

}

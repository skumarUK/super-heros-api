/*
 * Copyright 2018 the original author.
 *
 * Superhero API is build for interview @Payworks.
 *
 */
package com.payworks.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.payworks.model.Superhero;

/**
 * Repository Interface for Superhero API Database operations.
 * 
 * @author Sandeep Kumar
 */
@Repository
public interface SuperheroRepository extends CrudRepository<Superhero, Long>, JpaSpecificationExecutor<Superhero> {

	/**
	 * Retrieves an entity by its pseudonym.
	 * 
	 * @param pseudonym
	 *            must not be {@literal null}.
	 * @return the entity with the given pseudonym or {@literal null} if none
	 *         found
	 * @throws IllegalArgumentException
	 *             if {@code pseudonym} is {@literal null}.
	 */
	Superhero findByPseudonym(String pseudonym);
}

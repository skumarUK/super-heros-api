/*
 * Copyright 2018 the original author.
 *
 * Superhero API is build for interview @Payworks.
 *
 */
package com.payworks.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.payworks.exceptionhandling.EntityNotFoundException;
import com.payworks.model.Superhero;
import com.payworks.repository.SuperheroRepository;
import com.payworks.service.SuperheroService;

/**
 * 
 * @author Sandeep.Kumar
 *
 */
@Service
public class SuperheroServiceImpl implements SuperheroService {

	@Autowired
	private SuperheroRepository superheroRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.payworks.service.SuperheroService#findAllSuperheros()
	 */
	@Override
	public Iterable<Superhero> findAllSuperheros() {
		return superheroRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.payworks.service.SuperheroService#findAllSuperheros(org.
	 * springframework.data.jpa.domain.Specification)
	 */
	@Override
	public Iterable<Superhero> findAllSuperheros(Specification<Superhero> spec) {
		return superheroRepository.findAll(spec);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.payworks.service.SuperheroService#saveSuperhero(com.payworks.model.
	 * Superhero)
	 */

	@Override
	public Superhero saveSuperhero(Superhero superhero) {
		return superheroRepository.save(superhero);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.payworks.service.SuperheroService#findSuperheroById(java.lang.Long)
	 */
	@Override
	public Superhero findSuperheroById(Long id) throws EntityNotFoundException, IllegalArgumentException {

		return superheroRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Superhero.class, "id", id.toString()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.payworks.service.SuperheroService#getSuperheroByPseudonym(java.lang.
	 * String)
	 */
	@Override
	public Superhero findSuperheroByPseudonym(String pseudonym)
			throws EntityNotFoundException, IllegalArgumentException {

		Superhero superhero = superheroRepository.findByPseudonym(pseudonym);
		if (superhero == null) {
			throw new EntityNotFoundException(Superhero.class, "pseudonym", pseudonym);
		}
		return superhero;
	}

}

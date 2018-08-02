/*
 * Copyright 2018 the original author.
 *
 * Superhero API is build for interview @Payworks.
 *
 */
package com.payworks.specifications;

/**
 * The class provides Superhero filter specification of Pseudonym.
 * 
 * @author Sandeep Kumar
 */

import org.springframework.data.jpa.domain.Specification;

import com.payworks.model.Superhero;

import net.kaczmarzyk.spring.data.jpa.domain.EqualIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@Spec(path = "pseudonym", spec = EqualIgnoreCase.class)
public interface SuperheroPseudonymEqualIgnoreCaseSpecification extends Specification<Superhero> {

}

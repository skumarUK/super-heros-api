/*
 * Copyright 2018 the original author.
 *
 * Superhero API is build for interview @Payworks.
 *
 */

package com.payworks.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Index Controller.
 * 
 * @author Sandeep Kumar
 */

@RestController
@RequestMapping("/")
public class IndexController {

	@GetMapping
	public ResourceSupport index() {
		ResourceSupport index = new ResourceSupport();
		index.add(linkTo(SuperheroController.class).withRel("superheros"));
		return index;
	}

}
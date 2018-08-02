/*
 * Copyright 2018 the original author.
 *
 * Superhero API is build for interview @Payworks.
 *
 */
package com.payworks.utils;

import java.util.List;

import com.payworks.model.Ally;
import com.payworks.model.Skill;
import com.payworks.model.Superhero;

/**
 * 
 * @author Sandeep.Kumar
 *
 */
public class TestUtils {

	public static Superhero mockBatMan(String name, String pseudonym, String publisher) {
		Superhero superhero = new Superhero(name, pseudonym, publisher);
		List<Skill> skills = superhero.getSkills();
		skills.add(new Skill("martial arts"));
		skills.add(new Skill("gymnastics"));
		skills.add(new Skill("disguise"));
		superhero.getAllies().add(new Ally("Robin"));
		return superhero;
	}

	public static Superhero mockSuperMan(String name, String pseudonym, String publisher) {
		Superhero superhero = new Superhero(name, pseudonym, publisher);
		List<Skill> skills = superhero.getSkills();
		skills.add(new Skill("x-ray vision"));
		skills.add(new Skill("heat vision"));
		skills.add(new Skill("super-speed"));
		superhero.getAllies().add(new Ally("Robin"));
		return superhero;
	}

}

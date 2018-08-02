/*
 * Copyright 2018 the original author.
 *
 * Superhero API is build for interview @Payworks.
 *
 */
package com.payworks.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "pseudonym" }, name = "UQ_SUPERHERO_PSEUDONYM"))
public class Superhero implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(nullable = false)
	@NotNull(message = "{error.name.required}")
	@Size(max = 30, message = "{error.name.length}")
	private String name;

	@Column(nullable = false)
	@NotNull(message = "{error.pseudonym.required}")
	@Size(max = 30, message = "{error.pseudonym.length}")
	private String pseudonym;

	@Column(nullable = false)
	@NotNull(message = "{error.publisher.required}")
	@Size(max = 30, message = "{error.publisher.length}")
	private String publisher;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "superhero_id")
	private final List<Skill> skills = new ArrayList<Skill>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "superhero_id")
	private final List<Ally> allies = new ArrayList<Ally>();

	public Superhero(String name, String pseudonym, String publisher) {
		this.name = name;
		this.pseudonym = pseudonym;
		this.publisher = publisher;
	}

}

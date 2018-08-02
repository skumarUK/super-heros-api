/*
 * Copyright 2018 the original author.
 *
 * Superhero API is build for interview @Payworks.
 *
 */
package com.payworks.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.payworks.exceptionhandling.EntityNotFoundException;
import com.payworks.model.Superhero;
import com.payworks.repository.SuperheroRepository;
import com.payworks.service.impl.SuperheroServiceImpl;
import com.payworks.utils.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
public class SuperheroServiceTests {

	@Autowired
	SuperheroService service;

	@MockBean
	SuperheroRepository superheroRepository;

	@TestConfiguration
	static class SuperheroServiceImplTestContextConfiguration {

		@Bean
		public SuperheroService employeeService() {
			return new SuperheroServiceImpl();
		}
	}

	@Test
	public void whenNoneAvailable_thenReturnEmptySuperheros() {

		Mockito.when(superheroRepository.findAll()).thenReturn(EMPTY_ITERABLE_LIST);
		Iterable<Superhero> superheros = service.findAllSuperheros();
		assertThat(superheros).isEmpty();
	}

	@Test
	public void whenFinds_thenReturnSuperheros() {
		Mockito.when(superheroRepository.findAll()).thenReturn(superheros_mock);
		Iterable<Superhero> superheros = service.findAllSuperheros();
		assertThat(superheros).isNotEmpty();
		assertThat(superheros).size().isEqualTo(2);
	}

	@Test
	public void whenFilterByPseudonym_andFinds_thenReturnSuperheros() {
		List<Superhero> filtered_superheros_mock = superheros_mock.stream()
				.filter(obj -> obj.getPseudonym().equals(SUPERHERO_PSEUDONYM)).collect(Collectors.toList());
		Specification<Superhero> mockSpec = mock(Specification.class, withSettings().serializable());
		mockSpec.equals(SUPERHERO_PSEUDONYM);
		Mockito.when(superheroRepository.findAll(mockSpec)).thenReturn(filtered_superheros_mock);
		Iterable<Superhero> superheros = service.findAllSuperheros(mockSpec);
		assertThat(superheros).isNotEmpty();
		assertThat(superheros).size().isEqualTo(1);
	}

	@Test
	public void whenFilterByPseudonym_andFindsNone_thenReturnEmptySuperheros() {

		Specification<Superhero> mockSpec = mock(Specification.class, withSettings().serializable());
		mockSpec.equals(SUPERHERO_PSEUDONYM);
		Mockito.when(superheroRepository.findAll(mockSpec)).thenReturn(EMPTY_ITERABLE_LIST);
		Iterable<Superhero> superheros = service.findAllSuperheros(mockSpec);
		assertThat(superheros).isEmpty();
	}

	@Test
	public void whenFindById_thenReturnSuperhero() throws EntityNotFoundException {
		Superhero superhero = TestUtils.mockSuperMan("SuperMan", "Man of Steel_01", "DC");
		superhero.setId(SUPERHERO_ID);
		Mockito.when(superheroRepository.findById(SUPERHERO_ID)).thenReturn(Optional.of(superhero));
		Superhero result = service.findSuperheroById(SUPERHERO_ID);
		assertEquals(superhero, result);
	}

	@Test(expected = EntityNotFoundException.class)
	public void whenNotFindById_thenThrowsEntityNotFoundException() throws EntityNotFoundException {
		Mockito.when(superheroRepository.findById(SUPERHERO_ID)).thenReturn(Optional.empty());
		service.findSuperheroById(SUPERHERO_ID);
	}

	@Test
	public void whenFindByPseudonym_thenReturnSuperhero() throws EntityNotFoundException {
		Superhero superhero = TestUtils.mockSuperMan("SuperMan", "Man of Steel_01", "DC");
		Mockito.when(superheroRepository.findByPseudonym(SUPERHERO_PSEUDONYM)).thenReturn(superhero);
		Superhero result = service.findSuperheroByPseudonym(SUPERHERO_PSEUDONYM);
		assertEquals(superhero, result);
	}

	@Test(expected = EntityNotFoundException.class)
	public void whenNotFindByPseudonym_thenThrowsEntityNotFoundException() throws EntityNotFoundException {
		Mockito.when(superheroRepository.findByPseudonym(SUPERHERO_PSEUDONYM)).thenReturn(null);
		service.findSuperheroByPseudonym(SUPERHERO_PSEUDONYM);
	}

	private static final long SUPERHERO_ID = 1L;
	private static final String SUPERHERO_PSEUDONYM = "Man of Steel_01";
	private static final List<Superhero> superheros_mock = Arrays.asList(
			TestUtils.mockSuperMan("SuperMan", "Man of Steel_01", "DC"),
			TestUtils.mockBatMan("Batman", "Dark Knight_02", "DC"));
	private static final List<Superhero> EMPTY_ITERABLE_LIST = new ArrayList<Superhero>();

}

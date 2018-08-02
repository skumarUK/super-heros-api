/*
 * Copyright 2018 the original author.
 *
 * Superhero API is build for interview @Payworks.
 *
 */
package com.payworks.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payworks.model.Superhero;
import com.payworks.security.ApplicationUsers;
import com.payworks.utils.TestUtils;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SuperheroControllerIntegrationTests {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ApplicationUsers application;

	private MockMvc mockMvc;

	@After
	public void close() {
		SecurityContextHolder.clearContext();
	}

	@Before
	public void setUp() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(this.getApplicationUser(), this.getApplicationUserPassword()));
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation))
				.alwaysDo(document("{class-name}-{method-name}", preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())))
				.build();
	}

	@Test
	public void index() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk())
				.andDo(document("index", responseHeaders(headerWithName("Content-Type")
						.description("The Content-Type of the payload, e.g. 'application/hal+json'"))));
	}

	@Test
	public void superheroGetWithProvidedId() throws Exception {
		// prepare test data.
		Superhero superhero = createSuperhero(TestUtils.mockSuperMan("SuperMan", "Man of Steel_01", "DC"));
		long id = superhero.getId();
		this.mockMvc.perform(get("/superheros/{id}", id).accept(MediaTypes.HAL_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is((int) id))).andExpect(jsonPath("$.name", is(superhero.getName())))
				.andExpect(jsonPath("$.pseudonym", is(superhero.getPseudonym())))
				.andExpect(jsonPath("$.publisher", is(superhero.getPublisher())))
				.andExpect(jsonPath("$.allies[0].name", is(superhero.getAllies().get(0).getName())))
				.andExpect(jsonPath("$.skills[0].name", is(superhero.getSkills().get(0).getName())));
	}

	@Test
	public void superheroGetWithProvidedPseudonym() throws Exception {
		Superhero superhero = createSuperhero(TestUtils.mockBatMan("Batman", "Dark Knight_02", "DC"));
		String pseudonym = superhero.getPseudonym();
		this.mockMvc.perform(get("/superheros").param("pseudonym", pseudonym).accept(MediaTypes.HAL_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].name", is(superhero.getName())))
				.andExpect(jsonPath("$[0].pseudonym", is(superhero.getPseudonym())))
				.andExpect(jsonPath("$[0].publisher", is(superhero.getPublisher())))
				.andExpect(jsonPath("$[0].allies[0].name", is(superhero.getAllies().get(0).getName())))
				.andExpect(jsonPath("$[0].skills[0].name", is(superhero.getSkills().get(0).getName())));
	}

	@Test
	public void superheroGetWithProvidedPublisher() throws Exception {
		// prepare test data by publisher
		String publisher = "DC01";
		createSuperhero(TestUtils.mockBatMan("Batman", "Dark Knight_03", publisher));
		createSuperhero(TestUtils.mockSuperMan("SuperMan", "Man of Steel_04", publisher));

		// fetch superhero by publisher
		this.mockMvc.perform(get("/superheros").param("publisher", publisher).accept(MediaTypes.HAL_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].publisher", is(publisher)))
				.andExpect(jsonPath("$[1].publisher", is(publisher)));
	}

	@Test
	public void superheroCreateWithProvidedDetails() throws Exception {
		Superhero superhero = TestUtils.mockBatMan("Batman", "Dark Knight_05", "DC");
		this.mockMvc.perform(post("/superheros").content(objectMapper.writeValueAsString(superhero))
				.contentType(MediaTypes.HAL_JSON).accept(MediaTypes.HAL_JSON)).andExpect(status().isCreated());
	}

	@Test
	public void GetSuperheros() throws Exception {

		createSuperhero(TestUtils.mockBatMan("Batman", "Dark Knight_06", "DC"));
		this.mockMvc.perform(get("/superheros")).andExpect(status().isOk());

	}

	private Superhero createSuperhero(Superhero superhero) throws Exception {
		MvcResult result = this.mockMvc
				.perform(post("/superheros").content(objectMapper.writeValueAsString(superhero))
						.contentType(MediaTypes.HAL_JSON).accept(MediaTypes.HAL_JSON))
				.andExpect(status().isCreated()).andReturn();

		return objectMapper.readValue(result.getResponse().getContentAsString(), Superhero.class);

	}

	private String getApplicationUser() {
		return application.getUsers().get(0).getUsername();
	}

	private String getApplicationUserPassword() {
		return application.getUsers().get(0).getPassword();
	}
}

/*
 * Copyright 2018 the original author.
 *
 * Superhero API is build for interview @Payworks.
 *
 */
package com.payworks.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.payworks.security.ApplicationUsers.ApplicationUser;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(ApplicationUsers.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] PUBLIC_MATCHERS = { "/", "/docs/**" };

	@Autowired
	ApplicationUsers application;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().antMatchers("/superheros*")
				.hasAnyRole("ADMIN", "USER").anyRequest().authenticated().and().requiresChannel().anyRequest()
				.requiresSecure().and().httpBasic();
		http.csrf().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		BCryptPasswordEncoder passwordEncoder = bCryptPasswordEncoder();
		for (ApplicationUser client : application.getUsers()) {
			auth.inMemoryAuthentication().passwordEncoder(passwordEncoder).withUser(client.getUsername())
					.password(passwordEncoder.encode(client.getPassword())).roles(client.getRoles());
		}
	}
}
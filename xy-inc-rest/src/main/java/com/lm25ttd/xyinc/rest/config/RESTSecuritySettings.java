package com.lm25ttd.xyinc.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.lm25ttd.xyinc.service.AccountService;

/**
 * Methods and declarations for REST Security Settings.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
@Configuration
@EnableWebSecurity
@ComponentScan({ "com.lm25ttd.xyinc.service" })
public class RESTSecuritySettings extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccountService accountService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.accountService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().anyRequest().authenticated().and().httpBasic()
				.authenticationEntryPoint(new XYIncAuthenticationEntryPoint());

		// Other settings
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}

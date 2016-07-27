package com.lm25ttd.xyinc.rest.v1.resources;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.lm25ttd.xyinc.core.config.TestDatabaseSettings;
import com.lm25ttd.xyinc.model.Account;
import com.lm25ttd.xyinc.model.dao.AccountRepository;
import com.lm25ttd.xyinc.rest.config.RESTSecuritySettings;
import com.lm25ttd.xyinc.rest.config.RESTSettings;

/**
 * Integration tests for Account REST resource.
 *
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
@ContextConfiguration(classes = { TestDatabaseSettings.class, RESTSettings.class, RESTSecuritySettings.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(value = { "classpath:dataset-AccountIT.xml" }, type = DatabaseOperation.INSERT)
@DatabaseTearDown(value = { "classpath:dataset-AccountIT.xml" }, type = DatabaseOperation.DELETE_ALL)
public class AccountResourceIT {

	private static final String RESOURCE_URL = "/v1/accounts/login";

	private MockMvc mockMvc;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private Filter springSecurityFilterChain;

	private static final String ADMIN_NAME = "ADMIN";
	private static final String USER1_NAME = "USER1";
	private static final String USER2_NAME = "USER2";
	private static final String UNKNOWN_NAME = "UNKNOWN";
	private static final String PASSWORD = "1234";

	private static final RequestPostProcessor ADMIN_BASIC_AUTH = httpBasic(ADMIN_NAME, PASSWORD);
	private static final RequestPostProcessor USER1_BASIC_AUTH = httpBasic(USER1_NAME, PASSWORD);
	private static final RequestPostProcessor USER2_BASIC_AUTH = httpBasic(USER2_NAME, PASSWORD);
	private static final RequestPostProcessor UNKNOWN_AUTH = httpBasic(UNKNOWN_NAME, PASSWORD);

	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
				.addFilter(this.springSecurityFilterChain).build();
	}

	@Test
	public void testLoginOfAdminstratorWorks() throws Exception {
		Account account = accountRepository.findOne(1L);
		this.mockMvc.perform(get(RESOURCE_URL).with(ADMIN_BASIC_AUTH)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(account.getId().intValue())))
				.andExpect(jsonPath("$.login", is(account.getLogin())))
				.andExpect(jsonPath("$.role", is(account.getRole().toString())));
	}

	@Test
	public void testLoginOfUserOneWorks() throws Exception {
		Account account = accountRepository.findOne(2L);
		this.mockMvc.perform(get(RESOURCE_URL).with(USER1_BASIC_AUTH)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(account.getId().intValue())))
				.andExpect(jsonPath("$.login", is(account.getLogin())))
				.andExpect(jsonPath("$.role", is(account.getRole().toString())));
	}

	@Test
	public void testLoginOfUserTwoWorks() throws Exception {
		Account account = accountRepository.findOne(3L);
		this.mockMvc.perform(get(RESOURCE_URL).with(USER2_BASIC_AUTH)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(account.getId().intValue())))
				.andExpect(jsonPath("$.login", is(account.getLogin())))
				.andExpect(jsonPath("$.role", is(account.getRole().toString())));
	}

	@Test
	public void testLoginOfUnkown_thenUnauthorized() throws Exception {
		this.mockMvc.perform(get(RESOURCE_URL).with(UNKNOWN_AUTH))
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void testNoLogin_thenUnauthorized() throws Exception {
		this.mockMvc.perform(get(RESOURCE_URL))
				.andExpect(status().isUnauthorized());
	}

}

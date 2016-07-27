package com.lm25ttd.xyinc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.lm25ttd.xyinc.core.config.TestDatabaseSettings;
import com.lm25ttd.xyinc.core.exceptions.EntityNotFoundException;
import com.lm25ttd.xyinc.model.Account;
import com.lm25ttd.xyinc.model.enums.AccountRole;
import com.lm25ttd.xyinc.service.impl.AccountServiceImpl;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDatabaseSettings.class, AccountServiceImpl.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(value = { "classpath:dataset-AccountServiceIT.xml" }, type = DatabaseOperation.INSERT)
@DatabaseTearDown(value = { "classpath:dataset-AccountServiceIT.xml" }, type = DatabaseOperation.DELETE_ALL)
public class AccountServiceIT {

	@Autowired
	private AccountService accountService;

	@Test
	public void testLoadAccountByIdWorks() throws EntityNotFoundException {
		Long expectedId = 1L;
		String expectedLogin = "ADMIN";
		AccountRole expectedRole = AccountRole.ADMIN;
		Account account = accountService.getAccount(expectedId);

		assertNotNull(account);
		assertEquals(expectedId, account.getId());
		assertEquals(expectedLogin, account.getLogin());
		assertEquals(expectedRole, account.getRole());
	}

	@Test(expected = EntityNotFoundException.class)
	public void testLoadAccountByIdNotFound() throws EntityNotFoundException {
		Long expectedId = 8L;
		accountService.getAccount(expectedId);
	}

	@Test
	public void testLoadAccountByLogin() throws EntityNotFoundException {
		Long expectedId = 1L;
		String expectedLogin = "ADMIN";
		AccountRole expectedRole = AccountRole.ADMIN;
		Account account = accountService.getAccountByLogin(expectedLogin);

		assertNotNull(account);
		assertEquals(expectedId, account.getId());
		assertEquals(expectedLogin, account.getLogin());
		assertEquals(expectedRole, account.getRole());
	}

	@Test(expected = EntityNotFoundException.class)
	public void testLoadAccountByLoginNotFound() throws EntityNotFoundException {
		String expectedLogin = "not_exists_";
		accountService.getAccountByLogin(expectedLogin);
	}

	@Test
	public void testLoadAccountByUsernameWorks() throws EntityNotFoundException {
		String expectedLogin = "ADMIN";
		UserDetails account = accountService.loadUserByUsername(expectedLogin);

		assertNotNull(account);
		assertEquals(expectedLogin, account.getUsername());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void testLoadAccountByUsernameNotFound() throws UsernameNotFoundException {
		String expectedLogin = "NOT_EXISTS_";
		accountService.loadUserByUsername(expectedLogin);
	}

	@Test
	public void testHasNotCurrentAccountWorks() throws EntityNotFoundException {
		Boolean actual = accountService.hasCurrentAccount();
		assertFalse(actual);
	}
}

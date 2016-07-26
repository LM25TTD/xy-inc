package com.lm25ttd.xyinc.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.lm25ttd.xyinc.model.dao.AccountRepository;

/**
 * Tests for Account entity and DAO.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDatabaseSettings.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(value = { "classpath:dataset-AccountIT.xml" }, type = DatabaseOperation.INSERT)
@DatabaseTearDown(value = { "classpath:dataset-AccountIT.xml" }, type = DatabaseOperation.DELETE_ALL)
public class AccountIT {

	@Autowired
	AccountRepository accountRepository;

	@Test
	public void testRetrieveAccountByIdWorks() {
		Long expectedId = 1L;
		String expectedLogin = "ADMIN";
		Account account = accountRepository.findById(expectedId);

		assertNotNull(account);
		assertEquals(expectedId, account.getId());
		assertEquals(expectedLogin, account.getLogin());
	}

	@Test
	public void testRetrieveAccountByLoginWorks() {
		String expectedLogin = "ADMIN";
		Account account = accountRepository.findByLogin(expectedLogin);

		assertNotNull(account);
		assertEquals(expectedLogin, account.getLogin());
	}

	@Test
	public void testRetrieveAllAccountsWorks() {
		int expectedSize = 3;
		assertEquals(expectedSize, accountRepository.findAll().size());
	}

}

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
import com.lm25ttd.xyinc.model.dao.ProductRepository;

/**
 * Tests for Product entity and DAO.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDatabaseSettings.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(value = { "classpath:dataset-ProductIT.xml" }, type = DatabaseOperation.INSERT)
@DatabaseTearDown(value = { "classpath:dataset-ProductIT.xml" }, type = DatabaseOperation.DELETE_ALL)
public class ProductIT {

	@Autowired
	ProductRepository productRepository;

	@Test
	public void testCreateProductWorks() {

		Product prod = new Product();
		prod.setName("ProdSix");
		prod.setDescription("A new product inserted");
		prod.setPrice(5.57);
		prod.setCategory("NEW_THINGS");

		productRepository.save(prod);

		int expectedSize = 6;
		int actualSize = productRepository.findAll().size();
		assertEquals(expectedSize, actualSize);

		Long expectedId = 6L;
		prod = productRepository.findById(expectedId);
		assertEquals(expectedId, prod.getId());
	}

	@Test
	public void testRetrieveByIdWorks() {
		Long expectedId = 1L;
		String expectedName = "ProdOne";
		Product product = productRepository.findById(expectedId);

		assertNotNull(product);
		assertEquals(expectedId, product.getId());
		assertEquals(expectedName, product.getName());
	}

	@Test
	public void testRetrieveAllProductsWorks() {
		int expectedSize = 5;
		int actualSize = productRepository.findAll().size();
		assertEquals(expectedSize, actualSize);
	}

	@Test
	public void testUpdateWorks() {
		Long expectedId = 1L;
		String expectedName = "ProdOneUpdated";
		Product product = productRepository.findById(expectedId);

		product.setName(expectedName);
		productRepository.save(product);

		product = productRepository.findById(expectedId);

		assertNotNull(product);
		assertEquals(expectedId, product.getId());
		assertEquals(expectedName, product.getName());

	}
	
	@Test
	public void testDeleteWorks() {
		Long expectedId = 1L;	
		productRepository.delete(expectedId);
		
		int expectedSize = 4;
		int actualSize = productRepository.findAll().size();
		assertEquals(expectedSize, actualSize);
	}
	
	@Test
	public void testDeleteAllWorks() {
		
		productRepository.deleteAll();
		
		int expectedSize = 0;
		int actualSize = productRepository.findAll().size();
		assertEquals(expectedSize, actualSize);
	}

}

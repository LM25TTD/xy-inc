package com.lm25ttd.xyinc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

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
import com.lm25ttd.xyinc.core.exceptions.EntityNotFoundException;
import com.lm25ttd.xyinc.core.exceptions.IdentifierUpdatedException;
import com.lm25ttd.xyinc.model.Product;
import com.lm25ttd.xyinc.service.exceptions.ProductNameAlreadyExistsException;
import com.lm25ttd.xyinc.service.exceptions.ProductNotFoundException;
import com.lm25ttd.xyinc.service.impl.ProductServiceImpl;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDatabaseSettings.class, ProductServiceImpl.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(value = { "classpath:dataset-ProductServiceIT.xml" }, type = DatabaseOperation.INSERT)
@DatabaseTearDown(value = { "classpath:dataset-ProductServiceIT.xml" }, type = DatabaseOperation.DELETE_ALL)
public class ProductServiceIT {

	@Autowired
	private ProductService productService;

	@Test
	public void getAllProducts() {
		int expectedSize = 5;
		List<Product> allProducts = productService.getAllProducts();

		assertNotNull(allProducts);
		assertEquals(expectedSize, allProducts.size());
	}

	@Test
	public void getProductByIdWorks() throws ProductNotFoundException {
		Long expectedId = 1L;
		String expectedName = "ProdOne";
		Product actual = productService.getProductById(expectedId);

		assertNotNull(actual);
		assertEquals(expectedId, actual.getId());
		assertEquals(expectedName, actual.getName());
	}

	@Test(expected = EntityNotFoundException.class)
	public void getProductByIdNotFound() throws ProductNotFoundException {
		Long expectedId = 8L;
		productService.getProductById(expectedId);
	}

	@Test
	public void createProductWorks()
			throws ProductNameAlreadyExistsException, IdentifierUpdatedException, ProductNotFoundException {
		Product prod = new Product();
		prod.setName("ProdSix");
		prod.setDescription("A new product inserted");
		prod.setPrice(5.57);
		prod.setCategory("NEW_THINGS");

		productService.saveProduct(prod);

		int expectedSize = 6;
		List<Product> allProducts = productService.getAllProducts();

		assertNotNull(allProducts);
		assertEquals(expectedSize, allProducts.size());

		Long expectedId = 6L;
		String expectedName = "ProdSix";
		Product actual = productService.getProductById(expectedId);

		assertNotNull(actual);
		assertEquals(expectedId, actual.getId());
		assertEquals(expectedName, actual.getName());
	}

	@Test(expected = ProductNameAlreadyExistsException.class)
	public void createProductNameAlreadyExists() throws ProductNameAlreadyExistsException, IdentifierUpdatedException {
		Product prod = new Product();
		prod.setName("ProdOne");
		prod.setDescription("A new product inserted");
		prod.setPrice(5.57);
		prod.setCategory("NEW_THINGS");

		productService.saveProduct(prod);
	}

	@Test(expected = IdentifierUpdatedException.class)
	public void createProductIdentifierUpdated()
			throws ProductNameAlreadyExistsException, IdentifierUpdatedException, ProductNotFoundException {
		Long expectedId = 1L;
		Product actual = productService.getProductById(expectedId);

		assertNotNull(actual);
		assertEquals(expectedId, actual.getId());

		actual.setId(8L);
		productService.saveProduct(actual);
	}

	@Test
	public void updateProductWorks()
			throws ProductNameAlreadyExistsException, IdentifierUpdatedException, ProductNotFoundException {
		Long expectedId = 1L;
		String expectedName = "ProdOne";
		Product prod = productService.getProductById(expectedId);

		assertNotNull(prod);
		assertEquals(expectedId, prod.getId());
		assertEquals(expectedName, prod.getName());

		expectedName = "ProdOneModified";

		prod.setName(expectedName);
		productService.saveProduct(prod);

		prod = productService.getProductById(expectedId);
		assertNotNull(prod);
		assertEquals(expectedId, prod.getId());
		assertEquals(expectedName, prod.getName());

	}

	@Test
	public void deleteProductWorks() throws ProductNotFoundException {
		Long expectedId = 1L;
		Product prod = productService.getProductById(expectedId);
		assertNotNull(prod);

		productService.deleteProduct(prod);

		int expectedSize = 4;
		int allProductsSize = productService.getAllProducts().size();
		assertEquals(expectedSize, allProductsSize);
	}

	@Test
	public void deleteProductByIdWorks() throws ProductNotFoundException {
		Long expectedId = 1L;
		productService.deleteProductById(expectedId);

		int expectedSize = 4;
		int allProductsSize = productService.getAllProducts().size();
		assertEquals(expectedSize, allProductsSize);
	}

	@Test(expected = ProductNotFoundException.class)
	public void deleteProductAnonymousNotFound() throws ProductNotFoundException {
		Product prod = new Product();
		prod.setName("ProdOne");
		prod.setDescription("A new product inserted");
		prod.setPrice(5.57);
		prod.setCategory("NEW_THINGS");
		
		productService.deleteProduct(prod);
	}
	
	@Test(expected = ProductNotFoundException.class)
	public void deleteProductByIdNotFound() throws ProductNotFoundException {
		Long expectedId = 9L;
		productService.deleteProductById(expectedId);
	}
}

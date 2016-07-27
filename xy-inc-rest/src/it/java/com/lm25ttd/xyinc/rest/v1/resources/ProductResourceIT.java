package com.lm25ttd.xyinc.rest.v1.resources;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import com.lm25ttd.xyinc.model.Product;
import com.lm25ttd.xyinc.model.dao.ProductRepository;
import com.lm25ttd.xyinc.rest.config.RESTSecuritySettings;
import com.lm25ttd.xyinc.rest.config.RESTSettings;
import com.lm25ttd.xyinc.rest.utils.RESTUtils;

/**
 * Integration tests for Product REST resource.
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
@DatabaseSetup(value = { "classpath:dataset-ProductIT.xml",
		"classpath:dataset-AccountIT.xml" }, type = DatabaseOperation.INSERT)
@DatabaseTearDown(value = { "classpath:dataset-ProductIT.xml",
		"classpath:dataset-AccountIT.xml" }, type = DatabaseOperation.DELETE_ALL)
public class ProductResourceIT {

	private static final String RESOURCE_URL = "/v1/products";

	private MockMvc mockMvc;

	@Autowired
	private ProductRepository productRepository;

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
	public void testGetAllProductsWorks() throws Exception {
		this.mockMvc.perform(get(RESOURCE_URL).with(USER1_BASIC_AUTH))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThan(0))))
				.andExpect(jsonPath("$", everyItem(hasKey("id"))))
				.andExpect(jsonPath("$", everyItem(hasKey("name"))))
				.andExpect(jsonPath("$", everyItem(hasKey("description"))))
				.andExpect(jsonPath("$", everyItem(hasKey("price"))))
				.andExpect(jsonPath("$", everyItem(hasKey("category"))));
	}

	@Test
	public void testGetAllProductsUnauthorized() throws Exception {
		this.mockMvc.perform(get(RESOURCE_URL)).andExpect(status().isUnauthorized());
	}

	@Test
	public void testGetProductByIdWorks() throws Exception {
		Product product = productRepository.findById(1L);
		assertNotNull(product);
		
		this.mockMvc.perform(get(RESOURCE_URL + "/1").with(USER1_BASIC_AUTH))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(product.getId().intValue())))
				.andExpect(jsonPath("$.name", is(product.getName())))
				.andExpect(jsonPath("$.description", is(product.getDescription())))
				.andExpect(jsonPath("$.price", is(product.getPrice())))
				.andExpect(jsonPath("$.category", is(product.getCategory().toString())));
	}
	
	@Test
	public void testGetProductByIdUnauthorized() throws Exception {		
		this.mockMvc.perform(get(RESOURCE_URL + "/1"))
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void testCreateProductWorks() throws Exception {
		Product product = new Product();
		product.setName("NewProd");
		product.setDescription("A new cool thing");
		product.setPrice(9.99);
		product.setCategory("NEW_THINGS");
		
		int expectedId = 6;
		
		this.mockMvc.perform(post(RESOURCE_URL).with(ADMIN_BASIC_AUTH)
				.contentType(MediaType.APPLICATION_JSON)
	            .content(RESTUtils.convertToJsonBytes(product)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(expectedId)))
				.andExpect(jsonPath("$.name", is(product.getName())))
				.andExpect(jsonPath("$.description", is(product.getDescription())))
				.andExpect(jsonPath("$.price", is(product.getPrice())))
				.andExpect(jsonPath("$.category", is(product.getCategory().toString())));
	}
	
	@Test
	public void testCreateProductAlreadyExists() throws Exception {
		Product product = new Product();
		product.setName("ProdTwo");
		product.setDescription("A new cool thing");
		product.setPrice(9.99);
		product.setCategory("NEW_THINGS");
				
		this.mockMvc.perform(post(RESOURCE_URL).with(ADMIN_BASIC_AUTH)
				.contentType(MediaType.APPLICATION_JSON)
	            .content(RESTUtils.convertToJsonBytes(product)))
				.andExpect(status().isConflict());
	}

	@Test
	public void testCreateProductUnauthorized() throws Exception {
		Product product = new Product();
		product.setName("NewProd");
		product.setDescription("A new cool thing");
		product.setPrice(9.99);
		product.setCategory("NEW_THINGS");
					
		this.mockMvc.perform(post(RESOURCE_URL)
				.contentType(MediaType.APPLICATION_JSON)
	            .content(RESTUtils.convertToJsonBytes(product)))
				.andExpect(status().isUnauthorized());
		
		this.mockMvc.perform(post(RESOURCE_URL).with(USER2_BASIC_AUTH)
				.contentType(MediaType.APPLICATION_JSON)
	            .content(RESTUtils.convertToJsonBytes(product)))
				.andExpect(status().isUnauthorized());
		
		this.mockMvc.perform(post(RESOURCE_URL).with(UNKNOWN_AUTH)
				.contentType(MediaType.APPLICATION_JSON)
	            .content(RESTUtils.convertToJsonBytes(product)))
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void testDeleteProductWorks() throws Exception {					
		this.mockMvc.perform(delete(RESOURCE_URL + "/1")
				.with(ADMIN_BASIC_AUTH))
				.andExpect(status().isNoContent());
		
		this.mockMvc.perform(get(RESOURCE_URL + "/1")
				.with(USER1_BASIC_AUTH))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void testDeleteProductUnauthorized() throws Exception {					
		this.mockMvc.perform(delete(RESOURCE_URL + "/1")
				.with(USER1_BASIC_AUTH))
				.andExpect(status().isUnauthorized());
		
		this.mockMvc.perform(delete(RESOURCE_URL + "/1"))
				.andExpect(status().isUnauthorized());		
	}
	
	@Test
	public void testUpdateProductWorks() throws Exception {
		Long expectedId = 1L;	
		String expectedName = "newName";
		Product product = productRepository.findById(expectedId);
		assertNotNull(product);
		
		product.setName(expectedName);
				
		this.mockMvc.perform(put(RESOURCE_URL + "/1").with(ADMIN_BASIC_AUTH)
				.contentType(MediaType.APPLICATION_JSON)
	            .content(RESTUtils.convertToJsonBytes(product)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(expectedId.intValue())))
				.andExpect(jsonPath("$.name", is(expectedName)))
				.andExpect(jsonPath("$.description", is(product.getDescription())))
				.andExpect(jsonPath("$.price", is(product.getPrice())))
				.andExpect(jsonPath("$.category", is(product.getCategory().toString())));
	}
	
	@Test
	public void testUpdateProductUnauthorized() throws Exception {
		Long expectedId = 1L;	
		String expectedName = "newName";
		Product product = productRepository.findById(expectedId);
		assertNotNull(product);
		
		product.setName(expectedName);
				
		this.mockMvc.perform(put(RESOURCE_URL + "/1").with(USER2_BASIC_AUTH)
				.contentType(MediaType.APPLICATION_JSON)
	            .content(RESTUtils.convertToJsonBytes(product)))
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void testUpdateProductNotFound() throws Exception {
		Product product = new Product();
		product.setId(8L);
		product.setName("NewProd");
		product.setDescription("A new cool thing");
		product.setPrice(9.99);
		product.setCategory("NEW_THINGS");
				
		this.mockMvc.perform(put(RESOURCE_URL + "/8").with(ADMIN_BASIC_AUTH)
				.contentType(MediaType.APPLICATION_JSON)
	            .content(RESTUtils.convertToJsonBytes(product)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void testUpdateProductAlreadyExists() throws Exception {
		Product product = new Product();
		product.setId(1L);
		product.setName("ProdTwo");
		product.setDescription("A new cool thing");
		product.setPrice(9.99);
		product.setCategory("NEW_THINGS");
				
		this.mockMvc.perform(put(RESOURCE_URL + "/1").with(ADMIN_BASIC_AUTH)
				.contentType(MediaType.APPLICATION_JSON)
	            .content(RESTUtils.convertToJsonBytes(product)))
				.andExpect(status().isConflict());
	}
	
}

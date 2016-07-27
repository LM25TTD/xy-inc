package com.lm25ttd.xyinc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lm25ttd.xyinc.core.exceptions.IdentifierUpdatedException;
import com.lm25ttd.xyinc.core.utils.Utils;
import com.lm25ttd.xyinc.model.Product;
import com.lm25ttd.xyinc.model.dao.ProductRepository;
import com.lm25ttd.xyinc.service.ProductService;
import com.lm25ttd.xyinc.service.exceptions.ProductNameAlreadyExistsException;
import com.lm25ttd.xyinc.service.exceptions.ProductNotFoundException;

/**
 * Implementation of service of Product entity.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
@Service
@Transactional("xyincTransactionManager")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(Long id) throws ProductNotFoundException {
		return retrieveIfExists(id);
	}

	@Override
	public Product getProductByName(String name) throws ProductNotFoundException {
		return retrieveIfExists(name);
	}

	@Override
	public Product saveProduct(Product product) throws ProductNameAlreadyExistsException, IdentifierUpdatedException {
		Product retrieved = productRepository.findByName(product.getName());
		if (retrieved != null && product.anonymous()) {
			throw new ProductNameAlreadyExistsException(product.getName());
		} else if (!product.anonymous() && retrieved != null && !Utils.equals(product.getId(), retrieved.getId())) {
			throw new IdentifierUpdatedException();
		}
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product)
			throws ProductNotFoundException, ProductNameAlreadyExistsException {
		if(!productRepository.exists(product.getId()))
		{
			throw new ProductNotFoundException(product.getId());
		}		
		if(productRepository.existsOtherProductWithName(product.getId(), product.getName())){
			throw new ProductNameAlreadyExistsException(product.getName());
		}
		return productRepository.save(product);
	}

	@Override
	public void deleteProduct(Product product) throws ProductNotFoundException {
		if (!product.anonymous()) {
			Product retrieved = retrieveIfExists(product.getId());
			productRepository.delete(retrieved);
		} else {
			throw new ProductNotFoundException(0L);
		}
	}

	@Override
	public void deleteProductById(Long id) throws ProductNotFoundException {
		Product retrieved = retrieveIfExists(id);
		productRepository.delete(retrieved);
	}

	private Product retrieveIfExists(Long id) throws ProductNotFoundException {
		Product retrieved = productRepository.findById(id);
		if (retrieved == null) {
			throw new ProductNotFoundException(id);
		}
		return retrieved;
	}

	private Product retrieveIfExists(String name) throws ProductNotFoundException {
		Product retrieved = productRepository.findByName(name);
		if (retrieved == null) {
			throw new ProductNotFoundException(name);
		}
		return retrieved;
	}
}

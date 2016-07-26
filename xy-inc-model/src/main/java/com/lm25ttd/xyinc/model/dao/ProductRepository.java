package com.lm25ttd.xyinc.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lm25ttd.xyinc.model.Product;

/**
 * Data access definitions for Products.
 * Spring auto generate methods based on its names (for simple queries).
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	/**
	 * Looks up a product by its Id.
	 * 
	 * @param id
	 *            The identifier of product to be retrieved.
	 * @return The product object or <code>null</code> if not found.
	 */
	public Product findById(Long id);

	/**
	 * Looks up a product by its unique name.
	 * 
	 * @param name
	 *            The name used as search criteria.
	 * @return The product object or <code>null</code> if not found.
	 */
	public Product findByName(String name);
}

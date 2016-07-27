package com.lm25ttd.xyinc.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
	
	/**
	 * Check if other product has a given name.
	 * @param id The id of current product.
	 * @param name The name to be checked
	 * @return True if other product has the name, false otherwise.
	 */
	@Query("SELECT CASE WHEN COUNT(product) > 0 THEN 'true' ELSE 'false' END "
            + "FROM Product product "
            + "WHERE product.id != ?1 "
            + "AND product.name = ?2 ")
	public boolean existsOtherProductWithName(Long id, String name);
}

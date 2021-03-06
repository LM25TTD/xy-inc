package com.lm25ttd.xyinc.service;

import java.util.List;

import com.lm25ttd.xyinc.core.exceptions.IdentifierUpdatedException;
import com.lm25ttd.xyinc.model.Product;
import com.lm25ttd.xyinc.service.exceptions.ProductNameAlreadyExistsException;
import com.lm25ttd.xyinc.service.exceptions.ProductNotFoundException;

/**
 * Exposed service of Product entity.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
public interface ProductService {

	/**
	 * Retrieves all products.
	 * 
	 * @return A {@link List} of {@link Product} objects or <code>null</code>
	 *         the database is empty.
	 */
	public List<Product> getAllProducts();

	/**
	 * Retrieves a {@link Product} object with given id.
	 * 
	 * @param id
	 *            The identifier of the product.
	 * @return A {@link Product} object.
	 * @throws ProductNotFoundException
	 *             If there is no product with given id.
	 */
	public Product getProductById(Long id) throws ProductNotFoundException;

	/**
	 * Retrieves a {@link Product} object with given name.
	 * 
	 * @param name
	 *            The name of the product.
	 * @return A {@link Product} object.
	 * @throws ProductNotFoundException
	 *             If there is no product with given name.
	 */
	public Product getProductByName(String name) throws ProductNotFoundException;

	/**
	 * Save a new {@link Product} object.
	 * 
	 * @param product
	 *            The {@link Product} object to be saved.
	 * @return The saved {@link Product} object.
	 * @throws ProductNameAlreadyExistsException
	 *             If there is other product with same name into system.
	 * @throws IdentifierUpdatedException
	 *             If during the update case, an id change is applied (Id cannot
	 *             be updated).
	 */
	public Product saveProduct(Product product) throws ProductNameAlreadyExistsException, IdentifierUpdatedException;

	/**
	 * Updates a {@link Product} object.
	 * 
	 * @param product
	 *            The {@link Product} object to be updated.
	 * @return The updated {@link Product} object.
	 * @throws ProductNotFoundException
	 *             If the product doesn't exists into database.
	 * @throws ProductNameAlreadyExistsException
	 *             If there is other product with same name into system.
	 */
	public Product updateProduct(Product product)
			throws ProductNotFoundException, ProductNameAlreadyExistsException;

	/**
	 * Delete a given {@link Product} object from database.
	 * 
	 * @param product
	 *            The {@link Product} object to be deleted.
	 * @throws ProductNotFoundException
	 *             If the product doesn't exists into database.
	 */
	public void deleteProduct(Product product) throws ProductNotFoundException;

	/**
	 * Delete a {@link Product} from database based on its Id.
	 * 
	 * @param id
	 *            The identifier of the product.
	 * @throws ProductNotFoundException
	 *             If there is no one product with the given id into database.
	 */
	public void deleteProductById(Long id) throws ProductNotFoundException;

}

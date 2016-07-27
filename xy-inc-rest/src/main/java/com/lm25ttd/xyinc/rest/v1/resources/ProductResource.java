package com.lm25ttd.xyinc.rest.v1.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lm25ttd.xyinc.core.exceptions.IdentifierUpdatedException;
import com.lm25ttd.xyinc.model.Product;
import com.lm25ttd.xyinc.model.enums.AccountRole;
import com.lm25ttd.xyinc.rest.utils.RESTUtils;
import com.lm25ttd.xyinc.rest.v1.dto.ProductDTO;
import com.lm25ttd.xyinc.service.ProductService;
import com.lm25ttd.xyinc.service.exceptions.ProductNameAlreadyExistsException;
import com.lm25ttd.xyinc.service.exceptions.ProductNotFoundException;

/**
 * Implementation of REST API Contract related to Products.
 *
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
@RestController
@RequestMapping(value = "/v1/products")
public class ProductResource {

	@Autowired
	private ProductService productService;

	/**
	 * Return all saved Products.
	 * 
	 * @return A JSON with a List of Product.
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<ProductDTO> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return RESTUtils.convertAllProducts(products);
	}

	/**
	 * Return a JSON object with a Product of a given id.
	 * 
	 * @param id
	 *            A {@link Long} that is the unique identifier.
	 * @return A {@link Product} JSON.
	 * @throws ProductNotFoundException
	 *             HTTP 404 - If a Product with this ID doesn't exists in
	 *             database.
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ProductDTO getProductById(@PathVariable Long id) throws ProductNotFoundException {
		Product product = productService.getProductById(id);
		return new ProductDTO(product);
	}

	/**
	 * Create a new product into database.
	 * 
	 * @param productDTO
	 *            The body of request must contains a JSON Product object.
	 * @return A JSON representation of the saved Product.
	 * @throws ProductNameAlreadyExistsException
	 *             - HTTP 409
	 * @throws IdentifierUpdatedException
	 *             - HTTP 500
	 * @throws ProductNotFoundException
	 *             - HTTP 404
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@PreAuthorize("hasAnyAuthority('" + AccountRole.Names.ADMIN + "')")
	public ProductDTO createProduct(@RequestBody ProductDTO productDTO)
			throws ProductNameAlreadyExistsException, IdentifierUpdatedException, ProductNotFoundException {
		Product savedProduct = productService.saveProduct(productDTO.getWrapped());
		return new ProductDTO(savedProduct);
	}

	/**
	 * Updates a product info into database.
	 * 
	 * @param id
	 *            The ID of the Product to be updated.
	 * @param productDTO
	 *            The body of request must contains a JSON Product object.
	 * @return A JSON representation of the updated Product.
	 * @throws ProductNotFoundException
	 *             - HTTP 404
	 * @throws ProductNameAlreadyExistsException
	 *             - HTTP 409
	 * @throws IdentifierUpdatedException
	 *             - HTTP 500
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	@PreAuthorize("hasAnyAuthority('" + AccountRole.Names.ADMIN + "')")
	public ProductDTO editProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO)
			throws ProductNotFoundException, ProductNameAlreadyExistsException, IdentifierUpdatedException {
		Product product = productDTO.getWrapped();
		product.setId(id);
		product = productService.updateProduct(product);
		return new ProductDTO(product);
	}

	/**
	 * Deletes a Product with the given ID.
	 * 
	 * @param id
	 *            The ID of the Product to be deleted.
	 * @throws ProductNotFoundException
	 *             - HTTP 404
	 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAnyAuthority('" + AccountRole.Names.ADMIN + "')")
	public void deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
		productService.deleteProductById(id);
	}
}

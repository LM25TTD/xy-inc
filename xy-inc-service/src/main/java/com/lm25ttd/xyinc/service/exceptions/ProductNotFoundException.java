package com.lm25ttd.xyinc.service.exceptions;

import com.lm25ttd.xyinc.core.exceptions.EntityNotFoundException;

/**
 * Thrown when a product with given id is not found.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
public class ProductNotFoundException extends EntityNotFoundException {
	
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(Long id) {
		super("exception.product.not.found", id);
	}
}

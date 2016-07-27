package com.lm25ttd.xyinc.service.exceptions;

import com.lm25ttd.xyinc.core.exceptions.EntityAlreadyExistsException;

/**
 * Thrown when a product with same name exists into database.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
public class ProductNameAlreadyExistsException extends EntityAlreadyExistsException {
	private static final long serialVersionUID = 1L;

	public ProductNameAlreadyExistsException(String name) {
		super("exception.product.name.already.exists", name);
	}
}

package com.lm25ttd.xyinc.rest.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lm25ttd.xyinc.core.exceptions.XYIncUnexpectedException;
import com.lm25ttd.xyinc.model.Product;
import com.lm25ttd.xyinc.rest.v1.dto.ProductDTO;

/**
 * Util methods for REST operations.
 *
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
public class RESTUtils {

	/**
	 * Applies the default DTO to a list of Product.
	 * 
	 * @param products
	 *            The list of products.
	 * @return A {@link List} of {@link ProductDTO}.
	 */
	public static List<ProductDTO> convertAllProducts(List<Product> products) {
		List<ProductDTO> result = new ArrayList<>(products.size());
		for (Product product : products) {
			result.add(new ProductDTO(product));
		}
		return result;
	}

	/**
	 * Converts a Java object to a binary JSON string (byte array).
	 */
	public static byte[] convertToJsonBytes(Object object) {

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			return mapper.writeValueAsBytes(object);
		} catch (IOException e) {
			throw new XYIncUnexpectedException(e);
		}
	}

}

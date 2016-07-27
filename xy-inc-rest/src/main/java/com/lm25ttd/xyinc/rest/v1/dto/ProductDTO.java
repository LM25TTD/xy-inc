package com.lm25ttd.xyinc.rest.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lm25ttd.xyinc.model.Identifiable;
import com.lm25ttd.xyinc.model.Product;

/**
 * Data Type Object for Product.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
@JsonInclude(Include.NON_NULL)
public class ProductDTO extends GenericDTO<Product> {

	/**
	 * Creates an instance of AccountDTO.
	 */
	public ProductDTO() {
		super(new Product());
	}

	/**
	 * Creates an instance of AccountDTO.
	 */
	public ProductDTO(Product product) {
		super(product);
	}

	/**
	 * @see Identifiable#getId()
	 */
	public Long getId() {
		return super.getWrapped().getId();
	}

	/**
	 * @see Identifiable#setId(Long)
	 */
	public void setId(Long id) {
		super.getWrapped().setId(id);
	}

	/**
	 * @see Product#getName()
	 */
	public String getName() {
		return super.getWrapped().getName();
	}

	/**
	 * @see Product#setName(String)
	 */
	public void setName(String name) {
		super.getWrapped().setName(name);
	}

	/**
	 * @see Product#getDescription()
	 */
	public String getDescription() {
		return super.getWrapped().getDescription();
	}

	/**
	 * @see Product#setDescription(String)
	 */
	public void setDescription(String description) {
		super.getWrapped().setDescription(description);
	}

	/**
	 * @see Product#getPrice()
	 */
	public Double getPrice() {
		return super.getWrapped().getPrice();
	}

	/**
	 * @see Product#setPrice(Double)
	 */
	public void setPrice(Double price) {
		super.getWrapped().setPrice(price);
	}

	/**
	 * @see Product#getCategory()
	 */
	public String getCategory() {
		return super.getWrapped().getCategory();
	}

	/**
	 * @see Product#setCategory(String)
	 */
	public void setCategory(String category) {
		super.getWrapped().setCategory(category);
	}
}

package com.lm25ttd.xyinc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * This class represents the Product.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
@Entity
@Table(name = "PRODUCT")
@GenericGenerator(name = "ID", strategy = "native")
public class Product extends Identified<Long> {

	@Column(name = "NAME", unique = true, nullable = false)
	private String name;

	@Column(name = "DESCRIPTION", unique = false)
	@Lob
	private String description;

	@Column(name = "PRICE", unique = false, nullable = false)
	private Double price;

	@Column(name = "CATEGORY", unique = false, nullable = false)
	private String category;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}

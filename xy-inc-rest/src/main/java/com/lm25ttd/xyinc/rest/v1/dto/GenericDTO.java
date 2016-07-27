package com.lm25ttd.xyinc.rest.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Generic Data Type Object that wraps other classes.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
public class GenericDTO<T> {
	
	@JsonIgnore
	private T wrapped;

	/**
	 * Creates an instance of GenericDTO.
	 */
	public GenericDTO(T wrapped) {
		this.wrapped = wrapped;
	}

	/**
	 * @return the wrapped
	 */
	public T getWrapped() {
		return this.wrapped;
	}

	/**
	 * @param wrapped
	 *            the wrapped to set
	 */
	public void setWrapped(T wrapped) {
		this.wrapped = wrapped;
	}
}

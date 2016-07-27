package com.lm25ttd.xyinc.rest.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lm25ttd.xyinc.core.exceptions.XYIncException;
import com.lm25ttd.xyinc.core.exceptions.XYIncUnexpectedException;;

/**
 * Data Type Object for a generic error response.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {

	private final String code;
	private final String message;

	public ErrorResponse(Throwable exception) {
		this.code = exception.getClass().getSimpleName();
		this.message = exception.getMessage();
	}

	public ErrorResponse(XYIncException exception) {
		this.code = exception.getCode();
		this.message = exception.getMessage();
	}

	public ErrorResponse(XYIncUnexpectedException exception) {
		this.code = exception.getCode();
		this.message = exception.getMessage();
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

}
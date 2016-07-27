package com.lm25ttd.xyinc.rest.v1.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonParseException;
import com.lm25ttd.xyinc.core.exceptions.AccountLoginException;
import com.lm25ttd.xyinc.core.exceptions.EntityAlreadyExistsException;
import com.lm25ttd.xyinc.core.exceptions.EntityNotFoundException;
import com.lm25ttd.xyinc.core.exceptions.XYIncException;
import com.lm25ttd.xyinc.core.exceptions.XYIncUnexpectedException;
import com.lm25ttd.xyinc.rest.v1.dto.ErrorResponse;

/**
 * Implementation of REST API exception handler. This class binds Java
 * exceptions to one HTTP Status.
 *
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
@ControllerAdvice
public class XYIncExceptionHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(XYIncExceptionHandler.class);

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler({ Throwable.class, XYIncUnexpectedException.class })
	public void handleInternalServerError(Throwable t) {
		LOGGER.error("Internal server error", t);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	@ExceptionHandler({ EntityNotFoundException.class })
	public ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
		LOGGER.debug(e.getMessage());
		return new ErrorResponse(e);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.CONFLICT) // 409
	@ExceptionHandler({ EntityAlreadyExistsException.class })
	public ErrorResponse handleEntityAlreadyExistsException(XYIncException e) {
		LOGGER.debug(e.getMessage());
		return new ErrorResponse(e);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
	@ExceptionHandler({ AccessDeniedException.class, AccountLoginException.class })
	public ErrorResponse handleAccessDeniedException(Throwable e) {
		LOGGER.debug(e.getMessage());
		return new ErrorResponse(e);
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	@ExceptionHandler({ IllegalArgumentException.class, JsonParseException.class, HttpMessageNotReadableException.class })
	public ErrorResponse handleBadRequestException(Throwable e) {
		LOGGER.debug(e.getMessage());
		return new ErrorResponse(e);
	}
}

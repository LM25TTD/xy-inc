package com.lm25ttd.xyinc.core.exceptions;

/**
 * Thrown when an entity already exists in system.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
public class EntityAlreadyExistsException extends XYIncException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates the exception with a default message.
	 */
	public EntityAlreadyExistsException() {
		super("exception.entity.already.exists");
	}

	/**
	 * Creates the exception with a default message.
	 *
	 * @param entityId
	 *            The entity id.
	 * @param entityType
	 *            The entity class.
	 */
	public EntityAlreadyExistsException(Object entityId, Class<?> entityType) {
		super("exception.entity.already.exists.with.id", entityType.getSimpleName(), entityId);
	}

	/**
	 * Creates the exception with a custom message.
	 *
	 * @param message
	 *            The custom message or message key
	 * @param args
	 *            the message arguments, if any
	 */
	public EntityAlreadyExistsException(String message, Object... args) {
		super(message, args);
	}
}

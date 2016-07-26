package com.lm25ttd.xyinc.core.exceptions;

/**
 * Thrown when an Id update operation is attempt.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
public class IdentifierUpdatedException extends XYIncUnexpectedException {

	private static final long serialVersionUID = 1L;

	public IdentifierUpdatedException() {
		super("exception.identifier.update");
	}
}

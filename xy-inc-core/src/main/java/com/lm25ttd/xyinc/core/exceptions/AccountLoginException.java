package com.lm25ttd.xyinc.core.exceptions;

/**
 * Thrown when an entity is not found in system.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
public class AccountLoginException extends XYIncException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates the exception with a default message.
	 */
	public AccountLoginException() {
		super("exception.account.login");
	}	
}

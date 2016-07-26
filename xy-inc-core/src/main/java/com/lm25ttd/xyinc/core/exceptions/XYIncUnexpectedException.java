package com.lm25ttd.xyinc.core.exceptions;

import com.lm25ttd.xyinc.core.i18n.I18nService;

/**
 * The parent of all runtime exceptions, usually bugs.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
public class XYIncUnexpectedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates an instance of XYIncUnexpectedException.
	 */
	public XYIncUnexpectedException() {
		super(I18nService.translate("exception.unexpected"));
	}

	/**
	 * Creates an instance of XYIncUnexpectedException.
	 *
	 * @param message
	 *            An i18n messageKey or an integral message
	 * @param args
	 *            Arguments of an i18n messageKey
	 */
	public XYIncUnexpectedException(String message, Object... args) {
		super(I18nService.translate(message, args));
	}

	/**
	 * Creates an instance of XYIncUnexpectedException.
	 *
	 * @param message
	 *            An i18n messageKey or an integral message
	 * @param cause
	 *            The exception that caused this exception
	 * @param args
	 *            Arguments of an i18n messageKey
	 */
	public XYIncUnexpectedException(String message, Throwable cause, Object... args) {
		super(I18nService.translate(message, args) + ": " + cause.getMessage(), cause);
	}

	/**
	 * Creates an instance of XYIncUnexpectedException.
	 *
	 * @param cause
	 *            The exception that caused this exception
	 */
	public XYIncUnexpectedException(Throwable cause) {
		super(I18nService.translate("exception.unexpected") + ": " + cause.getMessage(), cause);
	}

	/**
	 * Returns the error code.
	 */
	public String getCode() {
		return this.getClass().getSimpleName();
	}
}

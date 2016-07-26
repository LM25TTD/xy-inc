package com.lm25ttd.xyinc.core.utils;

import java.util.Collection;

import com.lm25ttd.xyinc.core.i18n.I18nService;

/**
 * General utilities.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
public class Utils {

	public static final String EMPTY_STRING = "";

	/**
	 * Compares two objects for equality
	 */
	public static boolean equals(Object a, Object b) {
		if ((a == null) || (b == null)) {
			return a == b;
		}
		if (a.getClass() != b.getClass()) {
			return false;
		}
		return a.equals(b);
	}

	/**
	 * Validates if a collection is null or empty
	 */
	public static boolean isNullOrEmpty(Collection<?> collection) {
		return (collection == null) || collection.isEmpty();
	}

	/**
	 * Validates if an array is null or empty
	 */
	public static boolean isNullOrEmpty(Object[] array) {
		return (array == null) || (array.length == 0);
	}

	/**
	 * Validates if a string is null or empty
	 */
	public static boolean isNullOrEmpty(String string) {
		return (string == null) || string.isEmpty();
	}

	/**
	 * Throws IllegalArgumentException if argument is null
	 */
	public static void cannotBeNull(Object argumentValue, String argumentName) {
		if (argumentValue == null) {
			String message = I18nService.translate("exception.null.argument", argumentName);
			throw new IllegalArgumentException(message);
		}
	}
}

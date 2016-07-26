package com.lm25ttd.xyinc.core.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Methods and declarations for I18nService.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
public class I18nService {

	private static final Logger LOGGER = LoggerFactory.getLogger(I18nService.class);
	private static final Locale FALLBACK_LOCALE = new Locale.Builder().setLanguage("pt").setRegion("BR").build();

	private static String baseName = "i18n/messages";

	/**
	 * Retrieves the localized messages.
	 *
	 * @param locale
	 *            The locale of the file.
	 * @return a resource bundle for the given base name and locale
	 */
	private static ResourceBundle getMessages(Locale locale) {

		try {
			return ResourceBundle.getBundle(baseName, locale);
		} catch (MissingResourceException e) {
			String msg = "Cannot find messages: " + baseName + "_" + locale;
			LOGGER.error(msg);
			throw new RuntimeException(msg, e);
		}

	}

	/**
	 * Allows changing the set of message files to consider; useful for tests.
	 * 
	 * @param value
	 *            The bundle base name
	 */
	public static void setBaseName(String value) {
		baseName = value;
	}

	/**
	 * If a messageKey is provided, the key is translated in a internationalized
	 * message based on given locale. If the key cannot be found, this method
	 * understands that it is already a message which is returned without
	 * translation.
	 *
	 * @param messageKey
	 *            The key or the message itself.
	 * @param locale
	 *            The language and region
	 * @param args
	 *            Values of arguments to be replaced in the message body.
	 * @return A message
	 */
	public static String translate(String messageKey, Locale locale, Object... args) {

		try {
			String message = getMessages(locale).getString(messageKey);
			if (args.length > 0) {
				MessageFormat formatter = new MessageFormat("");
				formatter.setLocale(locale);
				formatter.applyPattern(message);
				message = formatter.format(args);
			}
			return message;
		} catch (MissingResourceException e) {
			return messageKey;
		}

	}

	/**
	 * An alias to translate an exception message.
	 *
	 * @param messageKey
	 *            The key or the message itself.
	 * @param args
	 *            Values of arguments to be replaced in the message body.
	 * @return A message
	 */
	public static String translate(String messageKey, Object... args) {
		return translate(messageKey, FALLBACK_LOCALE, args);
	}

}

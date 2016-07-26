package com.lm25ttd.xyinc.core.i18n;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Integration tests for translation tool.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
public class I18nServiceIT {

	@BeforeClass
	public static void setUp() {
		I18nService.setBaseName("i18n/messages_test");
	}

	@Test
	public void testTranslateWorksSuccess() {
		String messageKey = "test.message";
		String expectedMessage = "Olá mundo!!";
		String actualMessage = I18nService.translate(messageKey);

		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	public void testTranslateUnknowMessageShouldReturnKey() {
		String messageKey = "test.messagge";
		String actualMessage = I18nService.translate(messageKey);	
		assertEquals(messageKey, actualMessage);
	}

}

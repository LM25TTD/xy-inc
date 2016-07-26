package com.lm25ttd.xyinc.core.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.lm25ttd.xyinc.core.exceptions.XYIncException;
import com.lm25ttd.xyinc.core.i18n.I18nService;

/**
 * Integration tests for system exceptions message translation.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
public class XYIncExceptionIT {

	@BeforeClass
	public static void setUp() {
		I18nService.setBaseName("i18n/messages_test");
	}

	@Test
	public void testGetMessageIsTranslated() {
		XYIncException e = new XYIncException("test.message");
		String expectedMessage = "Olá mundo!!";
		assertEquals(expectedMessage, e.getMessage());
	}
}

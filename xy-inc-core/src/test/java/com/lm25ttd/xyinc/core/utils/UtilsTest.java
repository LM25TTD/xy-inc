package com.lm25ttd.xyinc.core.utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Unit tests for Utils.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
public class UtilsTest {

	@Test
	public void testCollectionIsNullOrEmpty() {
		boolean expected = true;

		List<String> nullList = null;
		List<String> emptyList = new ArrayList<String>();

		assertEquals(expected, Utils.isNullOrEmpty(nullList));
		assertEquals(expected, Utils.isNullOrEmpty(emptyList));

		expected = false;
		emptyList.add("test");

		// not empty now
		assertEquals(expected, Utils.isNullOrEmpty(emptyList));
	}

	@Test
	public void testArrayIsNullOrEmpty() {
		boolean expected = true;

		String[] nullArray = null;
		String[] emptyArray = new String[0];

		assertEquals(expected, Utils.isNullOrEmpty(nullArray));
		assertEquals(expected, Utils.isNullOrEmpty(emptyArray));

		expected = false;
		emptyArray = new String[1];
		emptyArray[0] = "test";

		// not empty now
		assertEquals(expected, Utils.isNullOrEmpty(emptyArray));
	}

	@Test
	public void testStringIsNullOrEmpty() {
		boolean expected = true;

		String nullStr = null;
		String emptyStr = Utils.EMPTY_STRING;

		assertEquals(expected, Utils.isNullOrEmpty(nullStr));
		assertEquals(expected, Utils.isNullOrEmpty(emptyStr));

		expected = false;
		emptyStr = "test";

		// not empty now
		assertEquals(expected, Utils.isNullOrEmpty(emptyStr));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCannotBeNullArgShouldThrowIllegalArgumentException() {
		String nullArg = null;
		Utils.cannotBeNull(nullArg, "nullArg");
	}

	@Test
	public void testEqualsWorks() {

		Number longNumber1 = new Long(1);
		Number longNumber2 = new Long(1);

		boolean actual = Utils.equals(longNumber1, longNumber2);
		boolean expected = true;

		assertEquals(expected, actual);

	}
	
	@Test
	public void testNotEqualsDifferentClassesWorks() {

		Long longNumber1 = new Long(1);
		Double longNumber2 = new Double(1);

		boolean actual = Utils.equals(longNumber1, longNumber2);
		boolean expected = false;

		assertEquals(expected, actual);

	}
	
	@Test
	public void testNullEqualsWorks() {

		Number longNumber1 = null;
		Number longNumber2 = null;

		boolean actual = Utils.equals(longNumber1, longNumber2);
		boolean expected = true;

		assertEquals(expected, actual);
		
		expected = false;
		longNumber2 = new Long(1);
		actual = Utils.equals(longNumber1, longNumber2);

		assertEquals(expected, actual);
	
		longNumber1 = new Long(1);
		longNumber2 = null;
		actual = Utils.equals(longNumber1, longNumber2);

		assertEquals(expected, actual);
		
	}
}

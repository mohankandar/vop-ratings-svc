/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wynd.vop.ratingssvc.exception;

import com.wynd.vop.framework.messages.MessageKeys;
import com.wynd.vop.framework.messages.MessageSeverity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author rthota
 */
public class RatingsSvcServiceExceptionTest {
	RatingsSvcServiceException instance;

	private static final String NAME = "NO_KEY";
	private static final String MESSAGE = "NO_KEY";

	@Before
	public void setUp() {
		instance = new RatingsSvcServiceException(MessageKeys.NO_KEY, MessageSeverity.ERROR, null);
	}

	/**
	 * Test of getSeverity method, of class RatingsSvcServiceException.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetSeverity() {
		System.out.println("getSeverity");
		MessageSeverity expResult = MessageSeverity.ERROR;
		MessageSeverity result = instance.getExceptionData().getSeverity();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getKey method, of class RatingsSvcServiceException.
	 */
	@Test
	public void testGetKey() {
		System.out.println("getKey");
		String expResult = NAME;
		String result = instance.getExceptionData().getKey();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getMessage method, of class RatingsSvcServiceException.
	 */
	@Test
	public void testGetMessage() {
		System.out.println("getMessage");
		String expResult = MESSAGE;
		String result = instance.getMessage();
		assertEquals(expResult, result);

	}
}

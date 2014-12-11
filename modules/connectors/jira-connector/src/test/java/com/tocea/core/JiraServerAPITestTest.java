/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.core;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

/**
 *
 * @author rgalerme
 */
public class JiraServerAPITestTest {

	public JiraServerAPITestTest() {
	}


	@Test
	public void testSomeMethod() {
		try {
			final JiraServerAPITest js = new JiraServerAPITest();
			js.test();
		} catch (final ParseException ex) {
			Logger.getLogger(JiraServerAPITestTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}

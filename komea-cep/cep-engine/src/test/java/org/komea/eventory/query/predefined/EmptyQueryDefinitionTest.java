package org.komea.eventory.query.predefined;

import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.formula.CountFormula;

public class EmptyQueryDefinitionTest {

	@Test
	public void testGetFilterDefinitions() throws Exception {

		Assert.assertTrue(new EmptyQueryDefinition().getFilterDefinitions().isEmpty());
	}

	@Test
	public void testGetFormula() throws Exception {

		Assert.assertTrue(new EmptyQueryDefinition().getFormula() instanceof CountFormula);
	}

}

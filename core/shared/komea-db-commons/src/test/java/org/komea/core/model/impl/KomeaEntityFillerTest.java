/**
 *
 */
package org.komea.core.model.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.core.schema.IEntityType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author sleroy
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class KomeaEntityFillerTest {
	@Mock
	private IEntityType entityType;

	@Mock
	private OKomeaModelFactory oKomeaModelFactory;
	@InjectMocks
	private KomeaEntityFiller komeaEntityFiller;

	/**
	 * Test method for
	 * {@link org.komea.core.model.impl.KomeaEntityFiller#put(java.lang.Object)}
	 * .
	 */
	@Test
	public final void testPut() throws Exception {
		// TODO
		throw new RuntimeException("not yet implemented");
	}

}

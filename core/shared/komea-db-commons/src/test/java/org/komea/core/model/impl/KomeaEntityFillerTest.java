/**
 *
 */
package org.komea.core.model.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.schema.IEntityType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author sleroy
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class KomeaEntityFillerTest {
	public static class HumanDto {

		private String	name;
		private int		age;

		public HumanDto() {
			super();
		}

		public int getAge() {
			return this.age;
		}

		public String getName() {
			return this.name;
		}

		public void setAge(final int _age) {
			this.age = _age;
		}

		public void setName(final String _name) {
			this.name = _name;
		}

	}

	@Mock
	private IEntityType	       entityType;
	@Mock
	private OKomeaModelFactory	oKomeaModelFactory;

	@InjectMocks
	private KomeaEntityFiller	komeaEntityFiller;

	/**
	 * Test method for
	 * {@link org.komea.core.model.impl.KomeaEntityFiller#put(java.lang.Object)}
	 * .
	 */
	@Test
	public final void testPut() throws Exception {
		final HumanDto entity = new HumanDto();
		entity.setName("sylvain");
		entity.setAge(31);
		final IKomeaEntity komeaEntity = mock(IKomeaEntity.class);
		when(this.oKomeaModelFactory.newInstance(this.entityType)).thenReturn(komeaEntity);
		this.komeaEntityFiller.put(entity);
		verify(komeaEntity, Mockito.times(1)).set("name", "sylvain");
		verify(komeaEntity, Mockito.times(1)).set("age", 31);
	}

}

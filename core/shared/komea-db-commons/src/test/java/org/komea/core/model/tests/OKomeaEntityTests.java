package org.komea.core.model.tests;

import org.junit.Test;
import org.komea.core.model.impl.OKomeaEntity;
import org.komea.core.schema.IEntityType;

import com.tinkerpop.blueprints.impls.orient.OrientVertex;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OKomeaEntityTests {

	@Test
	public void equalsTest() {
		IEntityType type = mock(IEntityType.class);
		OrientVertex v1 = mock(OrientVertex.class);
		OrientVertex v2 = mock(OrientVertex.class);
		OKomeaEntity e1 = new OKomeaEntity(type, v1);
		OKomeaEntity e2 = new OKomeaEntity(type, v1);
		OKomeaEntity e3 = new OKomeaEntity(type, v2);
		assertTrue(e1.equals(e2));
		assertFalse(e1.equals(e3));
	}

	@Test
	public void hashCodeTest() {
		IEntityType type = mock(IEntityType.class);
		OrientVertex v1 = mock(OrientVertex.class);
		OrientVertex v2 = mock(OrientVertex.class);
		OKomeaEntity e1 = new OKomeaEntity(type, v1);
		OKomeaEntity e2 = new OKomeaEntity(type, v1);
		OKomeaEntity e3 = new OKomeaEntity(type, v2);
		assertTrue(e1.hashCode() == e2.hashCode());
		assertFalse(e1.hashCode() == e3.hashCode());
	}
	
	@Test
	public void toStringTest(){
		IEntityType type = mock(IEntityType.class);
		OrientVertex v1 = mock(OrientVertex.class);
		OKomeaEntity e1 = new OKomeaEntity(type, v1);
		e1.toString();
	}

}

package org.komea.core.schema.tests;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;

import org.junit.Test;
import org.komea.core.schema.IReference;
import org.komea.core.schema.IType;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.ReferenceKind;
import org.komea.core.schema.impl.Reference;
public class ReferenceTests {

	@Test
	public void testAggregation(){
		IReference ref = new Reference("ref", mock(IType.class));
		assertFalse(ref.isAggregation());
		ref.setKind(ReferenceKind.AGGREGATION);
		assertTrue(ref.isAggregation());
	}
	

	@Test
	public void testContainment(){
		IReference ref = new Reference("ref", mock(IType.class));
		assertFalse(ref.isContainment());
		ref.setKind(ReferenceKind.CONTAINMENT);
		assertTrue(ref.isContainment());
	}
	


	@Test
	public void testArity(){
		IReference ref = new Reference("ref", mock(IType.class));
		assertFalse(ref.isMany());
		ref.setArity(ReferenceArity.MANY);
		assertTrue(ref.isMany());
	}
	
	
	@Test
	public void testMandatory(){
		IReference ref = new Reference("ref", mock(IType.class));
		assertFalse(ref.isMandatory());
		ref.enableMandatory();
		assertTrue(ref.isMandatory());
		ref.disableMandatory();
		assertFalse(ref.isMandatory());
	}
	
	@Test
	public void testUnique(){
		IReference ref = new Reference("ref", mock(IType.class));
		assertFalse(ref.isUnique());
		ref.enableUnique();
		assertTrue(ref.isUnique());
		ref.disableUnique();
		assertFalse(ref.isUnique());
	}
	
	
	@Test
	public void testIndexed(){
		IReference ref = new Reference("ref", mock(IType.class));
		assertFalse(ref.isIndexed());
		ref.enableIndexation();
		assertTrue(ref.isIndexed());
		ref.disableIndexation();
		assertFalse(ref.isIndexed());
	}
}

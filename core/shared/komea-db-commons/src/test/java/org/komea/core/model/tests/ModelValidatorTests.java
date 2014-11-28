package org.komea.core.model.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.validation.KomeaModelValidator;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IReference;

import com.google.common.collect.Lists;

public class ModelValidatorTests {

	@Test
	public void testManyRefefenceValidation() {

		// create a mocked entity type for a company
		final IEntityType person = mock(IEntityType.class);
		final IEntityType computer = mock(IEntityType.class);

		final IEntityType company = mock(IEntityType.class);
		final IReference companyMembers = mock(IReference.class);
		when(companyMembers.getType()).thenReturn(person);
		when(companyMembers.isMany()).thenReturn(true);
		when(companyMembers.isContainment()).thenReturn(true);

		final IKomeaEntity personMock = mock(IKomeaEntity.class);
		when(personMock.getType()).thenReturn(person);
		final IKomeaEntity computerMock = mock(IKomeaEntity.class);
		when(computerMock.getType()).thenReturn(computer);

		final List<IKomeaEntity> validEntites = Lists.newArrayList();
		validEntites.add(personMock);

		final List<IKomeaEntity> notValidEntites = Lists
				.newArrayList(validEntites);
		notValidEntites.add(computerMock);

		// if the members of a company is containing some computers
		final IKomeaEntity notValidCompanyMock = mock(IKomeaEntity.class);
		when(notValidCompanyMock.getType()).thenReturn(company);
		when(notValidCompanyMock.value(companyMembers)).thenReturn(
				notValidEntites);
		when(notValidCompanyMock.getType().getProperties()).thenReturn(
				Lists.newArrayList(companyMembers));

		// then validator should not validate the company
		Assert.assertFalse(KomeaModelValidator.validate(notValidCompanyMock));

		// if the members of a company contains some computers
		final IKomeaEntity validCompanyMock = mock(IKomeaEntity.class);
		when(validCompanyMock.getType()).thenReturn(company);
		when(validCompanyMock.value(companyMembers)).thenReturn(validEntites);
		when(validCompanyMock.getType().getProperties()).thenReturn(
				Lists.newArrayList(companyMembers));

		// then validator should not validate the company
		Assert.assertTrue(KomeaModelValidator.validate(validCompanyMock));
	}

}

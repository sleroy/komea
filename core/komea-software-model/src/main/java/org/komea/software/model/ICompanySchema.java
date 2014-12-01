package org.komea.software.model;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;

public interface ICompanySchema extends ICompanySchemaInformations {

	IEntityType getHumanType();

	String getName();

	IKomeaSchema getSchema();

}

package org.komea.software.model;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;

public interface ICompanySchema extends IKomeaSchema,
ICompanySchemaInformations {

	IEntityType getHumanType();

}

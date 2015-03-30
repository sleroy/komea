package org.komea.software.model.impl;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.komea.core.schema.impl.SchemaBuilder;
import org.komea.software.model.ICompanySchema;
import org.komea.software.model.ICompanySchemaInformations;

/**
 * This class defines a minimal company schema ready to use.
 *
 * @author sleroy
 *
 */
public class MinimalCompanySchema implements ICompanySchema {

	private final IKomeaSchema	newSchema;

	private IEntityType	       humanType;

	public MinimalCompanySchema() {
		final KomeaSchemaFactory komeaSchemaFactory = new KomeaSchemaFactory();
		final SchemaBuilder builder = komeaSchemaFactory.newBuilder(ICompanySchemaInformations.COMPANY_SCHEMA);

		this.humanType = builder.newEntity("Human").addStringProperty("login").addStringProperty("firstName")
		        .addStringProperty("lastName").addStringProperty("email").addProperty("creation_date", Primitive.DATE)
		        .build();

		this.newSchema = builder.build();

	}

	@Override
	public IEntityType getHumanType() {
		return this.humanType;
	}

	@Override
	public String getName() {

		return this.newSchema.getName();
	}

	@Override
	public IKomeaSchema getSchema() {
		return this.newSchema;
	}

	public void setHumanType(final IEntityType _humanType) {
		this.humanType = _humanType;
	}

}

package org.komea.software.model.impl;

import java.util.List;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IPrimitiveType.Primitive;
import org.komea.core.schema.SchemaBuilder;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.komea.software.model.ICompanySchema;
import org.komea.software.model.ICompanySchemaInformations;

/**
 * This class defines a minimal company schema ready to use.
 * @author sleroy
 *
 */
public class MinimalCompanySchema implements ICompanySchema {

	private final IKomeaSchema newSchema;

	private IEntityType humanType;

	public MinimalCompanySchema() {
		final KomeaSchemaFactory komeaSchemaFactory = new KomeaSchemaFactory();
		final SchemaBuilder builder = komeaSchemaFactory
				.newBuilder(ICompanySchemaInformations.COMPANY_SCHEMA);

		this.humanType = builder.newEntity("Human").addStringProperty("login")
				.addStringProperty("firstName").addStringProperty("lastName")
				.addStringProperty("email")
				.addProperty("creation_date", Primitive.DATE).build();

		this.newSchema = builder.build();

	}

	@Override
	public void addType(final IEntityType _type) {
		this.newSchema.addType(_type);

	}

	@Override
	public IEntityType findType(final String _name) {

		return this.newSchema.findType(_name);
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
	public List<IEntityType> getTypes() {

		return this.newSchema.getTypes();
	}

	public void setHumanType(final IEntityType _humanType) {
		this.humanType = _humanType;
	}

}

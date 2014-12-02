package org.komea.connectors.bugzilla.schema.impl;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.SchemaBuilder;
import org.komea.software.model.ICompanySchema;

/**
 * This class defines a minimal company schema ready to use.
 *
 * @author sleroy
 *
 */
public class BugzillaSchemaBuilder {

	private final IEntityType	bugzillaProduct;

	private final IEntityType	bugzillaProductVersion;

	private final IEntityType	bugzillaProductComponent;

	private final IEntityType	bugzillaOS;

	private final IEntityType	bugzillaPlatform;

	private final IKomeaSchema	schema;

	public BugzillaSchemaBuilder(final ICompanySchema _schema) {

		final SchemaBuilder schemaBuilder = new SchemaBuilder(_schema.getSchema());

		this.bugzillaProduct = schemaBuilder.newEntity("BugzillaProduct").addStringProperty("name")
		        .addIntegerProperty("productId").addStringProperty("description").build();

		this.bugzillaProductVersion = schemaBuilder.newEntity("BugzillaProductVersion").addStringProperty("name")
		        .addIntegerProperty("versionId").build();

		this.bugzillaProductComponent = schemaBuilder.newEntity("BugzillaComponent").addStringProperty("name").build();

		this.bugzillaOS = schemaBuilder.newEntity("BugzillaOperationSystem").addStringProperty("name").build();

		this.bugzillaPlatform = schemaBuilder.newEntity("BugzillaPlatform").addStringProperty("name").build();

		schemaBuilder.entityContainsMany("versions", this.bugzillaProduct, this.bugzillaProductVersion);
		schemaBuilder.entityAggregatesMany("components", this.bugzillaProduct, this.bugzillaProductComponent);
		schemaBuilder.entityAggregatesMany("owned_by", this.bugzillaProductComponent, this.bugzillaProduct);
		schemaBuilder.entityAggregatesMany("platforms_supported", this.bugzillaProduct, this.bugzillaPlatform);
		schemaBuilder.entityAggregatesMany("os_supported", this.bugzillaProduct, this.bugzillaOS);
		schemaBuilder.entityAggregatesMany("platforms_supported", this.bugzillaProductComponent, this.bugzillaPlatform);
		schemaBuilder.entityAggregatesMany("os_supported", this.bugzillaProductComponent, this.bugzillaOS);
		this.schema = _schema.getSchema();

	}

	public IEntityType getBugzillaOS() {
		return this.bugzillaOS;
	}

	public IEntityType getBugzillaPlatform() {
		return this.bugzillaPlatform;
	}

	public IEntityType getBugzillaProduct() {
		return this.bugzillaProduct;
	}

	public IEntityType getBugzillaProductComponent() {
		return this.bugzillaProductComponent;
	}

	public IEntityType getBugzillaProductVersion() {
		return this.bugzillaProductVersion;
	}

	public IKomeaSchema getSchema() {

		return this.schema;
	}

}

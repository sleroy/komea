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
	
	private final IEntityType	user;

	private final IKomeaSchema	schema;

	public BugzillaSchemaBuilder(final ICompanySchema _schema) {

		final SchemaBuilder schemaBuilder = new SchemaBuilder(_schema.getSchema());

		/*
		 * Resources
		 */
		this.bugzillaProduct = schemaBuilder.newEntity("BugzillaProduct").addStringProperty("name")
		        .addIntegerProperty("productId").addStringProperty("description").build();

		this.bugzillaProductVersion = schemaBuilder.newEntity("BugzillaProductVersion").addStringProperty("name")
		        .addIntegerProperty("versionId").build();

		this.bugzillaProductComponent = schemaBuilder.newEntity("BugzillaComponent").addStringProperty("name").build();

		this.bugzillaOS = schemaBuilder.newEntity("BugzillaOperationSystem").addStringProperty("name").build();

		this.bugzillaPlatform = schemaBuilder.newEntity("BugzillaPlatform").addStringProperty("name").build();
		
		/*
		 * Persons
		 */
		this.user = schemaBuilder.newEntity("BugzillaUser").addStringProperty("bz_email").setExtends(_schema.getHumanType()).build();

		
		schemaBuilder.entityContainsMany("versions", this.bugzillaProduct, this.bugzillaProductVersion);
		schemaBuilder.entityRefersMany("components", this.bugzillaProduct, this.bugzillaProductComponent);
		schemaBuilder.entityRefersMany("owned_by", this.bugzillaProductComponent, this.bugzillaProduct);
		schemaBuilder.entityRefersMany("platforms_supported", this.bugzillaProduct, this.bugzillaPlatform);
		schemaBuilder.entityRefersMany("os_supported", this.bugzillaProduct, this.bugzillaOS);
		schemaBuilder.entityRefersMany("platforms_supported", this.bugzillaProductComponent, this.bugzillaPlatform);
		schemaBuilder.entityRefersMany("os_supported", this.bugzillaProductComponent, this.bugzillaOS);
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
	
	public IEntityType getUser() {
		return this.user;
	}

	public IKomeaSchema getSchema() {

		return this.schema;
	}

}

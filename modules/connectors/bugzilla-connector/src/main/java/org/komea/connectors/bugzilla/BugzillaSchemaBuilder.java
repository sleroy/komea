package org.komea.connectors.bugzilla;

import org.komea.core.schema.IEntityType;
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

	public BugzillaSchemaBuilder(final ICompanySchema _schema) {

		final SchemaBuilder schemaBuilder = new SchemaBuilder(_schema.getSchema());

		this.bugzillaProductVersion = schemaBuilder.newEntity("BugzillaProductVersion").addStringProperty("name")
		        .addIntegerProperty("id").build();

		this.bugzillaProduct = schemaBuilder.newEntity("BugzillaProduct").addStringProperty("name")
		        .addIntegerProperty("id").addStringProperty("description").build();

		this.bugzillaProductComponent = schemaBuilder.newEntity("BugzillaComponent").addStringProperty("name").build();

		this.bugzillaOS = schemaBuilder.newEntity("BugzillaOperationSystem").addStringProperty("name").build();

		this.bugzillaPlatform = schemaBuilder.newEntity("BugzillaPlatform").addStringProperty("name").build();

		schemaBuilder.entity_contains_many("versions", this.bugzillaProduct, this.bugzillaProductVersion);
		schemaBuilder.entity_refers_many("components", this.bugzillaProduct, this.bugzillaProductComponent);
		schemaBuilder.entity_refers_many("owned_by", this.bugzillaProductComponent, this.bugzillaProduct);
		schemaBuilder.entity_refers_many("platforms_supported", this.bugzillaProduct, this.bugzillaPlatform);
		schemaBuilder.entity_refers_many("os_supported", this.bugzillaProduct, this.bugzillaOS);
		schemaBuilder.entity_refers_many("platforms_supported", this.bugzillaProductComponent, this.bugzillaPlatform);
		schemaBuilder.entity_refers_many("os_supported", this.bugzillaProductComponent, this.bugzillaOS);

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

}

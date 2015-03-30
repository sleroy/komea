
package org.komea.connectors.bugzilla.schema.impl;


import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.impl.SchemaBuilder;
import org.komea.software.model.ICompanySchema;

/**
 * This class defines a minimal schema for bugzilla informations.
 *
 * @author sleroy
 */
public class BugzillaSchemaBuilder
{
    
    public static final String BUGZILLA_USER             = "BugzillaUser";
    
    public static final String BUGZILLA_PLATFORM         = "BugzillaPlatform";
    
    public static final String BUGZILLA_OPERATION_SYSTEM = "BugzillaOperationSystem";
    
    public static final String BUGZILLA_COMPONENT        = "BugzillaComponent";
    
    public static final String BUGZILLA_PRODUCT_VERSION  = "BugzillaProductVersion";
    
    public static final String BUGZILLA_PRODUCT          = "BugzillaProduct";
    
    private final IEntityType  bugzillaProduct;
    
    private final IEntityType  bugzillaProductVersion;
    
    private final IEntityType  bugzillaProductComponent;
    
    private final IEntityType  bugzillaOS;
    
    private final IEntityType  bugzillaPlatform;
    
    private final IEntityType  user;
    
    private final IKomeaSchema schema;
    
    public BugzillaSchemaBuilder(final ICompanySchema _schema) {
    
        final SchemaBuilder schemaBuilder = new SchemaBuilder(_schema.getSchema());
        
        /*
         * Resources
         */
        this.bugzillaProduct = schemaBuilder.newEntity(BUGZILLA_PRODUCT).addStringProperty("name").addIntegerProperty("productId")
                .addStringProperty("description").build();
        
        this.bugzillaProductVersion = schemaBuilder.newEntity(BUGZILLA_PRODUCT_VERSION).addStringProperty("name")
                .addIntegerProperty("versionId").build();
        
        this.bugzillaProductComponent = schemaBuilder.newEntity(BUGZILLA_COMPONENT).addStringProperty("name").build();
        
        this.bugzillaOS = schemaBuilder.newEntity(BUGZILLA_OPERATION_SYSTEM).addStringProperty("name").build();
        
        this.bugzillaPlatform = schemaBuilder.newEntity(BUGZILLA_PLATFORM).addStringProperty("name").build();
        
        /*
         * Persons
         */
        this.user = schemaBuilder.newEntity(BUGZILLA_USER).addStringProperty("bz_email").setExtends(_schema.getHumanType()).build();
        
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
    
    public IEntityType getUser() {
    
        return this.user;
    }
    
    public IKomeaSchema getSchema() {
    
        return this.schema;
    }
    
}

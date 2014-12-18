
package org.komea.connectors.jira.schema;


import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.impl.SchemaBuilder;
import org.komea.software.model.ICompanySchema;

public class JiraSchema
{
    
    private final IEntityType  project;
    private final IEntityType  user;
    private final IEntityType  component;
    private final IEntityType  version;
    private final IEntityType  roles;
    
    public static final String JIRAPROEJCT   = "Project";
    public static final String JIRAUSER      = "User";
    public static final String JIRACOMPONENT = "Component";
    public static final String JIRAROLE      = "Role";
    public static final String JIRAVERSION   = "Version";
    
    private final IKomeaSchema schema;
    
    public JiraSchema(final ICompanySchema _schema) {
    
        
        final SchemaBuilder schemaBuilder = new SchemaBuilder(_schema.getSchema());
        
        this.version = schemaBuilder.newEntity(JIRAVERSION).addStringProperty("name").addStringProperty("releaseDate")
                .addStringProperty("description").addBooleanProperty("archived").addBooleanProperty("released").build();
        
        this.project = schemaBuilder.newEntity(JIRAPROEJCT).addStringProperty("key").addStringProperty("name")
                .addStringProperty("description").addStringProperty("assigneeType").build();
        
        this.user = schemaBuilder.newEntity(JIRAUSER).addStringProperty("name").addStringProperty("email").addStringProperty("displayName")
                .addBooleanProperty("active").setExtends(_schema.getHumanType()).build();
        
        this.component = schemaBuilder.newEntity(JIRACOMPONENT).addStringProperty("name").addBooleanProperty("isAssignedTypeValid")
                .addStringProperty("description").build();
        
        this.roles = schemaBuilder.newEntity(JIRAROLE).addStringProperty("name").addStringProperty("roleId").build();
        
        schemaBuilder.entityAggregatesOne("lead", this.project, this.user);
        schemaBuilder.entityContainsMany("versions", this.project, this.version);
        schemaBuilder.entityAggregatesMany("components", this.project, this.component);
        schemaBuilder.entityAggregatesMany("roles", this.project, this.roles);
        
        this.schema = _schema.getSchema();
        
    }
    
    public IEntityType getProject() {
    
        return this.project;
    }
    
    public IEntityType getUser() {
    
        return this.user;
    }
    
    public IEntityType getComponent() {
    
        return this.component;
    }
    
    public IEntityType getVersion() {
    
        return this.version;
    }
    
    public IEntityType getRoles() {
    
        return this.roles;
    }
    
    public IKomeaSchema getSchema() {
    
        return this.schema;
    }
    
}

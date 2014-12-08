package org.komea.ecore;

import java.util.Map;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchemaFactory;

import com.google.common.collect.Maps;

class TypeMapper
{
    
    private final Map<String, IEntityType> typesMap;
    private final IKomeaSchemaFactory      factory;
    
    public TypeMapper(final IKomeaSchemaFactory factory) {
    
        this.factory = factory;
        this.typesMap = Maps.newHashMap();
    }
    
    public IEntityType getOrCreate(final String name) {
    
        IEntityType type = this.typesMap.get(name);
        if (type == null) {
            type = this.factory.newEntity(name);
            this.typesMap.put(name, type);
        }
        return type;
    }
    
    public IKomeaSchemaFactory getFactory() {
    
        return this.factory;
    }
}
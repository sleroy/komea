
package org.komea.core.model.impl;


import org.apache.commons.lang.Validate;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.IKomeaEntityFactory;
import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.core.schema.IEntityType;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public class OKomeaModelFactory implements IKomeaEntityFactory
{
    
    private final IKomeaGraphStorage storageService;
    
    public OKomeaModelFactory(final IKomeaGraphStorage storageService) {
    
        super();
        this.storageService = storageService;
        Validate.notNull(storageService);
        Validate.notNull(storageService.getSchema());
    }
    
    @Override
    public IKomeaEntity create(final IEntityType type) {
    
        Validate.isTrue(type.getSchema() != null && type.getSchema().equals(this.storageService.getSchema()),
                "Type is not defined in the same schema than the one used by the storage");
        final OrientGraph graph = this.storageService.getGraph();
        final OrientVertex vertex = graph.addVertex("class:" + type.getName());
        return new OKomeaEntity(type, vertex);
    }
    
    public IKomeaGraphStorage getStorageService() {
    
        return this.storageService;
    }
    
    public <T> IKomeaEntityFiller<T> newEntityFiller(final IEntityType _humanType) {
    
        return new KomeaEntityFiller<T>(this, _humanType);
    }
    
}


package org.komea.core.schema.integration.tests;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IReference;
import org.komea.core.schema.ReferenceKind;
import org.komea.core.schema.impl.OrientSchemaLoader;
import org.komea.core.schema.utils.YUMLExport;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType.OrientVertexProperty;

import static org.junit.Assert.*;

public class OrientSchemaLoaderTests
{
    
    @Test
    public void testSchemaLoader() throws FileNotFoundException, IOException {
    
        OrientGraph graph = buildExampleGraph();      
        
        OrientSchemaLoader loader = new OrientSchemaLoader();
        IKomeaSchema schema = loader.load("test", graph);
        graph.shutdown();
        
        //inheritancy check
        IEntityType developper = schema.findType("Developper");
        assertNotNull(developper);
        assertNotNull(developper.getSuperType());
        
        //containment check
        IReference resources = developper.findProperty("resources");
        assertNotNull(resources);
        assertTrue(resources.isContainment());
        assertTrue(resources.isMany());
        
        //aggregation check
        IReference family = developper.findProperty("family");
        assertNotNull(family);
        assertTrue(family.isAggregation());
        assertTrue(family.isMany());
        
        //indexation test
        IReference name = developper.findProperty("name");
        assertNotNull(name);
        assertTrue(name.isUnique());
        
        YUMLExport.exportToHtml(new FileOutputStream(new File("schema.html")), schema);
    }

    private OrientGraph buildExampleGraph() {
    
        OrientGraph graph = new OrientGraph("memory:test");
        graph.setAutoStartTx(false);
        graph.getRawGraph().commit(true);
        
        OrientEdgeType aggregation = graph.createEdgeType(ReferenceKind.AGGREGATION.name());
        OrientEdgeType containment = graph.createEdgeType(ReferenceKind.CONTAINMENT.name());
        
        OrientVertexType resource = graph.createVertexType("Resource");
        resource.createProperty("name", OType.STRING);
        
        OrientVertexType person = graph.createVertexType("Person");
        person.createProperty("values", OType.EMBEDDEDLIST, OType.INTEGER);
        OrientVertexProperty name = person.createProperty("name", OType.STRING);
        name.createIndex(OClass.INDEX_TYPE.UNIQUE);
        
        graph.createVertexType("Developper",person);
        
        OrientEdgeType familyEdge = graph.createEdgeType("Person_family",aggregation);
        familyEdge.setCustom("type", "Person");
        
       
        OrientEdgeType resourcesEdge = graph.createEdgeType("Person_resources",containment);
        resourcesEdge.setCustom("type", "Resource");
        
        person.createEdgeProperty(Direction.OUT, "Person_family");
        person.createEdgeProperty(Direction.OUT, "Person_resources");
        return graph;
    }
}

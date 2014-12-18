
package org.komea.connectors.jira;


import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.komea.connectors.jira.exceptions.BadConfigurationException;
import org.komea.connectors.jira.schema.JiraSchema;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.core.model.storage.impl.OKomeaGraphStorage;
import org.komea.software.model.impl.MinimalCompanySchema;
import org.springframework.orientdb.session.impl.OrientSessionFactory;
import org.springframework.orientdb.session.impl.TestDatabaseConfiguration;

import com.google.common.collect.Iterators;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

public class JiraProcessConnectorTests
{
    
    @Test
    public void testUpdateProcess() throws BadConfigurationException, IOException {
    
        JiraConfiguration config = new JiraConfiguration("https://jira.mongodb.org/");
        JiraSchema schema = new JiraSchema(new MinimalCompanySchema());
        
        OrientSessionFactory<ODatabaseDocumentTx> sessions = new OrientSessionFactory<ODatabaseDocumentTx>(new TestDatabaseConfiguration());
        
        IKomeaGraphStorage storage = new OKomeaGraphStorage(schema.getSchema(), sessions.getGraphTx());
        JiraProcessConnector jc = new JiraProcessConnector(storage, schema);
        
        jc.push(config);
        
        Iterable<IKomeaEntity> entities = storage.entities();
        int size = Iterators.size(entities.iterator());
        assertTrue(size > 0);
        sessions.close();
    }
}

package org.komea.event.storage.orient.impl;

import com.orientechnologies.orient.core.Orient;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import java.io.IOException;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.mysql.impl.EventDBFactory;
import org.springframework.orientdb.session.impl.DatabaseConfiguration;
import org.springframework.orientdb.session.impl.OrientSessionFactory;

/**
 * This class provides an implementation of the event storage with OrientDB.
 *
 * @author sleroy
 */
public class OEventDBFactory extends EventDBFactory {

    private final OrientSessionFactory<ODatabaseDocumentTx> orientSessionFactory;

    public OEventDBFactory(final DatabaseConfiguration _databaseConfiguration) {
        this(new OrientSessionFactory(_databaseConfiguration));
    }

    public OEventDBFactory(final OrientSessionFactory<ODatabaseDocumentTx> _orientSessionFactory) {
        super();
        orientSessionFactory = _orientSessionFactory;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.event.storage.IEventDBFactory#declareEventType(java.lang.String
     * )
     */
    @Override
    public void declareEventType(final String _type) {
        orientSessionFactory.getOrCreateDB().getMetadata().getSchema()
                .createClass(_type);

    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.event.storage.mysql.impl.AbstractEventDBFactory#closeStorage()
     */
    @Override
    protected void closeStorage() throws IOException {
        orientSessionFactory.getOrCreateDB().drop();
        orientSessionFactory.close();
        Orient.instance().closeAllStorages();

    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.event.storage.mysql.impl.AbstractEventDBFactory#newEventDB(
     * java.lang.String)
     */
    @Override
    protected IEventDB newEventDB(final String _eventType) {
        return new OrientDBEventDB(
                orientSessionFactory, _eventType);
    }

}

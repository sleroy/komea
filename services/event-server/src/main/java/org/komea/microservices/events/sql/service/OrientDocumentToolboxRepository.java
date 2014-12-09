package org.komea.microservices.events.sql.service;

import org.komea.orientdb.session.document.impl.AbstractOrientDocumentToolbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orientdb.orm.session.IOrientSessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

@Repository
@Transactional
public class OrientDocumentToolboxRepository extends AbstractOrientDocumentToolbox {

	@Autowired
	private IOrientSessionFactory	osf;

	public OrientDocumentToolboxRepository() {
		super();
	}

	@Override
	protected ODatabaseDocumentTx getDocumentTx() {

		return this.osf.db();
	}

}

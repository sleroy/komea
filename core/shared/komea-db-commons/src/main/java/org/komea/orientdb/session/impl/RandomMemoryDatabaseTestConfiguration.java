package org.komea.orientdb.session.impl;

import java.util.Date;
import java.util.Random;

/**
 * This class defines a database connection where OrientDB will specifically
 * creates a memory storage.
 *
 * @author sleroy
 *
 */
public class RandomMemoryDatabaseTestConfiguration extends
		MemoryDatabaseConfiguration {

	public RandomMemoryDatabaseTestConfiguration() {
		super("database" + Long.toString(new Date().getTime())
				+ new Random().nextInt());
	}

}

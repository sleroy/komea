package org.komea.event.storage.ehcache.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;

import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventDBFactory;

/**
 * This class provides an implementation of the event storage with OrientDB.
 *
 * @author sleroy
 */
public class EhCacheEventDBFactory implements IEventDBFactory {

	private final Map<String, IEventDB>	eventsDB	= new ConcurrentHashMap<>();
	private final CacheManager	        cacheManager;

	public EhCacheEventDBFactory(final File _diskstore) {
		final Configuration config = new Configuration();
		config.setUpdateCheck(true);
		config.setDynamicConfig(true);
		config.setMonitoring("autodetect");
		final CacheConfiguration defaultCacheConfiguration = new CacheConfiguration();
		defaultCacheConfiguration.maxEntriesLocalHeap(10000);
		defaultCacheConfiguration.setEternal(true);
		defaultCacheConfiguration.setOverflowToDisk(true);
		defaultCacheConfiguration.setDiskPersistent(true);
		defaultCacheConfiguration.setMemoryStoreEvictionPolicy("LFU");
		final DiskStoreConfiguration diskStoreConfigurationParameter = new DiskStoreConfiguration();
		_diskstore.mkdirs();
		diskStoreConfigurationParameter.setPath(_diskstore.getAbsolutePath());
		config.addDiskStore(diskStoreConfigurationParameter);
		config.setDefaultCacheConfiguration(defaultCacheConfiguration);
		cacheManager = CacheManager.newInstance(config);
	}

	@Override
	public void close() throws IOException {
		for (final IEventDB db : eventsDB.values()) {
			db.close();
		}
		cacheManager.removeAllCaches();

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.komea.event.storage.IEventDBFactory#declareEventType(java.lang.String
	 * )
	 */
	@Override
	public void declareEventType(final String _type) {
		// orientSessionFactory.getOrCreateDB().getMetadata().getSchema()
		// .createClass(_type);

	}

	@Override
	public IEventDB getEventDB(final String _storageName) {
		IEventDB iEventDB = eventsDB.get(_storageName);
		if (iEventDB == null) {
			final EhCacheEventDB ehCacheEventDB = new EhCacheEventDB(
					cacheManager, _storageName);
			eventsDB.put(_storageName, ehCacheEventDB);
			iEventDB = ehCacheEventDB;
		}

		return iEventDB;
	}

}

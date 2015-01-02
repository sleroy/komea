package org.komea.event.storage.ehcache.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang3.Validate;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.storage.DateInterval;
import org.komea.event.storage.IEventDB;
import org.skife.jdbi.v2.ResultIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhCacheEventDB implements IEventDB {
	
	private static final class EhcacheResultIteratorImplementation implements
	        ResultIterator<FlatEvent> {
		private final Iterator	iterator;
		private final Cache		ehCache;
		
		private EhcacheResultIteratorImplementation(
		        final Cache _cache, final List _keys) {
			ehCache = _cache;
			
			iterator = _keys.iterator();
		}
		
		@Override
		public void close() {
			//
		}
		
		@Override
		public boolean hasNext() {
			
			return iterator.hasNext();
		}
		
		@Override
		public FlatEvent next() {
			
			final Element element = ehCache.get(iterator.next());
			if (element == null) { return null; }
			return (FlatEvent) element.getObjectValue();
		}
		
		@Override
		public void remove() {
			iterator.remove();
			
		}
	}

	/**
	 * @author sleroy
	 */
	private final class EmptyResultIterator implements
	ResultIterator<FlatEvent> {
		@Override
		public void close() {

		}

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public FlatEvent next() {
			return null;
		}

		@Override
		public void remove() {

		}
	}
	
	private final CacheManager	    cacheManager;
	private final String	        eventType;
	private static final Logger	    LOGGER	= LoggerFactory
			.getLogger(EhCacheEventDB.class);
	private final Cache	            cache;
	
	private static final AtomicLong	events	= new AtomicLong();
	
	public EhCacheEventDB(
	        final CacheManager _cacheManager,
	        final String _eventType) {
		cacheManager = _cacheManager;
		if (!cacheManager.cacheExists(_eventType)) {
			cacheManager.addCache(_eventType);
		}
		cache = cacheManager.getCache(_eventType);
		eventType = _eventType;
	}
	
	@Override
	public void close() throws IOException {
		//
		
	}
	
	@Override
	public long count() {
		return cache.getSize();
	}
	
	@Override
	public ResultIterator<FlatEvent> loadAll() {
		try {
			final List keys = cache.getKeys();
			return new EhcacheResultIteratorImplementation(cache, keys);
		} catch (final Exception e) {
			LOGGER.warn(e.getMessage(), e);
			return new EmptyResultIterator();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.komea.event.storage.IEventDB#loadOnPeriod(org.komea.event.storage
	 * .DateInterval)
	 */
	@Override
	public ResultIterator<FlatEvent> loadOnPeriod(final DateInterval _period) {
		return null;
	}
	
	@Override
	public void put(final FlatEvent _flatEvent) {
		Validate.isTrue(eventType.equals(_flatEvent.getEventType()));
		cache.put(new Element(events.getAndIncrement(), _flatEvent));
		
	}

	@Override
	public void putAll(final Collection<FlatEvent> _values) {
		for (final FlatEvent event : _values) {
			put(event);
		}
		
	}
	
	@Override
	public void removeAll() {
		cache.removeAll();
		
	}
}

package org.komea.orientdb.session.impl;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang.Validate;
import org.komea.orientdb.session.PojoCreationException;
import org.komea.orientdb.session.document.IODocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class ODocumentProxy implements IODocument {

	private final ODocument newInstance;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ODocumentProxy.class);

	public ODocumentProxy(final ODocument _newInstance) {
		this.newInstance = _newInstance;
		Validate.notNull(_newInstance);

	}

	@Override
	public boolean containsField(final String _fieldDate) {

		return this.newInstance.containsField(_fieldDate);
	}

	@Override
	public String dump() {
		final StringBuilder sb = new StringBuilder();
		final Map<String, Object> map = Maps.newHashMap();
		for (final String entry : this.newInstance.fieldNames()) {
			map.put(entry, this.newInstance.field(entry));
		}
		sb.append("Document of class ").append(this.newInstance.getClassName())
				.append("with fields ").append(map.toString());
		return sb.toString();
	}

	@Override
	public Object field(final String _fieldDate) {

		return this.newInstance.field(_fieldDate);
	}

	@Override
	public void field(final String _key, final Serializable _value) {
		try {
			this.newInstance.field(_key, _value);
		} catch (final Exception e) {
			LOGGER.error("Invalid field or value has been rejected {} -> {}",
					_key, _value, e);
		}

	}

	@Override
	public ODocument getOrientDBDocument() {
		return this.newInstance;
	}

	@Override
	public void save() {
		this.newInstance.save();

	}

	@Override
	public String toJSON() {

		return this.newInstance.toJSON();
	}

	@Override
	public <T> T toPojo(final Class<T> _impl) {
		T t;
		try {
			t = _impl.newInstance();
			this.toPojo(t);
			return t;
		} catch (final Exception e) {
			throw new PojoCreationException(e);
		}

	}

	@Override
	public void toPojo(final Object _pojo) {
		final BeanMap beanMap = new BeanMap(_pojo);
		for (final String field : this.newInstance.fieldNames()) {
			beanMap.put(field, this.newInstance.field(field));
		}

	}

	@Override
	public String toString() {
		return "ODocumentProxy [newInstance=" + this.newInstance + "]";
	}
}

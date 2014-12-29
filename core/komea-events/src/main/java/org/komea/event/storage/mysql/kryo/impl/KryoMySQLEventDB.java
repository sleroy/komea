package org.komea.event.storage.mysql.kryo.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.komea.event.model.beans.FlatEvent;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.mysql.impl.MySQLStorageSupport;
import org.skife.jdbi.v2.tweak.ConnectionFactory;

import com.esotericsoftware.kryo.Kryo;

public class KryoMySQLEventDB extends MySQLStorageSupport implements IEventDB {

	private final KryoByteArrayToObjectConverter<FlatEvent>	unserializer;
	private final KryoObjectToByteArrayConverter<FlatEvent>	serializer;

	public KryoMySQLEventDB(final ConnectionFactory _dataSource, final String _tableName, String _eventType)  {
		super(_dataSource, _tableName, _eventType);
		this.unserializer = new KryoByteArrayToObjectConverter<FlatEvent>(this.initKryo(), FlatEvent.class);
		this.serializer = new KryoObjectToByteArrayConverter<FlatEvent>(this.initKryo(), FlatEvent.class);
	}

	private Kryo initKryo() {

		final Kryo kryo = new Kryo();
		kryo.register(List.class);
		kryo.register(ArrayList.class);
		kryo.register(HashMap.class);
		kryo.register(Map.class);
		kryo.register(HashSet.class);
		kryo.register(String.class);
		kryo.register(FlatEvent.class);
		return kryo;
	}

	@Override
	public byte[] serialize(FlatEvent _entry) {
		return serializer.apply(_entry);
	}

	@Override
	public FlatEvent unserialize(byte[] _entry) {

		return unserializer.apply(_entry);
	}

}

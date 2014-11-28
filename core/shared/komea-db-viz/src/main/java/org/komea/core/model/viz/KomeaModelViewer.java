package org.komea.core.model.viz;

import java.io.IOException;
import java.io.InputStream;

import org.komea.core.model.storage.IGraphModelStorage;
import org.komea.orientdb.viz.GraphStreamViewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KomeaModelViewer extends GraphStreamViewer{
	private final static Logger LOGGER= LoggerFactory.getLogger("Komea model viewer");
	private KomeaModelViewer() {
		super(new KomeaModelLabelProvider(), new KomeaModelAttributesProvider());
	}

	
	public static void  display(final String name, final IGraphModelStorage storage){
		KomeaModelViewer viewer = new KomeaModelViewer();
		viewer.run(name, storage);
	}
	
	private void run(final String name, final IGraphModelStorage storage){
		InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("komea-model.css");
		try {
			display(name, resourceAsStream, storage.getGraph());
		} catch (IOException e) {
			LOGGER.error("Unable to read stylesheet", e);
		}
	}
}

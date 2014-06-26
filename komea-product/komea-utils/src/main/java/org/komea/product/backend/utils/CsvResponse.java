package org.komea.product.backend.utils;

import java.io.Serializable;
import java.util.List;

public class CsvResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3134802114209356010L;
	
	private final String filename;
	private final List<String[]> records;

	public CsvResponse(List<String[]> records, String filename) {
		this.records = records;
		this.filename = filename;
	}
	
	public String getFilename() {
    	return filename;
	}
	
	public List<String[]> getRecords() {
    	return records;
	}
	   
}

package org.komea.product.backend.csv.utils;

import java.util.List;

public interface CSVExport {

	/**
	 * Convert an object into a List of String Array
	 * to make it easier for a CSV transformation
	 */
	public List<String[]> convertToStringList();
	
}

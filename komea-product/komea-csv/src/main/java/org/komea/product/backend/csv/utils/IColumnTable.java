package org.komea.product.backend.csv.utils;

import java.util.List;


public interface IColumnTable extends Table {

	/**
	 * Set names of the table's columns
	 * @param labels names of the table's columns
	 */
	public void setColumnHeaders(String... labels);
	
	/**
	 * Set names of the table's columns
	 * @param list of names of the table's columns
	 */
	public void setColumnHeaders(List<String> labels);
	
	/**
	 * Add a new line in the table
	 * @return a LineBuilder object for building the line
	 */
	public ILineBuilder newLine();
	
}

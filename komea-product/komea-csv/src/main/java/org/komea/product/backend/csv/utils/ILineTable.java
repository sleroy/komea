package org.komea.product.backend.csv.utils;


public interface ILineTable extends Table {
	
	/**
	 * Insert a new line in the table
	 * @param _label the line's label
	 * @return a LineBuilder object for building the line
	 */
	public ILineBuilder newLine(String _label);
	
}

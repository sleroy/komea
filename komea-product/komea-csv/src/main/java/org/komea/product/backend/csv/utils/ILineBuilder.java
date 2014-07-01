package org.komea.product.backend.csv.utils;

import java.util.List;


public interface ILineBuilder {
	
	/**
	 * Create a new cell
	 * @param _value content to put in the cell
	 * @return the current LineBuilder
	 */
	public ILineBuilder addNewCell(String _value);
	
	/**
	 * Create new cells
	 * @param _value content to put in the cells
	 * @return the current LineBuilder
	 */
	public ILineBuilder addNewCells(String... _value);
	
	/**
	 * Create new cells
	 * @param values list that contains the cells' values
	 * @return the current LineBuilder
	 */
	public ILineBuilder addNewCells(List<String> _values);
	
	/**
	 * Dump all the cells into a List of String
	 */
	public List<String> dump();

	/**
	 * Get the size of the line
	 * @return the number of cells in the line
	 */
	public Integer size();
	
}

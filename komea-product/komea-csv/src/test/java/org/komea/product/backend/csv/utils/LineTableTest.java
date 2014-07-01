package org.komea.product.backend.csv.utils;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class LineTableTest {

	private ILineTable table; 
	
	@Test
	public void testAddNewLineAndDump() {
		table = new LineTable();
		table.newLine("Line1").addNewCell("C1").addNewCell("C2");
		List<String[]> result = table.convertToStringList();
		assertEquals(1, result.size());
		assertArrayEquals(new String[] { "Line1", "C1", "C2" }, result.get(0));
	}
	
	@Test
	public void testAddMultipleLinesAndDump() {
		List<String[]> values = Lists.newArrayList();
		values.add(new String[] { "Line1", "C1", "C2" });
		values.add(new String[] { "Line2", "D1", "D2" });
		table = new LineTable();
		table.newLine(values.get(0)[0]).addNewCells(values.get(0)[1], values.get(0)[2]);
		table.newLine(values.get(1)[0]).addNewCells(values.get(1)[1], values.get(1)[2]);
		List<String[]> result = table.convertToStringList();
		assertEquals(values.size(), result.size());
		for (int i = 0; i < values.size(); i++) {
			assertArrayEquals(values.get(i), result.get(i));
		}
	}
	
}

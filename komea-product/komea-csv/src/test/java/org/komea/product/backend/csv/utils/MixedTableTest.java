package org.komea.product.backend.csv.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;


public class MixedTableTest {

	private MixedTable table;
	
	
	@Test
	public void testAddNewLineAndDump() {
		table = new MixedTable();
		table.newLine("Kpi-1").addNewCell("C1").addNewCell("C2");
		List<String[]> result = table.convertToStringList();
		assertEquals(1, result.size());
		assertArrayEquals(new String[] { "Kpi-1", "C1", "C2" }, result.get(0));
	}
	
	@Test
	public void testAddMultipleLinesAndDump() {
		table = new MixedTable();
		table.newLine("Kpi-1").addNewCell("C1").addNewCell("C2");
		table.newLine("Kpi-2").addNewCell("C3").addNewCell("C4");
		List<String[]> result = table.convertToStringList();
		assertEquals(2, result.size());
		assertArrayEquals(new String[] { "Kpi-1", "C1", "C2" }, result.get(0));
		assertArrayEquals(new String[] { "Kpi-2", "C3", "C4" }, result.get(1));
	}
	
	@Test
	public void testInsertBefore() {
		table = new MixedTable();
		table.newLine("Kpi-1").addNewCell("C1").addNewCell("C2");
		ILineBuilder line = table.newLine("Kpi-2").addNewCell("C3").addNewCell("C4");
		table.insertBefore(line).addNewCell("Kpi-3").addNewCell("C5").addNewCell("C6");
		List<String[]> result = table.convertToStringList();
		assertEquals(3, result.size());
		assertArrayEquals(new String[] { "Kpi-1", "C1", "C2" }, result.get(0));
		assertArrayEquals(new String[] { "Kpi-3", "C5", "C6" }, result.get(1));
		assertArrayEquals(new String[] { "Kpi-2", "C3", "C4" }, result.get(2));
	}
	
	@Test
	public void testAddNewColumn() {
		table = new MixedTable();
		table.setColumnHeaders("Kpi");
		table.newLine("Kpi-1");
		table.newLine("Kpi-2");
		table.newColumn("Entity-1", "C1", "C2");
		table.newColumn("Entity-2", "C3", "C4");
		List<String[]> result = table.convertToStringList();
		assertEquals(3, result.size());
		assertArrayEquals(new String[] { "Kpi", "Entity-1", "Entity-2" }, result.get(0));
		assertArrayEquals(new String[] { "Kpi-1", "C1", "C3" }, result.get(1));
		assertArrayEquals(new String[] { "Kpi-2", "C2", "C4" }, result.get(2));
	}
	
}

package org.komea.product.backend.csv.utils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;


public class LineBuilderTest {

	private ILineBuilder line;
	
	@Test
	public void testAddNewCell() {
		String[] values = { "C1", "C2" };
		line = new LineBuilder();
		line.addNewCell(values[0]).addNewCell(values[1]);
		assertEquals(line.dump(), Arrays.asList(values));
	}
	
	@Test
	public void testAddNewCells() {
		String[] values = { "C1", "C2", "C3" };
		line = new LineBuilder();
		line.addNewCells(values);
		assertEquals(line.dump(), Arrays.asList(values));
	}
	
	@Test
	public void testAddNewCellsFromList() {
		List<String> values = Arrays.asList(new String[] { "C1", "C2", "C3" });
		line = new LineBuilder();
		line.addNewCells(values);
		assertEquals(line.dump(), values);
	}
	
	@Test
	public void testDumpEmptyList() {
		line = new LineBuilder();
		assertEquals(line.dump(), Lists.newArrayList());
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testExceedingSizeLimit() {
		line = new LineBuilder(2);
		line.addNewCell("C1").addNewCell("C2").addNewCell("C3");
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testExceedingSizeLimitByGivingArray() {
		line = new LineBuilder(2);
		line.addNewCells(new String[] { "C1", "C2", "C3" });
	}
	
	@Test
	public void testFillingLine() {
		line = new LineBuilder(3);
		String[] values = { "C1", "C2", "C3" };
		line.addNewCell(values[0]).addNewCell(values[1]).addNewCell(values[2]);
		assertEquals(line.dump(), Arrays.asList(values));
	}
	
	@Test
	public void testFillingLineByGivingArray() {
		line = new LineBuilder(3);
		String[] values = { "C1", "C2", "C3" };
		line.addNewCells(values);
		assertEquals(line.dump(), Arrays.asList(values));
	}
	
	@Test
	public void testNotFilledLine() {
		line = new LineBuilder(3);
		String[] values = { "C1", "C2", "" };
		line.addNewCell(values[0]).addNewCell(values[1]);
		assertEquals(line.dump(), Arrays.asList(values));
	}
	
}

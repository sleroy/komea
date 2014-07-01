package org.komea.product.backend.csv.utils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class ColumTableTest {

	private ColumnTable table;
	
	@Test
	public void testColumnHeaders() {
		String[] headers = new String[] { "Entity1", "Entity2" };
		table = new ColumnTable();
		table.setColumnHeaders(headers);
		List<String[]> expectedResult =  Lists.newArrayList();
		expectedResult.add(headers);
		List<String[]> result = table.convertToStringList();
		assertEquals(result.size(), expectedResult.size());
		for (int i = 0; i < result.size(); i++) {
			assertArrayEquals(expectedResult.get(i), result.get(i));
		}
	}
	
	@Test
	public void testColumnHeadersAsList() {
		String[] headers = new String[] { "Entity1", "Entity2" };
		table = new ColumnTable();
		table.setColumnHeaders(Arrays.asList(headers));
		List<String[]> result = table.convertToStringList();
		List<String[]> expectedResult =  Lists.newArrayList();
		expectedResult.add(headers);
		assertEquals(result.size(), expectedResult.size());
		for (int i = 0; i < result.size(); i++) {
			assertArrayEquals(expectedResult.get(i), result.get(i));
		}
	}
	
	@Test
	public void testNewLine() {
		table = new ColumnTable();
		table.newLine().addNewCell("C1").addNewCell("C2");
		List<String[]> result = table.convertToStringList();
		assertEquals(1, result.size());
		assertArrayEquals(new String[] { "C1", "C2" }, result.get(0));
	}
	
}

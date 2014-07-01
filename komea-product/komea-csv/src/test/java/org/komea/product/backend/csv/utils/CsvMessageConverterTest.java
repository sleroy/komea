package org.komea.product.backend.csv.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import com.google.common.collect.Lists;

public class CsvMessageConverterTest {

	private CsvMessageConverter csvConverter;
	
	private final MediaType MEDIA_TYPE = new MediaType("text", "csv", Charset.forName("utf-8"));
	
	@SuppressWarnings("unchecked")
	private final List<Class<? extends Table>> supportedClasses = Lists.newArrayList(
			MixedTable.class, ColumnTable.class, LineTable.class
	);
	
	@Before
	public void testUp() {
		csvConverter = new CsvMessageConverter();
	}
	
	@Test
	public void testWrite() throws Exception {
		
		List<String> headers = Lists.newArrayList("header1", "header2", "header3");
		List<String> line1 = Lists.newArrayList("cell1", "cell2", "cell3");
		List<String> line2 = Lists.newArrayList("cell4", "cell5", "cell6");
		
		MixedTable table = new MixedTable();
		table.setColumnHeaders(headers);
		table.newLine().addNewCells(line1);
		table.newLine().addNewCells(line2);
		
		final HttpHeaders httpHeaders = new HttpHeaders();
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		HttpOutputMessage outputMessage = new HttpOutputMessage() {		
			@Override
			public HttpHeaders getHeaders() {
				return httpHeaders;
			}
			
			@Override
			public OutputStream getBody() throws IOException {
				return out;
			}
		};
		
		csvConverter.write(table, MEDIA_TYPE, outputMessage);
		
		StringBuilder expected = new StringBuilder();
		expected.append(headers.get(0)).append(',');
		expected.append(headers.get(1)).append(',');
		expected.append(headers.get(2)).append('\n');
		expected.append(line1.get(0)).append(',');
		expected.append(line1.get(1)).append(',');
		expected.append(line1.get(2)).append('\n');
		expected.append(line2.get(0)).append(',');
		expected.append(line2.get(1)).append(',');
		expected.append(line2.get(2)).append('\n');
		
		assertEquals(expected.toString(), out.toString());
		
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testSupportedClasses() {
		
		for(Class clazz : supportedClasses) {
			assertTrue(csvConverter.supports(clazz));
		}
		
	}
	
	@Test
	public void testNotSupportedClasses() {

		assertFalse(csvConverter.supports(LineBuilder.class));
		
	}
	
}

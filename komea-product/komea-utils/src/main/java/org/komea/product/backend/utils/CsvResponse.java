package org.komea.product.backend.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import au.com.bytecode.opencsv.CSVWriter;

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
	   
	public void output(HttpServletResponse _out) throws IOException {
		_out.setHeader("Content-Type", "text/csv;charset=UTF-8");
		_out.setHeader("Content-Disposition", "attachment; filename=\"" + this.filename + "\"");
		StringWriter sw = new StringWriter();
		CSVWriter writer = new CSVWriter(sw, ',');
		writer.writeAll(this.getRecords());
	    writer.close();
	    _out.getOutputStream().print(sw.getBuffer().toString());
	}
	
}

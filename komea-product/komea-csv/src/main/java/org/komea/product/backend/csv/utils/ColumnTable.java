package org.komea.product.backend.csv.utils;

import java.util.List;

import com.google.common.collect.Lists;

public class ColumnTable implements IColumnTable {

	protected List<String> headers;
	
	protected List<ILineBuilder> lines;
	
	public ColumnTable() {
		this.headers = Lists.newArrayList();
		this.lines = Lists.newArrayList();
	}

	@Override
	public void setColumnHeaders(String... labels) {
		for(String h : labels) {
			this.headers.add(h);
		}
	}
	
	@Override
	public void setColumnHeaders(List<String> labels) {
		this.headers = labels;
	}

	@Override
	public ILineBuilder newLine() {
		ILineBuilder line = new LineBuilder(headers.size());
		lines.add(line);
		return line;
	}
	
	@Override
	public List<String[]> convertToStringList() {
		List<String[]> result = Lists.newArrayList();
		if (!headers.isEmpty()) {
			result.add((String[])headers.toArray(new String[headers.size()]));		
		}
		if (!lines.isEmpty()) {
			for(ILineBuilder line : lines) {
				result.add((String[])line.dump().toArray(new String[line.size()]));
			}
		}
		return result;
	}

}

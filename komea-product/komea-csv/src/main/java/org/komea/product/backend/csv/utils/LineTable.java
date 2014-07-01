package org.komea.product.backend.csv.utils;

import java.util.List;

import com.google.common.collect.Lists;

public class LineTable implements ILineTable {

	private List<ILineBuilder> lines = Lists.newArrayList();
	
	@Override
	public ILineBuilder newLine(String _label) {
		ILineBuilder line = new LineBuilder();
		line.addNewCell(_label);
		lines.add(line);
		return line;
	}

	@Override
	public List<String[]> convertToStringList() {
		List<String[]> result = Lists.newArrayList();
		if (!lines.isEmpty()) {
			for(ILineBuilder line : lines) {
				result.add(line.dump().toArray(new String[line.size()]));
			}
		}
		return result;
	}
	
}

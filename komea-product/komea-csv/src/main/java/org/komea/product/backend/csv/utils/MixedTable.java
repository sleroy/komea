package org.komea.product.backend.csv.utils;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;


public class MixedTable extends ColumnTable implements ILineTable {
	
	@Override
	public ILineBuilder newLine(String _label) {
		ILineBuilder line = new LineBuilder();
		line.addNewCell(_label);
		lines.add(line);
		return line;
	}
	
	public ILineBuilder insertBefore(ILineBuilder line) {
		ILineBuilder newLine = new LineBuilder();	
		List<ILineBuilder> newLines = Lists.newArrayList();
		int i = 0;
		for (ILineBuilder l : lines) {
			if (l == line) {
				newLines.add(newLine);
				i++;
			}
			newLines.add(l);
			i++;
		}
		lines = newLines;
		return newLine;
	}
	
	public void newColumn(String... labels) {
		newColumn(labels[0], Arrays.asList(labels).subList(1, labels.length));
	}
	
	public void newColumn(String header, List<String> cells) {
		this.headers.add(header);
		for (int i = 0; i < cells.size(); i++) {
			lines.get(i).addNewCell(cells.get(i));
		}
	}

}

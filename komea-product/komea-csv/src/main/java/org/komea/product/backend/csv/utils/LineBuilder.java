package org.komea.product.backend.csv.utils;

import java.util.List;

import com.google.common.collect.Lists;

public class LineBuilder implements ILineBuilder {

	private List<String> cells = Lists.newArrayList();
	
	private int sizeLimit;
	
	public LineBuilder() {		
		this.sizeLimit = 0;
	}
	public LineBuilder(int size) {
		this.sizeLimit = size;
	}
	
	@Override
	public ILineBuilder addNewCell(String _value)
		throws ArrayIndexOutOfBoundsException {
		if (this.sizeLimit == 0 || cells.size() < this.sizeLimit)
			this.cells.add(_value);
		else
			throw new IndexOutOfBoundsException();
		return this;
	}

	@Override
	public ILineBuilder addNewCells(String... _value) {
		if (this.sizeLimit == 0 || cells.size()+_value.length <= this.sizeLimit) {
			for(String v : _value) {
				this.cells.add(v);
			}
		}else{
			throw new IndexOutOfBoundsException();
		}
		return this;
	}
	
	@Override
	public ILineBuilder addNewCells(List<String> _values) {
		return addNewCells(_values.toArray(new String[_values.size()]));
	}
	
	@Override
	public Integer size() {
		return this.cells.size();
	}

	@Override
	public List<String> dump() {
		List<String> result = Lists.newArrayList();
		result.addAll(this.cells);
		if (this.sizeLimit > 0) {
			while (result.size() < this.sizeLimit) {
				result.add("");
			}
		}
		return result;
	}
	
}

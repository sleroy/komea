package org.komea.product.backend.utils;

import java.util.List;

import org.apache.commons.math3.ode.MainStateJacobianProvider;

interface CSVExport {
	
	public List<String[]> convertToStringList();
	
}

public interface Table extends CSVExport {

//	List<LineBuilder> table;
//	
//	List<String> columnHeaders;
	
	
	
	public List<String[]> convertToSringList();

	

}

interface ColumnTable extends Table {
	public void setColumnHeaders(String... labels);
	
	public LineBuilder newLine();
	
}

interface LineBuilder {
	
	LineBuilder addNewCell(String _value);
	
	LineBuilder addNewCells(String... _value);
	
	List<String> dump();
}

class HeaderLineBuilder implements LineBuilder {
	public HeaderLineBuilder(String _header) {
		addNewCell(_header);
	}

	@Override
	public LineBuilder addNewCell(String _value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LineBuilder addNewCells(String... _value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> dump() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}


interface LineTable extends Table {
	public LineBuilder newLine(String _label);
}

interface MixedTable extends LineTable, ColumnTable {
	

	
}



class A {
	public static void main(String[] args) {
		ColumnTable columnTable = null;
		columnTable.setColumnHeaders("Entité", "KPI1" , "KPI2");
		columnTable.newLine().addNewCells("E1", "12", "24");
		columnTable.newLine().addNewCell("E1").addNewCell("12").addNewCell("24");
		
		columnTable.newLine().addNewCell("E1").addNewCell("E2").addNewCell("E3");
		
		
		LineTable lineTable = null;
		/*lineTable.setLineHeaders("KPI 1", "KPI 2" , "KPI 3");
		lineTable.setLine(0, "12");
		lineTable.setLine(1, "24"); // Throw new runtime // */
	}
}

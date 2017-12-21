package at.am.friedman.designer.data;

import org.eclipse.ui.IMemento;

import at.am.friedman.shared.SaveableObject;

public abstract class GraphicalDataObject implements SaveableObject {

	int high;
	int width;
	
	String row;
	String column;
	
	
	
	@Override
	public void saveState(IMemento memento){
		
	}

	public void restoreState(IMemento memento){
		
	}

	public boolean have(String row2, String column2) {
		return row.equals(row2) && column.equals(column2);
	}

	

}

package at.am.friedman.designer.data;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IMemento;

public abstract class GraphicalDataObjectsContainer extends GraphicalDataObject {

	List<GraphicalDataObject> objects = new ArrayList<>();
	
	String name;

	int nrOfColumns;
	int nrOfRows;
	
	public boolean addObject(GraphicalDataObject o){
		if(objects.contains(o)){
			return false;
		}
		objects.add(o);
		return true;
	}
	
	public GraphicalDataObject getObject(String row , String column){
		for(GraphicalDataObject o : objects){
			if(o.have(row, column)){
				return o;
			}
		}
		
		return null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNrOfColumns() {
		return nrOfColumns;
	}

	public void setNrOfColumns(int nrOfColumns) {
		this.nrOfColumns = nrOfColumns;
	}

	public int getNrOfRows() {
		return nrOfRows;
	}

	public void setNrOfRows(int nrOfRows) {
		this.nrOfRows = nrOfRows;
	}

	@Override
	public void saveState(IMemento memento) {
		
	}

	@Override
	public void restoreState(IMemento memento) {
		
	}

	
	
}

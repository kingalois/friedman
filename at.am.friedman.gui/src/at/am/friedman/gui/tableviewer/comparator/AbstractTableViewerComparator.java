package at.am.friedman.gui.tableviewer.comparator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

public abstract class AbstractTableViewerComparator extends ViewerComparator {

	private int propertyIndex = 0;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;

	public AbstractTableViewerComparator(){
		this.propertyIndex = 0;
		this.direction = DESCENDING;
	}
	
	public void setColumn(int column){
		if(column == this.propertyIndex ){
			direction = 1 - direction;
		}
		else {
			propertyIndex = column;
		}
	}
	
	public int getDirection(){
		return direction == 1 ? SWT.DOWN : SWT.UP;
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		int c = compareObjects(e1, e2, this.propertyIndex);
		// If descending order, flip the direction
	    if (direction == DESCENDING) {
	      c = -c;
	    }
		return c;
	}
	
	public abstract int compareObjects(Object e1, Object e2, int column);
	
	
	
}

package at.am.friedman.gui.tableviewer.filter;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public abstract class DefaultViewerFilter extends ViewerFilter {

	private Map<Integer, String> searchStrings = new HashMap<>();

	
	public void setSearchString(Integer id, String s){
		searchStrings.put(id, s);
	}
	
	

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(searchStrings.isEmpty() || matches(searchStrings, element)){
			return true;
		}
		return false;
	}
	
	public abstract boolean matches(Map<Integer, String> searchStringMapping, Object element);


}

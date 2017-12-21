package at.am.friedman.gui.provider;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class GraveOverviewTreeContentProvider implements ITreeContentProvider {

	@Override
	public void dispose ( ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged ( Viewer viewer, Object oldInput, Object newInput ) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements ( Object inputElement ) {
		ArrayList<Object> ret = (ArrayList<Object>) inputElement;
		return ret.toArray();
	}

	@Override
	public Object[] getChildren ( Object parentElement ) {

		return new ArrayList<Object>().toArray();

	}

	@Override
	public Object getParent ( Object element ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren ( Object element ) {
		return false;
	}

}

package at.am.friedman.gui.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import at.am.friedman.gui.tableviewer.AbstractTableViewer;
import at.am.friedman.update.shared.UpdateEntryInterface;
import at.am.friedman.update.shared.UpdateEntryManagerFactory;
import at.am.friedman.update.shared.UpdateEntryManagerInterface;


public class UpdateEntryDialog extends TitleAreaDialog {
	
	private final UpdateEntryManagerInterface manager;
	

	public UpdateEntryDialog(Shell parentShell) {
		super(parentShell);
		this.manager = UpdateEntryManagerFactory.getUpdateEntryManagerSingleton();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite content = (Composite) super.createDialogArea(parent);
		UpdateTableViewer tableViewer = new UpdateTableViewer(content, SWT.NONE);
		return content;
	}
	
	

	private class UpdateTableViewer extends AbstractTableViewer{

		public UpdateTableViewer(Composite parent, int style) {
			super(parent, style);
		}

		@Override
		public Object getViewerInput() {
			return manager.getAllNonConfirmedUpdateEntries();
		}

		@Override
		public List<String> getColumnNames() {
			List<String> names = new ArrayList<String>();
			names.add("Titel");
			names.add("Beschreibung");
			return names;
		}

		
		
		@Override
		public Composite createSearchComposite(Composite parent) {
			Composite search = super.createSearchComposite(parent);
			search.setVisible(false);
			return search;
			
		}

		@Override
		public List<Integer> getColumnWidths() {
			List<Integer> sizes = new ArrayList<Integer>();
			sizes.add(80);
			sizes.add(200);
			return sizes;
		}

		@Override
		public String getTextForColumn(int columnIndex, Object element) {
			UpdateEntryInterface entry = (UpdateEntryInterface) element;
			switch (columnIndex) {
			case 0:
				return entry.getTitle();
			case 1:
				return entry.getDescription();

			default:
				return "";
			}
		}

		@Override
		public Image getImageForColumn(int columnIndex, Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Menu createPopUpMenu(Composite parent, Table table) {
			Menu m = new Menu(parent);
			parent.setMenu(m);
			return m;
		}

		@Override
		public String getId() {
			return "updateEntriesTabelViewer";
		}
		
	}



	@Override
	protected void okPressed() {
		manager.confirmAll();
		super.okPressed();
	}

	public boolean shouldShow() {
		return manager.haveNotConfirmedUpdate();
	}
	

}

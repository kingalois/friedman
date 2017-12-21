package at.am.friedman.gui.customComposites;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import at.am.friedman.gui.tableviewer.DiedPersonTableViewer;
import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.shared.GraveInterface;

public class GraveDetailsComposite extends AbstractCemeteryComposite {

	private GraveChooserComposite chooser;
	private Text messageText;
	private AbstractCemeteryComposite graveOwnerComposite;
	private AbstractCemeteryComposite graveComposite;
	private DiedPersonTableViewer diedPersons;
	ScrolledComposite scrolledComposite;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public GraveDetailsComposite(Composite parent, int style) {
		super(parent, SWT.NONE);
		setLayout(new GridLayout(2, true));
		GridData data = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		setLayoutData(data);
	}

	@Override
	public void createContent() {
		chooser = new GraveChooserComposite(this, SWT.NONE);
		chooser.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		addListenerToChooser();
		
		messageText = new Text(this, SWT.NONE);
		messageText.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		messageText.setEditable(false);
		

		graveOwnerComposite = new GraveOwnerComposite(this, SWT.NONE);
		graveOwnerComposite.setEnabled(false);
		
		graveComposite = new GraveComposite(this, SWT.NONE);
		graveComposite.setEnabled(false);
		
		diedPersons = new DiedPersonTableViewer(this, SWT.NONE){

			@Override
			public Object getViewerInput() {
				return getDiedPersons();
			}

			@Override
			protected GraveInterface getActualGrave() {
				return getGrave();
			}

			@Override
			public String getId() {
				return "diedpersontablegravedetail";
			}
			
			
			
			
		};

		GridData diedPersonData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		diedPersonData.heightHint = 180;

		diedPersons.setLayoutData(diedPersonData);

	}
	
	private Object getDiedPersons(){
		return DataProviderFactory.createDataProvider().getDiedPersonsForGrave((GraveInterface) getData());
	}
	
	private GraveInterface getGrave(){
		return (GraveInterface) getData();
	}

	private void addListenerToChooser() {
		chooser.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				setData(chooser.getGraveData());
				graveOwnerComposite.setData(DataProviderFactory.createDataProvider().getOwnerFromGrave((GraveInterface) getData()));
				graveComposite.setData(getData());
				fillDiedPersonComponent();
				fillMessageText((GraveInterface) getData());
			}

			

		});

		chooser.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				setData(chooser.getGraveData());
				graveOwnerComposite.setData(DataProviderFactory.createDataProvider().getOwnerFromGrave((GraveInterface) getData()));
				graveComposite.setData(getData());
				fillDiedPersonComponent();
				fillMessageText((GraveInterface) getData());
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// do nothing
			}
		});

		chooser.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				setData(chooser.getGraveData());
				graveOwnerComposite.setData(DataProviderFactory.createDataProvider().getOwnerFromGrave((GraveInterface) getData()));
				graveComposite.setData(getData());
				fillDiedPersonComponent();
				fillMessageText((GraveInterface) getData());
			}
		});
	}

	@Override
	public void dataChanged() {

		layout();

	}
	
	private void fillMessageText(GraveInterface data) {
		if (data != null) {
			if (DataProviderFactory.createDataProvider().isGraveInMulitGrave(data)) {
				messageText.setText("Das Grab ist Teil eines Doppel(Mehrfach) Grabs");
			} else {
				messageText.setText("");

			}
		}
		messageText.setEnabled(true);

	}

	private void fillDiedPersonComponent() {
		diedPersons.refresh();
	}

	public void setInitalGrave(GraveInterface grave) {
		setData(grave);
		chooser.setGraveData(grave);
		layout();
	}

}

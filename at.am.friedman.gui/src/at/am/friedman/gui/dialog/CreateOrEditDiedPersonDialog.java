package at.am.friedman.gui.dialog;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import at.am.friedman.commons.utils.StringUtils;
import at.am.friedman.data.enums.InternalPosition;
import at.am.friedman.gui.customWidgets.ImageViewer;
import at.am.friedman.gui.utils.ImageUtils;
import at.am.friedman.gui.utils.WidgetFactory;
import at.am.friedman.shared.CemeteryDataProviderInterface;
import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.shared.DiedPersonInterface;
import at.am.friedman.shared.GraveInterface;
import at.am.common.logging.LogFactory;

import com.google.common.io.Files;

public class CreateOrEditDiedPersonDialog extends TitleAreaDialog {

	DiedPersonInterface diedPerson;
	GraveInterface grave;

	private Text txtFirstName;
	private Text lastNameText;
	private DateTime dateDayOfInterment;
	private DateTime dateDeathday;
	private Combo comboInternalPosition;
	private Combo comboGrave;
	private CemeteryDataProviderInterface provider;
	private Text txtAge;
	private Text txtDescription;
	private Button shouldCheck;
	private String imagePath;
	private Text txtStreet;
	private Text txtHouseNr;
	private Text txtPostalCode;
	private Text txtTown;

	private Composite imageComposite;
	private Button deleteImage;

	private final Logger log = LogFactory.makeLogger();

	public CreateOrEditDiedPersonDialog(Shell parentShell) {
		super(parentShell);
		provider = DataProviderFactory.createDataProvider();

	}

	@Override
	public void create() {
		super.create();
		if (diedPerson != null) {
			setTitle("Verstorbenen bearbeiten..");
			setMessage("Hier können die Daten des Verstorbenen ändern", IMessageProvider.INFORMATION);
		} else {
			setTitle("Neuen Verstorbenen anlegen..");
			setMessage("Hier können die Daten des Verstorbenen ändern", IMessageProvider.INFORMATION);

		}
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout(2, false);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(layout);
		createFirstName(container);
		createLastName(container);
		createStreet(container);
		createHouseNr(container);
		createPostalCode(container);
		createTown(container);
		createDeathday(container);
		createAge(container);
		createDayOfInterment(container);
		createGrave(container);
		createInternalPosition(container);
		createDescription(container);
		createAttachImage(container);
		createShouldCheck(container);

		return area;
	}

	private void createAttachImage(final Composite parent) {
		Button b = WidgetFactory.createButton(parent, SWT.PUSH);
		b.setText("Bild anhängen..");

		final Composite imageAndDelete = new Composite(parent, SWT.NONE);
		imageAndDelete.setLayout(new GridLayout(2, false));

		if (diedPerson != null && diedPerson.havePictures()) {
			createImageAndDelete(imageAndDelete, diedPerson.getPictures().iterator().next());
			setImagePath(diedPerson.getPictures().iterator().next());
		} else {
			createImageAndDelete(imageAndDelete, imagePath);
		}

		b.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(parent.getShell(), SWT.OPEN);
				fd.setText("Wähle Bild...");
				String path = fd.open();
				if (path != null) {
					setImagePath(path);
				}

			}

		});

	}

	/**
	 * sets the imagePath and controlls/layouts the thumbnail and delete button
	 * 
	 * @param path
	 */
	private void setImagePath(String path) {
		imagePath = path;
		if (imagePath == null) {
			imageComposite.setBackgroundImage(null);
			deleteImage.setEnabled(false);
			return;
		}
		Image scaledImage = ImageUtils.scaleImage(new Image(getShell().getDisplay(), getImagePath()), 30);
		imageComposite.setBackgroundImage(scaledImage);
		imageComposite.setLayoutData(new GridData(scaledImage.getBounds().width, scaledImage.getBounds().height));
		deleteImage.setEnabled(true);
		deleteImage.getParent().setVisible(true);

	}

	private String getImagePath() {
		return imagePath;

	}

	private void createImageAndDelete(final Composite parent, final String imagePath) {
		parent.setVisible(imagePath != null);
		imageComposite = new Composite(parent, SWT.NONE);
		if (imagePath != null) {
			final Image image = new Image(parent.getDisplay(), imagePath);
			Image scaledImage = ImageUtils.scaleImage(image, 30);
			imageComposite.setBackgroundImage(scaledImage);
			imageComposite.setLayoutData(new GridData(scaledImage.getBounds().width, scaledImage.getBounds().height));
		} else {
			imageComposite.setBackgroundImage(null);
			imageComposite.setLayoutData(new GridData(30, 30));
		}

		imageComposite.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDown(MouseEvent e) {
				if (getImagePath() != null) {
					ImageViewer v = new ImageViewer(parent.getShell());
					v.setImagePath(getImagePath());
					v.open();
				}
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		deleteImage = new Button(parent, SWT.PUSH);
		deleteImage.setText("Bild entfernen");
		deleteImage.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox confirmMessageBox = new MessageBox(parent.getShell());
				confirmMessageBox.setMessage("Bild wirklich entfernen?");
				if (confirmMessageBox.open() == SWT.OK) {
					if (diedPerson != null) {
						diedPerson.removePicture();
					}
					setImagePath(null);
				}
			}

		});

	}

	private void createShouldCheck(Composite parent) {
		WidgetFactory.createLabel(parent, "");
		shouldCheck = WidgetFactory.createButton(parent, SWT.CHECK);
		shouldCheck.setText("Position prüfen");
		shouldCheck.setSelection(true);

	}

	private void createDescription(Composite parent) {
		WidgetFactory.createLabel(parent, "Beschreibung");
		txtDescription = WidgetFactory.createText(parent, SWT.MULTI | SWT.BORDER | SWT.WRAP, diedPerson != null ? diedPerson.getDescription() : "");
	}

	private void createAge(Composite parent) {
		WidgetFactory.createLabel(parent, "im Alter von");
		txtAge = WidgetFactory.createText(parent, SWT.BORDER, diedPerson != null ? Integer.toString(diedPerson.getAge()) : "");
	}

	private void createFirstName(Composite container) {
		WidgetFactory.createLabel(container, "Vorname");
		txtFirstName = WidgetFactory.createText(container, SWT.BORDER, diedPerson != null ? diedPerson.getFirstName() : "");

	}

	private void createLastName(Composite container) {
		WidgetFactory.createLabel(container, "Nachname");
		lastNameText = WidgetFactory.createText(container, SWT.BORDER, diedPerson != null ? diedPerson.getSurname() : "");
	}

	private void createDayOfInterment(Composite container) {
		WidgetFactory.createLabel(container, "Begräbnistag");

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(diedPerson == null ? System.currentTimeMillis() : diedPerson.getDayOfInterment());

		dateDayOfInterment = WidgetFactory.createDateTime(container, SWT.DATE, cal);

	}

	private void createDeathday(Composite container) {
		WidgetFactory.createLabel(container, "Todestag");

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(diedPerson == null ? System.currentTimeMillis() : diedPerson.getDeathday());

		dateDeathday = WidgetFactory.createDateTime(container, SWT.DATE, cal);

	}

	private void createInternalPosition(Composite container) {
		WidgetFactory.createLabel(container, "Position");

		comboInternalPosition = WidgetFactory.createCombo(container, SWT.READ_ONLY, InternalPosition.values());

		if (diedPerson != null) {
			comboInternalPosition.select(diedPerson.getInternalPosition());
		}

	}

	private void createTown(Composite container) {
		WidgetFactory.createLabel(container, "Ort");
		txtTown = WidgetFactory.createText(container, SWT.BORDER, diedPerson != null ? diedPerson.getTown() : "");

	}

	private void createPostalCode(Composite container) {
		WidgetFactory.createLabel(container, "PLZ");
		txtPostalCode = WidgetFactory.createText(container, SWT.BORDER, diedPerson != null ? diedPerson.getPostalCode() : "");

	}

	private void createHouseNr(Composite container) {
		WidgetFactory.createLabel(container, "Hausnr.");
		txtHouseNr = WidgetFactory.createText(container, SWT.BORDER, diedPerson != null ? diedPerson.getHouseNr() : "");

	}

	private void createStreet(Composite container) {
		WidgetFactory.createLabel(container, "Straße");
		txtStreet = WidgetFactory.createText(container, SWT.BORDER, diedPerson != null ? diedPerson.getStreet() : "");

	}

	private void createGrave(Composite container) {
		WidgetFactory.createLabel(container, "Grab");

		GridData dataGrave = new GridData();
		dataGrave.grabExcessHorizontalSpace = true;
		dataGrave.horizontalAlignment = GridData.FILL;

		comboGrave = WidgetFactory.createCombo(container, SWT.READ_ONLY, provider.getAllGraves());

		if (diedPerson != null) {
			grave = provider.getGraveById(diedPerson.getGraveId());
		}
		if (grave != null) {
			comboGrave.select(provider.getAllGraves().indexOf(grave));
		}
	}

	public void setDiedPerson(DiedPersonInterface diedPerson) {
		this.diedPerson = diedPerson;
	}

	public DiedPersonInterface getDiedPerson() {
		return diedPerson;
	}

	public void setGrave(GraveInterface grave) {
		this.grave = grave;
	}

	@Override
	protected void okPressed() {
		if (!checkData()) {
			return;
		}
		saveInput();
		super.okPressed();
	}

	private boolean checkData() {
		if (shouldCheck.getSelection()) {
			GraveInterface grave = provider.getAllGraves().get(comboGrave.getSelectionIndex());
			Calendar cal = Calendar.getInstance();
			cal.clear();
			cal.set(dateDayOfInterment.getYear(), dateDayOfInterment.getMonth(), dateDayOfInterment.getDay());
			for (DiedPersonInterface diedPerson : provider.getDiedPersonsForGrave(grave)) {
				if (diedPerson.getInternalPosition() == comboInternalPosition.getSelectionIndex()
						&& !(diedPerson.getFirstName().equals(txtFirstName.getText()) && diedPerson.getSurname().equals(lastNameText.getText()) && diedPerson.getDayOfInterment() == cal
								.getTimeInMillis()) && InternalPosition.values()[comboInternalPosition.getSelectionIndex()] != InternalPosition.Urne
						&& isLessThan10YearsDifference(diedPerson.getDayOfInterment(), cal.getTimeInMillis())) {
					setErrorMessage("Diese Position ist nicht möglich, " + diedPerson.getFullName() + " wurde am " + StringUtils.stringifyDateLong(diedPerson.getDayOfInterment())
							+ " auf dieser Position eingegraben");
					return false;
				}
			}
		}
		return true;

	}

	private boolean isLessThan10YearsDifference(long date1, long date2) {
		if (Math.abs(date1 - date2) < TimeUnit.DAYS.toMillis(365 * 10)) {
			return true;
		}
		return false;
	}

	private void saveInput() {
		if (diedPerson == null) {
			diedPerson = provider.getNewDiedPerson();
		}
		diedPerson.setFirstName(txtFirstName.getText());
		diedPerson.setSurname(lastNameText.getText());
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(dateDayOfInterment.getYear(), dateDayOfInterment.getMonth(), dateDayOfInterment.getDay());
		diedPerson.setDayOfInterment(cal.getTimeInMillis());
		cal.set(dateDeathday.getYear(), dateDeathday.getMonth(), dateDeathday.getDay());
		diedPerson.setDeathday(cal.getTimeInMillis());
		diedPerson.setInternalPosition(comboInternalPosition.getSelectionIndex());
		diedPerson.setGraveId(provider.getAllGraves().get(comboGrave.getSelectionIndex()).getId());
		if (!txtAge.getText().isEmpty()) {
			diedPerson.setAge(Integer.parseInt(txtAge.getText()));
		}
		diedPerson.setDescription(txtDescription.getText());
		if (imagePath != null) {
			String image = copyImage(imagePath, diedPerson);
			if (image != null) {
				diedPerson.getPictures().clear();
				diedPerson.addPicture(image);
			}
		}
		diedPerson.setStreet(txtStreet.getText());
		diedPerson.setHouseNr(txtHouseNr.getText());
		diedPerson.setPostalCode(txtPostalCode.getText());
		diedPerson.setTown(txtTown.getText());
		log.info("saveInput: " + diedPerson.toString());
	}

	private String copyImage(String sourcePath, DiedPersonInterface dP) {
		File imageDirectory = new File("images");
		if (!imageDirectory.exists()) {
			imageDirectory.mkdir();
		}
		String destination = "images/" + dP.getFirstName() + dP.getSurname() + dP.getId() + "." + Files.getFileExtension(sourcePath);
		try {
			Files.copy(new File(sourcePath), new File(destination));
			return destination;
		} catch (IOException e) {
			log.log(Level.SEVERE, "cannot copy file from " + sourcePath + " to " + destination, e);
			MessageBox mb = new MessageBox(this.getShell());
			mb.setMessage("Datei konnte nocht kopiert werden: " + e.getMessage());
			mb.open();
		}
		return null;
	}

}

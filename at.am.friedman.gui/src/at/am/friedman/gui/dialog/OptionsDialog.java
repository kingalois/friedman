package at.am.friedman.gui.dialog;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import at.am.friedman.gui.customWidgets.IntegerText;
import at.am.friedman.gui.utils.WidgetFactory;
import at.am.friedman.shared.CemeteryOptionsInterface;

public class OptionsDialog extends TitleAreaDialog {

	private CemeteryOptionsInterface options;
	private IntegerText normalGraveLength;
	private IntegerText normalGraveWidth;
	private IntegerText wallGraveLength;
	private IntegerText wallGraveWidthLeft;
	private IntegerText wallGraveWidthLeftTop;
	private IntegerText wallGraveWidthLeftNew;
	private IntegerText wallGraveWidthLeftTopNew;
	private IntegerText wallGraveWidthRightTopNew;
	private IntegerText wallGraveWidthRightNew;
	private IntegerText wallGraveWidthRight;
	private IntegerText wallGraveWidthRightTop;
	private IntegerText nrOfGravePlacesOld;
	private IntegerText nrOfGraveRowsOld;
	private IntegerText nrOfWallGravesLeftSide;
	private IntegerText nrOfWallGravesLeftTop;
	private IntegerText nrOfWallGravesRightSide;
	private IntegerText nrOfWallGravesRightTop;

	private IntegerText maxImageWidthForDetail;
	private IntegerText maxImageWidthForTooltip;

	private OptionsDialog(Shell parent) {
		super(parent);

		// TODO Auto-generated constructor stub
	}

	public OptionsDialog(Shell parent, CemeteryOptionsInterface options) {
		super(parent);

		this.options = options;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		this.setTitle("Optionen....");
		this.setMessage("Dialog zum bearbeiten der Optionen", IMessageProvider.INFORMATION);
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		createOptionsComponents(comp);

		return comp;
	}

	@Override
	protected void okPressed() {
		options.setNormalGraveLength(normalGraveLength.getTextAsInt());
		options.setNormalGraveWidth(normalGraveWidth.getTextAsInt());
		options.setWallGraveLength(wallGraveLength.getTextAsInt());
		options.setWallGraveWidthLeft(wallGraveWidthLeft.getTextAsInt());
		options.setWallGraveWidthLeftTop(wallGraveWidthLeftTop.getTextAsInt());
		options.setWallGraveWidthLeftNew(wallGraveWidthLeftNew.getTextAsInt());
		options.setWallGraveWidthLeftTopNew(wallGraveWidthLeftTopNew.getTextAsInt());
		options.setWallGraveWidthRight(wallGraveWidthRight.getTextAsInt());
		options.setWallGraveWidthRightTop(wallGraveWidthRightTop.getTextAsInt());
		options.setWallGraveWidthRightNew(wallGraveWidthRightNew.getTextAsInt());
		options.setWallGraveWidthRightTopNew(wallGraveWidthRightTopNew.getTextAsInt());
		options.setNrOfGravePlacesOld(nrOfGravePlacesOld.getTextAsInt());
		options.setNrOfGraveRowsOld(nrOfGraveRowsOld.getTextAsInt());
		options.setNrOfWallGravesLeftSideOld(nrOfWallGravesLeftSide.getTextAsInt());
		options.setNrOfWallGravesLeftTopOld(nrOfWallGravesLeftTop.getTextAsInt());
		options.setNrOfWallGravesRightSideOld(nrOfWallGravesRightSide.getTextAsInt());
		options.setNrOfWallGravesRightTopOld(nrOfWallGravesRightTop.getTextAsInt());
		options.setMaxImageWidthForTooltip(maxImageWidthForTooltip.getTextAsInt());
		options.setMaxImageWidthForDetail(maxImageWidthForDetail.getTextAsInt());
		super.okPressed();
	}

	private void createOptionsComponents(final Composite comp) {
		int style = SWT.NONE;
		WidgetFactory.createLabel(comp, "Grablänge:");
		normalGraveLength = WidgetFactory.createIntegerText(comp, style, options.getNormalGraveLength());

		WidgetFactory.createLabel(comp, "Grabbreite:");
		normalGraveWidth = WidgetFactory.createIntegerText(comp, style, options.getNormalGraveWidth());

		WidgetFactory.createLabel(comp, "Grablänge Wandgrab:");
		wallGraveLength = WidgetFactory.createIntegerText(comp, style, options.getWallGraveLength());

		WidgetFactory.createLabel(comp, "Grabbreite Wandgrab links:");
		wallGraveWidthLeft = WidgetFactory.createIntegerText(comp, style, options.getWallGraveWidthLeft());

		WidgetFactory.createLabel(comp, "Grabbreite Wandgrab links oben:");
		wallGraveWidthLeftTop = WidgetFactory.createIntegerText(comp, style, options.getWallGraveWidthLeftTop());

		WidgetFactory.createLabel(comp, "Grabbreite Wandgrab links neuer Friedhof:");
		wallGraveWidthLeftNew = WidgetFactory.createIntegerText(comp, style, options.getWallGraveWidthLeftNew());

		WidgetFactory.createLabel(comp, "Grabbreite Wandgrab links oben neuer Friedhof:");
		wallGraveWidthLeftTopNew = WidgetFactory.createIntegerText(comp, style, options.getWallGraveWidthLeftTopNew());

		WidgetFactory.createLabel(comp, "Grabbreite Wandgrab rechts:");
		wallGraveWidthRight = WidgetFactory.createIntegerText(comp, style, options.getWallGraveWidthRight());

		WidgetFactory.createLabel(comp, "Grabbreite Wandgrab rechts oben:");
		wallGraveWidthRightTop = WidgetFactory.createIntegerText(comp, style, options.getWallGraveWidthRightTop());

		WidgetFactory.createLabel(comp, "Grabbreite Wandgrab rechts neuer Friedhof:");
		wallGraveWidthRightNew = WidgetFactory.createIntegerText(comp, style, options.getWallGraveWidthRightNew());

		WidgetFactory.createLabel(comp, "Grabbreite Wandgrab rechts oben neuer Friedhof:");
		wallGraveWidthRightTopNew = WidgetFactory.createIntegerText(comp, style, options.getWallGraveWidthRightTopNew());

		WidgetFactory.createLabel(comp, "Gräber pro Reihe:");
		nrOfGravePlacesOld = WidgetFactory.createIntegerText(comp, style, options.getNrOfGravePlacesOld());

		WidgetFactory.createLabel(comp, "Reihen:");
		nrOfGraveRowsOld = WidgetFactory.createIntegerText(comp, style, options.getNrOfGraveRowsOld());

		WidgetFactory.createLabel(comp, "Anzahl Wandgräber linke Seite:");
		nrOfWallGravesLeftSide = WidgetFactory.createIntegerText(comp, style, options.getNrOfWallGravesLeftSideOld());

		WidgetFactory.createLabel(comp, "Anzahl Wandgräber linke Seite oben:");
		nrOfWallGravesLeftTop = WidgetFactory.createIntegerText(comp, style, options.getNrOfWallGravesLeftTopOld());

		WidgetFactory.createLabel(comp, "Anzahl Wandgräber rechte Seite:");
		nrOfWallGravesRightSide = WidgetFactory.createIntegerText(comp, style, options.getNrOfWallGravesRightSideOld());

		WidgetFactory.createLabel(comp, "Anzahl Wandgräber rechte Seite oben:");
		nrOfWallGravesRightTop = WidgetFactory.createIntegerText(comp, style, options.getNrOfWallGravesRightTopOld());

		WidgetFactory.createLabel(comp, "Maximale Pixelbreite für Tooltip Bild:");
		maxImageWidthForTooltip = WidgetFactory.createIntegerText(comp, style, options.getMaxImageWidthForTooltip());

		WidgetFactory.createLabel(comp, "Maximale Pixelbreite für Detail Bild:");
		maxImageWidthForDetail = WidgetFactory.createIntegerText(comp, style, options.getMaxImageWidthForDetail());

	}
}

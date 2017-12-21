package at.am.friedman.gui.customWidgets;

import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Text;

import at.am.friedman.data.enums.GraveType;
import at.am.friedman.data.enums.GraveZone;
import at.am.friedman.data.enums.MultiGravePosition;
import at.am.friedman.gui.utils.GravePosition;
import at.am.friedman.gui.utils.GraveRotation;
import at.am.friedman.gui.utils.WidgetFactory;
import at.am.friedman.shared.CemeteryOptionsInterface;
import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.shared.GraveInterface;
import at.am.friedman.shared.MultiGraveInterface;
import at.am.common.logging.LogFactory;

/**
 * class draws the whole overview of the cemetery (all Graves)
 * 
 * @author Alois
 * 
 */
public class GraphicalMainOverviewComposite extends Composite {

	private final Logger logger = LogFactory.makeLogger();
	private final CemeteryOptionsInterface options = DataProviderFactory.createDataProvider().getCemeteryOptions();

	public GraphicalMainOverviewComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());

		createContent(loadGraves(), SWT.NONE);

	}

	private List<GraveInterface> loadGraves() {
		return DataProviderFactory.createDataProvider().getAllGraves();
	}

	private void createContent(List<GraveInterface> graves, int style) {

		final ScrolledComposite scrollComp = WidgetFactory.createScrollComposite(this, new FillLayout());

		final Composite cemeteryComp = WidgetFactory.createCompositeWithHorizontalAndVerticalAligment(scrollComp,
				new GridLayout(6, false));

		createLeftTopWallGravesNew(cemeteryComp, graves);
		Composite topWay = new Composite(cemeteryComp, SWT.NONE);
		createRightTopWallGravesNew(cemeteryComp, graves);

		final Composite rightDataComposite = WidgetFactory
				.createCompositeWithHorizontalAndVerticalAligment(cemeteryComp, new GridLayout(1, false));
		GridData emptyData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_BEGINNING
				| GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		emptyData.verticalSpan = 4;
		rightDataComposite.setLayoutData(emptyData);
		final Composite runnableComposite = new Composite(rightDataComposite, SWT.NONE);
		runnableComposite.setBounds(new Rectangle(0, 0, 300, 300));
		createContentforRunnableComposite(runnableComposite, graves);

		Composite leftWallGravesNew = WidgetFactory.createCompositeWithHorizontalAndVerticalAligment(cemeteryComp,
				WidgetFactory.createGridLayoutWithoutSpacing(1, true));
		createLeftWallGravesNew(leftWallGravesNew, graves);

		Composite leftGravesNew = WidgetFactory.createCompositeWithHorizontalAndVerticalAligment(cemeteryComp,
				WidgetFactory.createGridLayoutWithoutSpacing(15, true));
		createLeftGravesNew(leftGravesNew, graves, GraveZone.Links);

		Composite centerWayWithRowCountNew = WidgetFactory.createCompositeWithHorizontalAndVerticalAligment(
				cemeteryComp, WidgetFactory.createGridLayoutWithoutSpacing(1, true));
		createCenterNew(centerWayWithRowCountNew);

		Composite rightGravesNew = WidgetFactory.createCompositeWithHorizontalAndVerticalAligment(cemeteryComp,
				WidgetFactory.createGridLayoutWithoutSpacing(15, true));
		createRightGravesNew(rightGravesNew, graves, GraveZone.Rechts);

		Composite rightWallGravesNew = WidgetFactory.createCompositeWithHorizontalAndVerticalAligment(cemeteryComp,
				WidgetFactory.createGridLayoutWithoutSpacing(1, true));
		createRightWallGravesNew(rightWallGravesNew, graves);

		// old cemetery

		createLeftTopWallGraves(cemeteryComp, graves);
		topWay = new Composite(cemeteryComp, SWT.NONE);
		createRightTopWallGraves(cemeteryComp, graves);

		Composite leftWallGraves = WidgetFactory.createCompositeWithHorizontalAndVerticalAligment(cemeteryComp,
				WidgetFactory.createGridLayoutWithoutSpacing(1, true));
		createLeftWallGraves(leftWallGraves, graves);

		Composite leftGraves = WidgetFactory.createCompositeWithHorizontalAndVerticalAligment(cemeteryComp,
				WidgetFactory.createGridLayoutWithoutSpacing(15, true));
		createLeftGraves(leftGraves, graves, GraveZone.Links);

		Composite centerWayWithRowCount = WidgetFactory.createCompositeWithHorizontalAndVerticalAligment(cemeteryComp,
				WidgetFactory.createGridLayoutWithoutSpacing(1, true));
		createCenter(centerWayWithRowCount);

		Composite rightGraves = WidgetFactory.createCompositeWithHorizontalAndVerticalAligment(cemeteryComp,
				WidgetFactory.createGridLayoutWithoutSpacing(15, true));
		createRightGraves(rightGraves, graves, GraveZone.Rechts);

		Composite rightWallGraves = WidgetFactory.createCompositeWithHorizontalAndVerticalAligment(cemeteryComp,
				WidgetFactory.createGridLayoutWithoutSpacing(1, true));
		createRightWallGraves(rightWallGraves, graves);

		scrollComp.setContent(cemeteryComp);
		scrollComp.setExpandHorizontal(true);
		scrollComp.setExpandVertical(true);
		cemeteryComp.layout();
		scrollComp.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Rectangle r = scrollComp.getClientArea();
				scrollComp.setMinSize(cemeteryComp.computeSize(r.width, SWT.DEFAULT));
			}
		});
		ScrollBar vBar = scrollComp.getVerticalBar();
		vBar.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.detail == SWT.DRAG) {
					runnableComposite.setBounds(scrollComp.getOrigin().x, scrollComp.getOrigin().y, 300, 300);

				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void createContentforRunnableComposite(Composite runnableComposite, List<GraveInterface> graves) {
		runnableComposite.setLayout(new GridLayout(1, true));
		Composite colorLegend = new Composite(runnableComposite, SWT.NONE);
		colorLegend.setLayout(new GridLayout(2, false));
		Display display = Display.getCurrent();
		List<Entry<Integer, String>> colors = options.getGraveColors();
		for (int i = 0; i < colors.size(); i++) {
			Entry<Integer, String> entry = colors.get(i);
			Composite colComp = new Composite(colorLegend, SWT.BORDER);
			colComp.setBackground(display.getSystemColor(entry.getKey()));
			GridData d = new GridData();
			d.heightHint = 20;
			d.widthHint = 20;
			colComp.setLayoutData(d);

			Text text = new Text(colorLegend, SWT.NONE);
			text.setText(entry.getValue());
			text.setEditable(false);
			d = new GridData();
			d.heightHint = 20;
			d.widthHint = 200;
		}
		Label lbExtern = new Label(colorLegend, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		lbExtern.setLayoutData(data);

		GraveInterface externalGrave = getOrCreateGraveByRowAndPlace(graves, "1", "1", GraveZone.Externe);
		GraveComponent extern = new GraveComponent(colorLegend, SWT.NONE, 150, 80, externalGrave);
		extern.getGraveLabel().setText("Extern");
		GridData externLayoutData = (GridData) extern.getLayoutData();
		externLayoutData.horizontalSpan = 2;
	}

	private void createRightWallGravesNew(Composite rightWallGravesNew, List<GraveInterface> graves) {
		int endNrOfGrave = 85;
		int startNrOfGrave = 66;
		int style = SWT.NONE;
		int height = options.getWallGraveLength();
		int width = options.getWallGraveWidthRightNew() - 2;

		for (int i = endNrOfGrave; i >= startNrOfGrave; i--) {
			String actualPlace = Integer.toString(i);
			String actualRow = "1";
			GraveInterface actualGrave = getOrCreateGraveByRowAndPlace(graves, actualRow, actualPlace,
					GraveZone.Wandgrab_Rechts);
			new WallGraveComposite(rightWallGravesNew, style, actualGrave, GraveRotation.rightWallGraveRotation, width,
					height);

		}

	}

	private void createRightGravesNew(Composite rightGravesNew, List<GraveInterface> graves, GraveZone zone) {
		int startRow = 28;
		int endRow = 38;
		int columns = options.getNrOfGravePlacesOld();

		int height = options.getNormalGraveLength();
		int width = options.getNormalGraveWidth();

		for (int i = startRow * columns; i < endRow * columns; i++) {
			String actualRow = Integer.toString(endRow - ((i / columns) + 1) + startRow);

			String actualColumn = Integer.toString((i % columns) + 1);
			GraveInterface actualGrave = getOrCreateGraveByRowAndPlace(graves, actualRow, actualColumn, zone);

			height = options.getNormalGraveLength();
			new GraveComponent(rightGravesNew, SWT.NONE, width, height, actualGrave);

		}

	}

	private void createCenterNew(Composite centerWayWithRowCountNew) {
		int startRow = 28;
		int endRow = 38;
		for (int i = startRow; i < endRow; i++) {
			Composite comp = new Composite(centerWayWithRowCountNew, SWT.NONE);
			GridData gridData = new GridData(options.getNormalGraveWidth(), options.getNormalGraveLength());
			comp.setLayoutData(gridData);
			comp.setLayout(new FormLayout());

			FormData data = new FormData(15, 15);
			data.top = new FormAttachment(30);
			Label lb = new Label(comp, SWT.NONE);
			lb.setText(Integer.toString(endRow - i + 27));
			lb.setLayoutData(data);

		}

	}

	private void createLeftGravesNew(Composite leftGravesNew, List<GraveInterface> graves, GraveZone zone) {
		int startRow = 28;
		int endRow = 38;
		int columns = options.getNrOfGravePlacesOld();
		int height = options.getNormalGraveLength();
		int width = options.getNormalGraveWidth();

		for (int i = startRow * columns; i < endRow * columns; i++) {
			String actualRow = Integer.toString(endRow - ((i / columns) + 1) + startRow);
			String actualColumn = Integer.toString(columns + 1 - ((i % columns) + 1));

			// load grave
			GraveInterface actualGrave = getOrCreateGraveByRowAndPlace(graves, actualRow, actualColumn, zone);

			new GraveComponent(leftGravesNew, SWT.NONE, width, height, actualGrave);

		}

	}

	private void createLeftWallGravesNew(Composite leftWallGravesNew, List<GraveInterface> graves) {
		int startNrOfGrave = 69;
		int endNrOfGrave = 88;
		int style = SWT.NONE;
		int height = options.getWallGraveLength();
		int width = options.getWallGraveWidthLeftNew() - 3;

		for (int i = endNrOfGrave; i >= startNrOfGrave; i--) {
			String actualPlace = Integer.toString(i);
			String actualRow = "1";
			GraveInterface actualGrave = getOrCreateGraveByRowAndPlace(graves, actualRow, actualPlace,
					GraveZone.Wandgrab_Links);
			new WallGraveComposite(leftWallGravesNew, style, actualGrave, GraveRotation.leftWallGraveRotation, width,
					height);

		}

	}

	private void createRightTopWallGravesNew(Composite topGraves, List<GraveInterface> graves) {
		int startOfGraves = 86;
		int endOfGraves = 100;
		Composite gravesComp = WidgetFactory.createCompositeWithHorizontalAndVerticalAligment(topGraves,
				WidgetFactory.createGridLayoutWithoutSpacing(endOfGraves - startOfGraves + 1, true));
		GridData data = new GridData();
		data.horizontalSpan = 2;
		gravesComp.setLayoutData(data);
		int style = SWT.NONE;
		int height = options.getWallGraveLength();
		int width = options.getWallGraveWidthRightTopNew();

		for (int i = endOfGraves; i >= startOfGraves; i--) {
			String actualPlace = Integer.toString(i);
			String actualRow = "1";
			GraveInterface actualGrave = getOrCreateGraveByRowAndPlace(graves, actualRow, actualPlace,
					GraveZone.Wandgrab_Rechts);
			new WallGraveComposite(gravesComp, style, actualGrave, GraveRotation.normalGraveRotation, width, height);

		}

	}

	private void createRightTopWallGraves(Composite topGraves, List<GraveInterface> graves) {
		int startOfGraves = options.getNrOfWallGravesRightSideOld() + 1;
		int endOfGraves = startOfGraves + options.getNrOfWallGravesRightTopOld();
		Composite gravesComp = WidgetFactory.createCompositeWithHorizontalAndVerticalAligment(topGraves,
				WidgetFactory.createGridLayoutWithoutSpacing(endOfGraves - startOfGraves + 1, true));
		GridData data = new GridData();
		data.horizontalSpan = 2;
		gravesComp.setLayoutData(data);
		int style = SWT.NONE;
		int height = options.getWallGraveLength();
		int width = options.getWallGraveWidthRightTop();

		for (int i = endOfGraves; i >= startOfGraves; i--) {
			String actualPlace = Integer.toString(i);
			String actualRow = "1";
			GraveInterface actualGrave = getOrCreateGraveByRowAndPlace(graves, actualRow, actualPlace,
					GraveZone.Wandgrab_Rechts);
			new WallGraveComposite(gravesComp, style, actualGrave, GraveRotation.normalGraveRotation, width, height);

		}

	}

	private void createLeftTopWallGravesNew(Composite cemeteryComp, List<GraveInterface> graves) {
		int startOfGraves = 89;
		int endOfGraves = 103;
		Composite gravesComp = WidgetFactory.createCompositeWithHorizontalAndVerticalAligment(cemeteryComp, SWT.BORDER,
				WidgetFactory.createGridLayoutWithoutSpacing(endOfGraves - startOfGraves + 1, true), SWT.FILL,
				SWT.FILL);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		gravesComp.setLayoutData(data);
		int style = SWT.NONE;
		int height = options.getWallGraveLength();
		int width = options.getWallGraveWidthLeftTopNew();

		for (int i = startOfGraves; i <= endOfGraves; i++) {
			String actualPlace = Integer.toString(i);
			String actualRow = "1";
			GraveInterface actualGrave = getOrCreateGraveByRowAndPlace(graves, actualRow, actualPlace,
					GraveZone.Wandgrab_Links);
			new WallGraveComposite(gravesComp, style, actualGrave, GraveRotation.normalGraveRotation, width, height);

		}

	}

	private void createLeftTopWallGraves(Composite topGraves, List<GraveInterface> graves) {
		int startOfGraves = options.getNrOfWallGravesLeftSideOld() + 1;
		int endOfGraves = startOfGraves + options.getNrOfWallGravesLeftTopOld();
		Composite gravesComp = WidgetFactory.createCompositeWithHorizontalAndVerticalAligment(topGraves, SWT.BORDER,
				WidgetFactory.createGridLayoutWithoutSpacing(endOfGraves - startOfGraves + 1, true), SWT.FILL,
				SWT.FILL);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		gravesComp.setLayoutData(data);
		int style = SWT.NONE;
		int height = options.getWallGraveLength();
		int width = options.getWallGraveWidthLeftTop();

		for (int i = startOfGraves; i <= endOfGraves; i++) {
			String actualPlace = Integer.toString(i);
			String actualRow = "1";
			GraveInterface actualGrave = getOrCreateGraveByRowAndPlace(graves, actualRow, actualPlace,
					GraveZone.Wandgrab_Links);
			new WallGraveComposite(gravesComp, style, actualGrave, GraveRotation.normalGraveRotation, width, height);

		}

	}

	private void createLeftWallGraves(Composite leftWallGraves, List<GraveInterface> graves) {
		int nrOfGrave = options.getNrOfWallGravesLeftSideOld();
		int style = SWT.NONE;
		int height = options.getWallGraveLength();
		int width = options.getWallGraveWidthLeft();

		for (int i = nrOfGrave; i > 0; i--) {
			String actualPlace = Integer.toString(i);
			String actualRow = "1";
			GraveInterface actualGrave = getOrCreateGraveByRowAndPlace(graves, actualRow, actualPlace,
					GraveZone.Wandgrab_Links);
			new WallGraveComposite(leftWallGraves, style, actualGrave, GraveRotation.leftWallGraveRotation, width,
					height);

		}

	}

	private void createRightWallGraves(Composite rightWallGraves, List<GraveInterface> graves) {
		int nrOfGrave = options.getNrOfWallGravesRightSideOld();
		int style = SWT.NONE;
		int height = options.getWallGraveLength();
		int width = options.getWallGraveWidthRight();
		Composite wallGrave;
		for (int i = nrOfGrave; i > 0; i--) {
			String actualPlace = Integer.toString(i);
			String actualRow = "1";
			GraveInterface actualGrave = getOrCreateGraveByRowAndPlace(graves, actualRow, actualPlace,
					GraveZone.Wandgrab_Rechts);
			new WallGraveComposite(rightWallGraves, style, actualGrave, GraveRotation.rightWallGraveRotation, width,
					height);

		}

	}

	private void createRightGraves(Composite rightGraves, List<GraveInterface> graves, GraveZone zone) {
		int rows = options.getNrOfGraveRowsOld() + 1;
		int columns = options.getNrOfGravePlacesOld();

		int height = options.getNormalGraveLength();
		int width = options.getNormalGraveWidth();

		for (int i = 0; i < rows * columns; i++) {
			String actualRow = Integer.toString(rows + 1 - ((i / columns) + 1));
			if (actualRow.equals("28")) {
				actualRow = "27I";
			}
			String actualColumn = Integer.toString((i % columns) + 1);
			GraveInterface actualGrave = getOrCreateGraveByRowAndPlace(graves, actualRow, actualColumn, zone);
			// special handling
			if (actualGrave.getRow().equals("27") || actualGrave.getRow().equals("27I")) {
				actualGrave.setType(GraveType.Urnengrab);
				height = options.getSmallGraveLength();
			} else {
				height = options.getNormalGraveLength();
			}

			new GraveComponent(rightGraves, SWT.NONE, width, height, actualGrave);

		}

	}

	private void createCenter(Composite parent) {
		int rows = options.getNrOfGraveRowsOld();
		for (int i = 1; i <= rows; i++) {
			Composite comp = new Composite(parent, SWT.NONE);
			GridData gridData = new GridData(options.getNormalGraveWidth(), options.getNormalGraveLength());
			comp.setLayoutData(gridData);
			comp.setLayout(new FormLayout());

			FormData data = new FormData(15, 15);
			data.top = new FormAttachment(30);
			Label lb = new Label(comp, SWT.NONE);
			lb.setText(Integer.toString(rows + 1 - i));
			lb.setLayoutData(data);

		}
	}

	private void createLeftGraves(Composite leftGraves, List<GraveInterface> graves, GraveZone zone) {
		int rows = options.getNrOfGraveRowsOld();
		int columns = options.getNrOfGravePlacesOld();
		int height = options.getNormalGraveLength();
		int width = options.getNormalGraveWidth();

		for (int i = 0; i < rows * columns; i++) {
			String actualRow = Integer.toString(rows + 1 - ((i / columns) + 1));
			String actualColumn = Integer.toString(columns + 1 - ((i % columns) + 1));

			// load grave
			GraveInterface actualGrave = getOrCreateGraveByRowAndPlace(graves, actualRow, actualColumn, zone);

			new GraveComponent(leftGraves, SWT.NONE, width, height, actualGrave);

		}

	}

	/**
	 * 
	 * @param graves
	 * @param actualRow
	 * @param actualColumn
	 * @param zone
	 * @return the specified Grave, create a new if not existing
	 */
	private GraveInterface getOrCreateGraveByRowAndPlace(List<GraveInterface> graves, String actualRow,
			String actualColumn, GraveZone zone) {
		for (GraveInterface grave : graves) {
			if (grave.getRow().equals(actualRow) && grave.getPlace().equals(actualColumn)
					&& grave.getZone().equals(zone)) {
				return grave;
			}
		}
		GraveInterface emptyGrave = DataProviderFactory.createDataProvider().getNewGrave();
		emptyGrave.setRow(actualRow);
		emptyGrave.setPlace(actualColumn);
		emptyGrave.setZone(zone);
		emptyGrave.setType(GraveType.Normalgrab);
		emptyGrave.setOffset(0);
		emptyGrave.setRuntime(10);
		emptyGrave.setStarttime(new Date(0).getTime());

		DataProviderFactory.createDataProvider().addOrUpdateGrave(emptyGrave);
		return emptyGrave;
	}

}

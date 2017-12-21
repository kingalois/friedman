package at.am.friedman.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

public class GuiTest {

	@Test
	public void testComponents() {
		// testGraveComponent();

		// testCemeteryZone();
		// testLayout();
		testGridLayout();

	}

	private void testGridLayout() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		Composite content = new Composite(shell, SWT.None);

		GridLayout layout = new GridLayout(2, true);
		content.setLayout(layout);

		final Composite tile = new Composite(content, SWT.BORDER);
		GridData data = new GridData();

		tile.setLayout(new FormLayout());

		final Composite grave = new Composite(tile, SWT.BORDER);
		FormData formData = new FormData();

		formData.top = new FormAttachment(50);
		formData.bottom = new FormAttachment(100);
		formData.left = new FormAttachment(25);
		formData.right = new FormAttachment(75);
		grave.setLayoutData(formData);

		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		tile.setLayoutData(data);

		Color color = display.getSystemColor(SWT.COLOR_GREEN);
		content.setBackground(color);

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}

	private void testLayout() {
		Display display = new Display();
		Shell shell = new Shell(display);

		int width = 600;
		int height = 600;

		int style = SWT.BORDER;

		Composite oldCemetery = new Composite(shell, style);
		oldCemetery.setSize(width, height);

		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		oldCemetery.setLayout(rowLayout);

		Composite topGraveComposite = new Composite(oldCemetery, style);
		topGraveComposite.setLayoutData(new RowData(width - 15, 30));

		Composite centerComposite = new Composite(oldCemetery, style);
		centerComposite.setLayoutData(new RowData(width, height - 200));
		centerComposite.setLayout(new GridLayout(3, false));

		Composite leftCenterComposite = new Composite(centerComposite, style);
		leftCenterComposite.setLayoutData(new GridData(30, height - 220));

		Composite centerGraveComposite = new Composite(centerComposite, style);
		centerGraveComposite.setLayoutData(new GridData(width - 100, height - 220));

		Composite rightCenterComposite = new Composite(centerComposite, style);
		rightCenterComposite.setLayoutData(new GridData(30, height - 220));

		Composite bottomGraveComposite = new Composite(oldCemetery, style);
		bottomGraveComposite.setLayoutData(new RowData(width, 30));

		oldCemetery.layout();
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}

	// private void testCemeteryZone() {
	// Display display = new Display();
	// Shell shell = new Shell(display);
	// int width = 40;
	// int height = 40;
	//
	// CemeteryZoneComponent zone = new CemeteryZoneComponent(shell, SWT.NONE,
	// 15, 5);
	// zone.setSize(600, 600);
	// for (int i = 0; i < 15 * 30; i++) {
	// GridData data = new GridData(width, height);
	// GraveComponent comp;
	// if (i == 0) {
	// comp = new GraveComponent(zone, SWT.NONE, null,
	// GravePosition.RIGHT, GraveRotation.normalGraveRotation);
	// } else if (i == 1) {
	// comp = new GraveComponent(zone, SWT.NONE, null,
	// GravePosition.LEFT, GraveRotation.normalGraveRotation);
	// } else {
	// comp = new GraveComponent(zone, SWT.NONE, null,
	// GravePosition.CENTER, GraveRotation.normalGraveRotation);
	// }
	// comp.setLayoutData(data);
	// }
	//
	// zone.layout();
	// shell.pack();
	// shell.open();
	// while (!shell.isDisposed()) {
	// if (!display.readAndDispatch()) {
	// display.sleep();
	// }
	// }
	//
	// }

	// private void testGraveComponent() {
	// Display display = new Display();
	// Shell shell = new Shell(display);
	// GraveComponent comp = new GraveComponent(shell, SWT.None, null,
	// GravePosition.RIGHT, GraveRotation.normalGraveRotation);
	//
	// shell.pack();
	// shell.open();
	// while (!shell.isDisposed()) {
	// if (!display.readAndDispatch()) {
	// display.sleep();
	// }
	// }
	// }

}

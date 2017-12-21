package at.am.friedman.gui.utils;

import java.util.Calendar;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import at.am.friedman.commons.utils.StringUtils;
import at.am.friedman.gui.customWidgets.IntegerText;

public class WidgetFactory {

	public static Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		return label;
	}

	public static Label createHorizontalSeparator(Composite parent, GridData data) {
		Label separator = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
		separator.setLayoutData(data);
		return separator;
	}

	public static Text createText(Composite parent, int style, String text) {
		return createText(parent, style, text, true);
	}

	public static IntegerText createIntegerText(Composite comp, int style, int value) {
		IntegerText intText = new IntegerText(comp, style);
		intText.setInteger(value);
		return intText;
	}

	public static GridData createGridData(Boolean grabExcessHorizontalSpace, int horizontalAlignment) {
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = grabExcessHorizontalSpace;
		gridData.horizontalAlignment = horizontalAlignment;
		return gridData;
	}

	public static DateTime createDateTime(Composite parent, int style, Calendar cal) {
		DateTime dateTime = new DateTime(parent, style);
		dateTime.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		dateTime.setLayoutData(createGridData(true, GridData.FILL));
		return dateTime;
	}

	public static Combo createCombo(Composite parent, int style, Enum<?>[] data) {
		return createCombo(parent, style, data, true);
	}

	public static <T> Combo createCombo(Composite container, int style, List<T> list) {
		Combo combo = new Combo(container, style);
		for (T data : list) {

			combo.add(data.toString());
		}
		combo.setLayoutData(createGridData(true, GridData.FILL));
		return combo;
	}

	public static Text createText(Composite parent, int style, String text, boolean enabled) {
		Text txtText = new Text(parent, style);
		txtText.setLayoutData(createGridData(true, GridData.FILL));
		txtText.setText(text);
		txtText.setEnabled(enabled);
		return txtText;
	}

	public static Combo createCombo(Composite parent, int style, Enum<?>[] data, boolean enabled) {
		Combo combo = new Combo(parent, style);
		for (Enum<?> pos : data) {

			combo.add(pos.name());
		}

		combo.setLayoutData(createGridData(true, GridData.FILL));
		combo.setEnabled(enabled);
		return combo;
	}

	public static TableViewerColumn createTableViewerColumn(TableViewer viewer, String title, int bound) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.None);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;

	}

	public static String formatLongTimestamp(long timestamp) {
		return StringUtils.stringifyDateLong(timestamp);

	}

	public static ScrolledComposite createScrollComposite(Composite parent, Layout layout) {
		final ScrolledComposite scrollComp = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL);
		scrollComp.setLayout(layout);
		return scrollComp;
	}

	public static Composite createCompositeWithHorizontalAndVerticalAligment(Composite parent, Layout layout) {
		return createCompositeWithHorizontalAndVerticalAligment(parent, SWT.BORDER, layout, SWT.BEGINNING, SWT.FILL);
	}

	public static Composite createCompositeWithHorizontalAndVerticalAligment(Composite parent, int style, Layout layout, int horizontalAlign, int verticalAlign) {
		final Composite composite = new Composite(parent, style);
		composite.setLayout(layout);
		GridData gridData = new GridData(horizontalAlign, verticalAlign, false, false);
		composite.setLayoutData(gridData);
		return composite;
	}

	public static GridLayout createGridLayoutWithoutSpacing(int numColumns, boolean makeColumnsEqualWidth) {
		GridLayout layout = new GridLayout(numColumns, makeColumnsEqualWidth);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginTop = 0;
		layout.marginBottom = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		return layout;
	}

	public static Button createButton(Composite parent, int style) {
		Button button = new Button(parent, style);
		return button;
	}

}

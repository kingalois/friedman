/*******************************************************************************
 * Copyright (c) 2010 - 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Lars Vogel <lars.Vogel@gmail.com> - Bug 419770
 *******************************************************************************/
package at.am.friedman.gui.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import at.am.friedman.shared.DataProviderFactory;

public class DeleteGraveHandler {
	@Execute
	public void execute(Shell shell) {
		if (MessageDialog.openConfirm(shell, "Bestätigung", "Das Grab wirklich löschen?\n" + DataProviderFactory.createDataProvider().getActualGrave().getId())) {
			DataProviderFactory.createDataProvider().deleteGrave(DataProviderFactory.createDataProvider().getActualGrave());
			DataProviderFactory.createDataProvider().setActualGrave(null);
		}
	}

	@CanExecute
	public boolean canExecute() {
		return DataProviderFactory.createDataProvider().getActualGrave() != null;
	}
}

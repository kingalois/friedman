/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
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
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import at.am.friedman.gui.dialog.CreateOrEditDiedPersonDialog;
import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.shared.DiedPersonInterface;

public class EditDiedPersonHandler {

	@Execute
	public void execute(Shell shell) {
		execute(shell, DataProviderFactory.createDataProvider().getActualDiedPerson());
	}

	public static void execute(Shell shell, DiedPersonInterface diedPerson) {
		CreateOrEditDiedPersonDialog dialog = new CreateOrEditDiedPersonDialog(shell);
		dialog.setDiedPerson(diedPerson);
		dialog.create();

		if (dialog.open() == Window.OK) {
			DataProviderFactory.createDataProvider().addOrUpdateDiedPerson(dialog.getDiedPerson());
		}
	}

	@CanExecute
	public boolean canExecute() {
		return DataProviderFactory.createDataProvider().getActualDiedPerson() != null;
	}

}

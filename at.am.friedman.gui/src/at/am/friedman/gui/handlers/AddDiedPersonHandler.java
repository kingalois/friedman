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

import java.util.logging.Logger;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import at.am.friedman.gui.dialog.CreateOrEditDiedPersonDialog;
import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.shared.GraveInterface;
import at.am.common.logging.LogFactory;

public class AddDiedPersonHandler {

	private static Logger	log	= LogFactory.makeLogger();

	@Execute
	public static void execute ( Shell shell ) {
		CreateOrEditDiedPersonDialog dialog = new CreateOrEditDiedPersonDialog(shell);
		dialog.create();
		if ( dialog.open() == Window.OK ) {
			log.info("New DiedPerson: " + dialog.getDiedPerson().toString());
			DataProviderFactory.createDataProvider().addOrUpdateDiedPerson(dialog.getDiedPerson());
		}
	}

	public static void execute ( Shell shell, GraveInterface grave ) {
		CreateOrEditDiedPersonDialog dialog = new CreateOrEditDiedPersonDialog(shell);
		dialog.setGrave(grave);
		dialog.create();
		if ( dialog.open() == Window.OK ) {
			log.info("New DiedPerson: " + dialog.getDiedPerson().toString());
			DataProviderFactory.createDataProvider().addOrUpdateDiedPerson(dialog.getDiedPerson());
		}
	}
}
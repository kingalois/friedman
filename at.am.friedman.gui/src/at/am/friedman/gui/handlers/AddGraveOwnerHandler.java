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

import at.am.friedman.gui.dialog.CreateOrEditGraveOwnerDialog;
import at.am.friedman.shared.DataProviderFactory;
import at.am.common.logging.LogFactory;

public class AddGraveOwnerHandler {

	private static Logger	log	= LogFactory.makeLogger();

	@Execute
	public static void execute ( Shell shell ) {
		CreateOrEditGraveOwnerDialog dialog = new CreateOrEditGraveOwnerDialog(shell);
		dialog.create();
		if ( dialog.open() == Window.OK ) {
			log.info("New Owner: " + dialog.getGraveOwner().toString());
			DataProviderFactory.createDataProvider().addOrUpdateGravOwner(dialog.getGraveOwner());
		}
	}
}
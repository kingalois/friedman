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
package at.am.friedman.gui.parts;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import at.am.friedman.gui.provider.GraveOwnerOverviewTreeContentProvider;
import at.am.friedman.gui.provider.GraveOwnerOverviewTreeLabelProvider;
import at.am.friedman.shared.CemeteryDataProviderInterface;
import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.shared.GraveOwnerInterface;
import at.am.common.logging.LogFactory;

public class OwnerOverviewTree implements ChangeListener {

	private TreeViewer						treeViewer;

	private static Logger					logger	= LogFactory.makeLogger();

	private CemeteryDataProviderInterface	provider;

	@PostConstruct
	public void createComposite ( Composite parent ) {
		parent.setLayout(new FillLayout());

		provider = DataProviderFactory.createDataProvider();


		treeViewer = new TreeViewer(parent, SWT.FILL | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		treeViewer.setContentProvider(new GraveOwnerOverviewTreeContentProvider());
		treeViewer.setLabelProvider(new GraveOwnerOverviewTreeLabelProvider());
		treeViewer.setInput(provider.getAllOwner());
		Tree tree = (Tree) treeViewer.getControl();
		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected ( SelectionEvent e ) {
				TreeItem item = (TreeItem) e.item;
				DataProviderFactory.createDataProvider().setActualGraveOwner((GraveOwnerInterface) item.getData());
				logger.info("item changed: " + item.getData().toString());
			}
		});

	}

	@Focus
	public void setFocus ( ) {

	}

	@Override
	public void stateChanged ( ChangeEvent event ) {
		if ( event.getSource() instanceof CemeteryDataProviderInterface ) {
			treeViewer.setInput(provider.getAllOwner());
			treeViewer.refresh();
		}
	}
}
package at.am.friedman.gui.handlers;

import java.io.File;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import at.am.friedman.gui.utils.FileUtils;
import at.am.friedman.shared.DiedPersonInterface;

import com.google.common.io.Files;

public class AttachImageToDiedPerson {
	@Execute
	public static String execute(Shell shell, String source, DiedPersonInterface diedPerson) {

		if (source != null) {
			// 1. zielverzeichnis erzeugen falls notwendig
			String destinationDirectory = "images";
			File f = new File(destinationDirectory);
			if (!f.exists()) {
				f.mkdir();
			}

			String destination = "images/" + diedPerson.getFirstName() + diedPerson.getSurname() + diedPerson.getDeathday() + "." + Files.getFileExtension(source);
			if (FileUtils.transferFile(source, destination)) {
				MessageBox mb = new MessageBox(shell);
				mb.setMessage("Bild wurde erfolgreich kopiert");
				mb.open();
				return destination;
			} else {
				MessageBox mb = new MessageBox(shell);
				mb.setMessage("Bild konnte nicht kopiert werden");
				mb.open();

			}

		}
		return null;
	}

}
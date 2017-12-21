package at.am.friedman.gui.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import at.am.common.logging.LogFactory;

import com.google.common.io.Files;

public class FileUtils {

	private static Logger log = LogFactory.makeLogger();

	public static boolean transferFile(String source, String destination) {
		try {
			Files.copy(new File(source), new File(destination));
			return true;
		} catch (IOException e) {
			log.log(Level.SEVERE, "cannot copy file from " + source + " to " + destination, e);
		}

		return false;
	}

}

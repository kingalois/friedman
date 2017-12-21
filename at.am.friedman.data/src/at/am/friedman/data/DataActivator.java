package at.am.friedman.data;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.common.io.Files;

import at.am.friedman.commons.utils.Constants;
import at.am.common.logging.LogFactory;

public class DataActivator implements BundleActivator {
	
	private static final Logger log = LogFactory.makeLogger(DataActivator.class.getName());

	@Override
	public void start(BundleContext context) throws Exception {
		try{
		File source = new File(Constants.DATA_FILE);
		if(source.exists()){
			File destination = new File(Constants.BACKUP_FOLDER);
			if(!destination.exists()){
				destination.mkdir();
			}
			destination = new File (Constants.BACKUP_FOLDER + Constants.DATA_FILE + "backup");
			Files.copy(source, destination);
		}
		}catch(IOException e){
			log.log(Level.SEVERE, "cannot make a backup copy ", e);
		}

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

}

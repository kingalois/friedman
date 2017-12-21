package at.am.friedman.update;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;

import at.am.friedman.commons.utils.Constants;
import at.am.friedman.commons.utils.DebugFlagHelper;
import at.am.friedman.update.shared.UpdateEntryInterface;
import at.am.friedman.update.shared.UpdateEntryManagerInterface;
import at.am.common.logging.LogFactory;

public class UpdateEntryManager implements UpdateEntryManagerInterface {
	
	private Collection<UpdateEntryInterface> updateEntries = new HashSet<>();
	private String updateFilePath = DebugFlagHelper.getStringDebugFlag("updatefilepath", Constants.UPDATESFILE);
	private String updateFilePathRoot = DebugFlagHelper.getStringDebugFlag("updatefilepathroot", Constants.UPDATESFILEROOT);
	private static final Logger log = LogFactory.makeLogger(UpdateEntryManager.class.getName());
	
	
	public UpdateEntryManager(){
		loadState();
		//addUpdateEntryForTest();
	}
	

	private void addUpdateEntryForTest() {
		UpdateEntry e = new UpdateEntry();
		e.setTitle("Test");
		e.setDescription("Test description");
		e.setConfirmed(false);
		updateEntries.add(e);
		
	}


	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryManagerInterface#haveNotConfirmedUpdate()
	 */
	@Override

	public boolean haveNotConfirmedUpdate() {
		for(at.am.friedman.update.shared.UpdateEntryInterface entry : updateEntries){
			if(!entry.isConfirmed()){
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryManagerInterface#getAllNonConfirmedUpadteEntries()
	 */
	@Override
	public Collection<UpdateEntryInterface> getAllNonConfirmedUpdateEntries(){
		List<UpdateEntryInterface> nonComfirmedUpdateEntries = new ArrayList<UpdateEntryInterface>();
		for(UpdateEntryInterface entry : updateEntries){
			if (!entry.isConfirmed()){
				nonComfirmedUpdateEntries.add(entry);
			}
		}
		return nonComfirmedUpdateEntries;
	}
	
	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryManagerInterface#getAllUpdateEntries()
	 */
	@Override
	public Collection<UpdateEntryInterface> getAllUpdateEntries(){
		return updateEntries;
	}
	
	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryManagerInterface#confirmAll()
	 */
	@Override
	public void confirmAll(){
		for(UpdateEntryInterface entry : updateEntries){
			entry.setConfirmed(true);
		}
		saveState();
	}
	
	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryManagerInterface#loadState()
	 */
	@Override
	public void loadState(){
		loadFileFromRoot();
		loadFileFromBundle();
		
			
		
		
	}
	
	private void loadFileFromBundle() {
		URL url;
		try {
			url = new URL(updateFilePath);
			InputStream in = url.openStream();
			Reader reader = new InputStreamReader(in);
			readMemento(reader);
		} catch (IOException e) {
		log.log(Level.INFO, "cannot load update files from bundle", e);
		}
	}


	private void readMemento(Reader r){
		try{
		IMemento memento = XMLMemento.createReadRoot(r);
		for(IMemento updateMemento : memento.getChildren("update")){
			UpdateEntry entry = new UpdateEntry();
			entry.loadState(updateMemento);
			updateEntries.add(entry);
		}
		}catch ( WorkbenchException e) {
			
			log.info("cannot find update file for path : " + e.getMessage());
		}
	}
	
	private void loadFileFromRoot() {
		try {
			Reader reader = new FileReader(updateFilePathRoot);
			readMemento(reader);
		} catch (FileNotFoundException e) {
			log.log(Level.INFO, "cannot laod updates from root" , e);
		}
		
	}


	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryManagerInterface#saveState()
	 */
	@Override
	public void saveState(){
		
		try {
			
			
			Writer fileWriter = new FileWriter(updateFilePathRoot);
			XMLMemento memento = XMLMemento.createWriteRoot("updates");
			for(at.am.friedman.update.shared.UpdateEntryInterface entry : updateEntries){
				entry.saveState(memento.createChild("update"));
			}
			memento.save(fileWriter);
		} catch (IOException e) {
			log.log(Level.INFO, "cannot save updates in root", e);
		}
		
		
	}

}

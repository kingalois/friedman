package at.am.friedman.update.shared;

import at.am.friedman.update.UpdateEntryManager;

public class UpdateEntryManagerFactory {
	
	private static UpdateEntryManagerInterface manager = null;
	
	public static UpdateEntryManagerInterface getUpdateEntryManagerSingleton(){
		if(manager == null){
			manager = new UpdateEntryManager();
		}
		return manager;
	}

}

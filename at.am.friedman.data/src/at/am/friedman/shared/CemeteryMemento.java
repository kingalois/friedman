package at.am.friedman.shared;

public interface CemeteryMemento {

	public void saveState();

	public void loadState();

	public void saveState(String path);

	public void loadState(String path);

}

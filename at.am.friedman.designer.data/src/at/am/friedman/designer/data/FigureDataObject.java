package at.am.friedman.designer.data;

import org.eclipse.ui.IMemento;

import at.am.friedman.shared.SaveableObject;

public class FigureDataObject implements SaveableObject {
	
	String picturePath;
	String name;
	
	
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		
	}
	
	
	@Override
	public void saveState(IMemento memento) {
		IMemento figure = memento.createChild("figure");
		figure.putString("name", name);
		figure.putString("picturePath", picturePath);
	}
	@Override
	public void restoreState(IMemento memento) {
		
		name = memento.getString("name");
		picturePath = memento.getString("picturePath");
	}

}

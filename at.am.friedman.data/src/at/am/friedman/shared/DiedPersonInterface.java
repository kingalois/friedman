package at.am.friedman.shared;

import java.util.Collection;

public interface DiedPersonInterface extends PersonInterface, Comparable<DiedPersonInterface> {

	int getAge();

	long getDeathday();

	long getDayOfInterment();

	int getInternalPosition();

	int getGraveId();

	String getDescription();

	Collection<String> getPictures();

	void addPicture(String pictureName);

	void removePicture(String pictureName);

	boolean havePictures();

	void removePicture();

}

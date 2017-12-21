package at.am.friedman.shared;

import java.util.Collection;

public interface DiedPersonInterface extends PersonInterface, Comparable<DiedPersonInterface> {

	int getAge();

	long getDeathday();

	long getDayOfInterment();

	int getInternalPosition();

	int getGraveId();

	void setAge(int age);

	void setDeathday(long deathday);

	void setDayOfInterment(long dayOfInterment);

	void setInternalPosition(int position);

	void setGraveId(int graveId);

	void setDescription(String description);

	String getDescription();

	Collection<String> getPictures();
	
	void addPicture(String pictureName);
	void removePicture(String pictureName);
	
	boolean havePictures();

	void removePicture();
	
}

package at.am.friedman.shared;

import java.util.List;
import java.util.Map.Entry;

public interface CemeteryOptionsInterface extends CemeteryMemento {

	public void setNrOfGraveRowsOld(int nrOfRows);

	public int getNrOfGraveRowsOld();

	public void setNrOfGravePlacesOld(int nrOfPlaces);

	public int getNrOfGravePlacesOld();

	public void setNrOfWallGravesLeftSideOld(int nrOfGraves);

	public int getNrOfWallGravesLeftSideOld();

	public void setNrOfWallGravesRightSideOld(int nrOfGraves);

	public int getNrOfWallGravesRightSideOld();

	public void setNrOfWallGravesLeftTopOld(int nrOfGraves);

	public int getNrOfWallGravesLeftTopOld();

	public void setNrOfWallGravesRightTopOld(int nrOfGraves);

	public int getNrOfWallGravesRightTopOld();

	public int getNormalGraveLength();

	public void setNormalGraveLength(int length);

	public int getNormalGraveWidth();

	public void setNormalGraveWidth(int width);

	public int getWallGraveLength();

	public void setWallGraveLength(int length);

	public int getWallGraveWidthLeft();
	public int getWallGraveWidthLeftTop();

	public void setWallGraveWidthLeft(int width);
	public void setWallGraveWidthRight(int width);
	public void setWallGraveWidthLeftTop(int width);
	public void setWallGraveWidthRightTop(int width);
	public void setWallGraveWidthLeftNew(int width);
	public void setWallGraveWidthRightNew(int width);
	public void setWallGraveWidthLeftTopNew(int width);
	public void setWallGraveWidthRightTopNew(int width);
	
	public int getWallGraveWidthLeftNew();

	public int getWallGraveWidthLeftTopNew();
	public int getWallGraveWidthRightTopNew();
	public int getWallGraveWidthRightNew();
	public int getWallGraveWidthRight();
	public int getWallGraveWidthRightTop();

	public int getSmallGraveLength();

	public List<Entry<Integer, String>> getGraveColors();

	public void setGraveColors(List<Entry<Integer, String>> colors);
	
	public void setMaxImageWidthForTooltip(int width);
	public int getMaxImageWidthForTooltip();

	public void setMaxImageWidthForDetail(int width);
	public int getMaxImageWidthForDetail();
}

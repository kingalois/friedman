package at.am.friedman.data;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;

import at.am.common.logging.LogFactory;
import at.am.friedman.shared.CemeteryOptionsInterface;

public class CemeteryOptions implements CemeteryOptionsInterface {

	private static CemeteryOptions instance;

	private static final Logger log = LogFactory.makeLogger();

	private int wallGraveWidthLeft = 33;
	private int wallGraveWidthLeftTop = 33;
	private int wallGraveWidthLeftNew = 33;
	private int wallGraveWidthLeftTopNew = 33;
	private int wallGraveWidthRight = 33;
	private int wallGraveWidthRightTop = 33;
	private int wallGraveWidthRightNew = 33;
	private int wallGraveWidthRightTopNew = 33;
	private int nrOfGraveRowsOld = 27;
	private int nrOfGravePlacesOld = 15;
	private int nrOfWallGravesLeftSideOld = 53;
	private int nrOfWallGravesRightSideOld = 51;
	private int nrOfWallGravesLeftTopOld = 13;
	private int nrOfWallGravesRightTopOld = 14;
	private int normalGraveLength = 58;
	private int normalGraveWidth = 30;
	private int wallGraveLength = 30;

	private Integer maxImageWidtForTooltip = 500;
	private Integer maxImageWidthForDetail = 1000;

	private int smallGraveLength = normalGraveLength / 2;

	private List<Entry<Integer, String>> graveColors = new ArrayList<Map.Entry<Integer, String>>();

	private CemeteryOptions(String optionsFile) {
		graveColors.add(new AbstractMap.SimpleEntry(5, "text für 5"));
		graveColors.add(new AbstractMap.SimpleEntry(12, "text für 12"));
		graveColors.add(new AbstractMap.SimpleEntry(3, "text für 3"));
		loadState(optionsFile);
	}

	@Override
	public void saveState(String path) {
		FileWriter writer = null;

		try {
			writer = new FileWriter(path);
			XMLMemento memento = XMLMemento.createWriteRoot("options");
			memento.putInteger("gravewidth", getNormalGraveWidth());
			memento.putInteger("gravelength", getNormalGraveLength());
			memento.putInteger("wallgravewidthleft", getWallGraveWidthLeft());
			memento.putInteger("wallgravewidthlefttop", getWallGraveWidthLeftTop());
			memento.putInteger("wallgravewidthleftnew", getWallGraveWidthLeftNew());
			memento.putInteger("wallgravewidthlefttopnew", getWallGraveWidthLeftTopNew());
			memento.putInteger("wallgravewidthright", getWallGraveWidthRight());
			memento.putInteger("wallgravewidthrighttop", getWallGraveWidthRightTop());
			memento.putInteger("wallgravewidthrightnew", getWallGraveWidthRightNew());
			memento.putInteger("wallgravewidthrighttopnew", getWallGraveWidthRightTopNew());
			memento.putInteger("wallgravelength", getWallGraveLength());
			memento.putInteger("nrofgraveplaces", getNrOfGravePlacesOld());
			memento.putInteger("nrofgraverows", getNrOfGraveRowsOld());
			memento.putInteger("nrofwallgravesleftside", getNrOfWallGravesLeftSideOld());
			memento.putInteger("nrofwallgraveslefttop", getNrOfWallGravesLeftTopOld());
			memento.putInteger("nrofwallgravesrightside", getNrOfWallGravesRightSideOld());
			memento.putInteger("nrofwallgravesrighttop", getNrOfWallGravesRightTopOld());
			memento.putInteger("maximagewidthfortooltip", getMaxImageWidthForTooltip());
			memento.putInteger("maximagewidthfordetail", getMaxImageWidthForDetail());
			IMemento graveColorsMemento = memento.createChild("gravecolors");
			graveColorsMemento.putInteger("nrofcolors", graveColors.size());
			for (int i = 0; i < graveColors.size(); i++) {
				IMemento colorMemento = graveColorsMemento.createChild("color");
				Entry<Integer, String> entry = graveColors.get(i);
				colorMemento.putInteger("colorvalue", entry.getKey());
				colorMemento.putString("text", entry.getValue());
			}

			memento.save(writer);
			log.info("options successfully written");
		} catch (Exception e) {
			log.log(Level.SEVERE, "cannot save options", e);
		}

	}

	@Override
	public void loadState(String path) {
		FileReader reader;
		try {
			reader = new FileReader(path);
			IMemento memento = XMLMemento.createReadRoot(reader);
			loadStateInternal(memento);
			log.info("options sucessfully loaded");
		} catch (Exception e) {
			log.log(Level.SEVERE, "Cannot load cemetery options, init default values", e);
			saveState(path);
		}

	}

	private void loadStateInternal(IMemento memento) {
		normalGraveWidth = memento.getInteger("gravewidth");
		normalGraveLength = memento.getInteger("gravelength");
		wallGraveWidthLeft = memento.getInteger("wallgravewidthleft");
		wallGraveWidthLeftTop = memento.getInteger("wallgravewidthlefttop");
		wallGraveWidthLeftNew = memento.getInteger("wallgravewidthleftnew");
		wallGraveWidthLeftTopNew = memento.getInteger("wallgravewidthlefttopnew");
		wallGraveWidthRight = memento.getInteger("wallgravewidthright");
		wallGraveWidthRightTop = memento.getInteger("wallgravewidthrighttop");
		wallGraveWidthRightNew = memento.getInteger("wallgravewidthrightnew");
		wallGraveWidthRightTopNew = memento.getInteger("wallgravewidthrighttopnew");
		wallGraveLength = memento.getInteger("wallgravelength");
		nrOfGravePlacesOld = memento.getInteger("nrofgraveplaces");
		nrOfGraveRowsOld = memento.getInteger("nrofgraverows");
		nrOfWallGravesLeftSideOld = memento.getInteger("nrofwallgravesleftside");
		nrOfWallGravesLeftTopOld = memento.getInteger("nrofwallgraveslefttop");
		nrOfWallGravesRightSideOld = memento.getInteger("nrofwallgravesrightside");
		nrOfWallGravesRightTopOld = memento.getInteger("nrofwallgravesrighttop");

		maxImageWidtForTooltip = memento.getInteger("maximagewidthfortooltip");
		if (maxImageWidtForTooltip == null) {
			maxImageWidtForTooltip = 500;
		}

		maxImageWidthForDetail = memento.getInteger("maximagewidthfordetail");
		if (maxImageWidthForDetail == null) {
			maxImageWidthForDetail = 1000;
		}

		IMemento graveColorMemento = memento.getChild("gravecolors");
		if (graveColorMemento != null) {
			graveColors.clear();
			int size = graveColorMemento.getInteger("nrofcolors");
			IMemento[] colors = graveColorMemento.getChildren("color");
			for (int i = 0; i < colors.length; i++) {
				graveColors.add(new AbstractMap.SimpleEntry(colors[i].getInteger("colorvalue"), colors[i].getString("text")));
			}
		}

	}

	public static synchronized CemeteryOptionsInterface getInstance(String optionsFile) {
		if (instance == null) {
			instance = new CemeteryOptions(optionsFile);
		}
		return instance;
	}

	@Override
	public void setNrOfGraveRowsOld(int nrOfRows) {
		this.nrOfGraveRowsOld = nrOfRows;

	}

	@Override
	public int getNrOfGraveRowsOld() {
		return this.nrOfGraveRowsOld;
	}

	@Override
	public void setNrOfGravePlacesOld(int nrOfPlaces) {
		this.nrOfGravePlacesOld = nrOfPlaces;
	}

	@Override
	public int getNrOfGravePlacesOld() {
		return this.nrOfGravePlacesOld;
	}

	@Override
	public void setNrOfWallGravesLeftSideOld(int nrOfGraves) {
		this.nrOfWallGravesLeftSideOld = nrOfGraves;
	}

	@Override
	public int getNrOfWallGravesLeftSideOld() {
		return this.nrOfWallGravesLeftSideOld;
	}

	@Override
	public void setNrOfWallGravesRightSideOld(int nrOfGraves) {
		this.nrOfWallGravesRightSideOld = nrOfGraves;
	}

	@Override
	public int getNrOfWallGravesRightSideOld() {
		return this.nrOfWallGravesRightSideOld;
	}

	@Override
	public void setNrOfWallGravesLeftTopOld(int nrOfGraves) {
		this.nrOfWallGravesLeftTopOld = nrOfGraves;
	}

	@Override
	public int getNrOfWallGravesLeftTopOld() {
		return this.nrOfWallGravesLeftTopOld;
	}

	@Override
	public void setNrOfWallGravesRightTopOld(int nrOfGraves) {
		this.nrOfWallGravesRightTopOld = nrOfGraves;
	}

	@Override
	public int getNrOfWallGravesRightTopOld() {
		return this.nrOfWallGravesRightTopOld;
	}

	@Override
	public int getNormalGraveLength() {
		return this.normalGraveLength;
	}

	@Override
	public void setNormalGraveLength(int length) {
		this.normalGraveLength = length;
	}

	@Override
	public int getNormalGraveWidth() {
		return this.normalGraveWidth;
	}

	@Override
	public void setNormalGraveWidth(int width) {
		this.normalGraveWidth = width;
	}

	@Override
	public int getWallGraveLength() {
		return this.wallGraveLength;
	}

	@Override
	public void setWallGraveLength(int length) {
		this.wallGraveLength = length;
	}

	@Override
	public int getWallGraveWidthLeft() {
		return this.wallGraveWidthLeft;
	}

	@Override
	public void setWallGraveWidthLeft(int width) {
		this.wallGraveWidthLeft = width;
	}

	@Override
	public void saveState() {
		// do nothing

	}

	@Override
	public void loadState() {
		// do nothing

	}

	@Override
	public int getSmallGraveLength() {
		return this.smallGraveLength;
	}

	@Override
	public List<Entry<Integer, String>> getGraveColors() {
		return graveColors;
	}

	@Override
	public void setGraveColors(List<Entry<Integer, String>> colors) {
		this.graveColors = colors;

	}

	@Override
	public int getWallGraveWidthLeftTop() {
		return wallGraveWidthLeftTop;
	}

	@Override
	public void setWallGraveWidthRight(int width) {
		this.wallGraveWidthRight = width;

	}

	@Override
	public void setWallGraveWidthLeftTop(int width) {
		this.wallGraveWidthLeftTop = width;
	}

	@Override
	public void setWallGraveWidthRightTop(int width) {
		this.wallGraveWidthRightTop = width;
	}

	@Override
	public void setWallGraveWidthLeftNew(int width) {
		this.wallGraveWidthLeftNew = width;
	}

	@Override
	public void setWallGraveWidthRightNew(int width) {
		this.wallGraveWidthRightNew = width;
	}

	@Override
	public void setWallGraveWidthLeftTopNew(int width) {
		this.wallGraveWidthLeftTopNew = width;
	}

	@Override
	public void setWallGraveWidthRightTopNew(int width) {
		this.wallGraveWidthRightTopNew = width;
	}

	@Override
	public int getWallGraveWidthLeftNew() {
		return this.wallGraveWidthLeftNew;
	}

	@Override
	public int getWallGraveWidthLeftTopNew() {
		return this.wallGraveWidthLeftTopNew;
	}

	@Override
	public int getWallGraveWidthRightTopNew() {
		return this.wallGraveWidthRightTopNew;
	}

	@Override
	public int getWallGraveWidthRightNew() {
		return this.wallGraveWidthRightNew;
	}

	@Override
	public int getWallGraveWidthRight() {
		return this.wallGraveWidthRight;
	}

	@Override
	public int getWallGraveWidthRightTop() {
		return this.wallGraveWidthRightTop;
	}

	@Override
	public void setMaxImageWidthForTooltip(int width) {
		maxImageWidtForTooltip = width;

	}

	@Override
	public int getMaxImageWidthForTooltip() {
		return maxImageWidtForTooltip;
	}

	@Override
	public void setMaxImageWidthForDetail(int width) {
		maxImageWidthForDetail = width;
	}

	@Override
	public int getMaxImageWidthForDetail() {
		return maxImageWidthForDetail;
	}
}

package at.am.friedman.data.enums;

public enum GraveZone {

	Links, Rechts, Wandgrab_Links, Wandgrab_Rechts, Externe;
	
	public boolean isWallGrave(){
		return this.equals(Wandgrab_Links) || this.equals(Wandgrab_Rechts);
	}

}

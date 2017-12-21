package at.am.friedman.shared;

public class CemeteryStringUtils {

	public static String getTooltipForGrave ( GraveInterface grave ) {
		StringBuilder builder = new StringBuilder();
		builder.append("Grab\n");
		builder.append(grave.getZone() + "\n");
		builder.append("Reihe: " + grave.getRow() + " Platz: " + grave.getPlace());

		return builder.toString();
	}

}

package at.am.friedman.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

	/**
	 * formats the specified long timestamp to String dd.MM.yyyy
	 * @param time long
	 * @return String with format dd.MM.yyyy
	 */
	public static String stringifyDateLong(long time) {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		return format.format(new Date(time));
	}

}

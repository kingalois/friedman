package at.am.friedman.commons.utils;

public class DebugFlagHelper {

	public static String getStringDebugFlag(String debugFlagKey, String defaultValue) {
		return System.getProperty(debugFlagKey, defaultValue);
	}

	public static boolean getBooleanDebugFlag(String debugFlagKey, Boolean defaultValue) {
		String value = System.getProperty(debugFlagKey, "none");
		if (value.equalsIgnoreCase("none")) {
			return defaultValue;
		}
		return Boolean.getBoolean(value);
	}

}

package modularfurnace.versionchecker;

public class ConfigurationSettings {

	   /*
     * Version check related settings credits to Pahimar
     */
    public static boolean DISPLAY_VERSION_RESULT;
    public static final String DISPLAY_VERSION_RESULT_CONFIGNAME = "version_check.display_results";
    public static final boolean DISPLAY_VERSION_RESULT_DEFAULT = true;

    public static String LAST_DISCOVERED_VERSION;
    public static final String LAST_DISCOVERED_VERSION_CONFIGNAME = "version_check.last_discovered_version";
    public static final String LAST_DISCOVERED_VERSION_DEFAULT = "";

    public static String LAST_DISCOVERED_VERSION_TYPE;
    public static final String LAST_DISCOVERED_VERSION_TYPE_CONFIGNAME = "version_check.last_discovered_version_type";
    public static final String LAST_DISCOVERED_VERSION_TYPE_DEFAULT = "";
}

package modularfurnace.versionchecker;

import modularfurnace.lib.Reference;

public class Strings {
	
	public static final String FALSE = "false";
	
	  /* Version check related constants */
    public static final String VERSION_CHECK_INIT_LOG_MESSAGE = "Modular Furnace : Starting Version Check";
    public static final String UNINITIALIZED_MESSAGE = "Modular Furnace : Version Check Failed";
    public static final String CURRENT_MESSAGE = "Modular Furnace Current Version : " + Reference.VERSION_NUMBER;
    public static final String OUTDATED_MESSAGE = Colours.PURE_WHITE + "Modular Furnace : Version is Outdated, go to pauljoda.com for updates";
    public static final String GENERAL_ERROR_MESSAGE = "Modular Furnace : error";
    public static final String FINAL_ERROR_MESSAGE = "Modular Furnace : It's over";
    public static final String MC_VERSION_NOT_FOUND = "Modular Furncae : MC Version Not Found";

}

package modularfurnace.versionchecker;

import java.io.File;

import modularfurnace.GeneralSettings;
import net.minecraftforge.common.Configuration;

public class ConfigurationHandler {
	public static Configuration configuration;


	public static void init(String configPath) {

		GeneralSettings.init(new File(configPath + "general.properties"));

	}
}

package modularfurnace;

import static net.minecraftforge.common.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.logging.Level;

import modularfurnace.versionchecker.ConfigurationSettings;

import cpw.mods.fml.common.FMLLog;

import modularfurnace.lib.Reference;
import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;

public class GeneralSettings {

    private static Configuration config;

    //Sets base id for blocks
    public static int furnaceCoreID;
    public static int furnaceDummyID;
    public static int furnaceDummyIDRedstone;
    public static int furnaceDummyIDGlowStone;
    public static int furnaceDummyIDDiamond;
    public static int crafterInactiveID;
    public static int crafterActiveID;
    public static int furnaceDummyIDEmerald;
	public static int furnaceCoreActiveID;
	public static int laveCoreID;
	public static int furnaceDummyIOID;
	public static int furnaceDummyActiveIOID;
	public static int furnaceCoreSmelteryID;
	public static int furnaceCoreSmelteryActiveID;
	public static int furnaceDummySmelteryID;
	public static int furnaceSmelteryBrickID;
	
	public static int furnaceCoreMultiID;
	public static int furnaceCoreMultiActiveID;
	public static int furnaceMultiDummyID;
	public static int furnaceDummyMultiRedstoneID;
	public static int furnaceDummyMultiIronID;
	public static int furnaceDummyMultiEmeraldID;
	
	//Id's associated with ore conversion
	public static int copperIngotID;
	public static int aluminiumIngotID;
	public static int tinIngotID;
	public static int textureOverlayID;
	public static int smelterOverlayID;
	
	//Banned Blocks
	public static int[] bannedBlocks;
	
	//Additional Smeltery Recipes
	public static int[] smelteryInput;
	public static int [] smelteryOutput;
	
	//PaintBrush
	public static int paintBrushID;

	//checks to see if we are using resource pack or not
	public static boolean useTextures;
	public static String textureName;
	

    public static void init(File configFile) {

        config = new Configuration(configFile);

        try {
        	config.load();
            
            /* Version check */
            ConfigurationSettings.DISPLAY_VERSION_RESULT = config.get(CATEGORY_GENERAL, ConfigurationSettings.DISPLAY_VERSION_RESULT_CONFIGNAME, ConfigurationSettings.DISPLAY_VERSION_RESULT_DEFAULT).getBoolean(ConfigurationSettings.DISPLAY_VERSION_RESULT_DEFAULT);
            ConfigurationSettings.LAST_DISCOVERED_VERSION = config.get(CATEGORY_GENERAL, ConfigurationSettings.LAST_DISCOVERED_VERSION_CONFIGNAME, ConfigurationSettings.LAST_DISCOVERED_VERSION_DEFAULT).getString();
            ConfigurationSettings.LAST_DISCOVERED_VERSION_TYPE = config.get(CATEGORY_GENERAL, ConfigurationSettings.LAST_DISCOVERED_VERSION_TYPE_CONFIGNAME, ConfigurationSettings.LAST_DISCOVERED_VERSION_TYPE_DEFAULT).getString();

            //Grabs the ids from the config
            furnaceCoreID = config.getBlock("Furnace Core", 300).getInt();
            furnaceCoreActiveID = config.getBlock("Furnace Core Active", 301).getInt();
            furnaceDummyID = config.getBlock("Furnace Dummy", 302).getInt();
            furnaceDummyIDRedstone = config.getBlock("Furnace Dummy Redstone", 303).getInt();
            furnaceDummyIDGlowStone = config.getBlock("Furnace Dummy Iron", 304).getInt();
            furnaceDummyIDDiamond = config.getBlock("Furnace Dummy Diamond", 305).getInt();
            furnaceDummyIDEmerald = config.getBlock("Furnace Dummy Emerald", 306).getInt();
            crafterInactiveID = config.getBlock("Crafter Inactive", 307).getInt();
            crafterActiveID = config.getBlock("Crafter Active", 308).getInt();
            laveCoreID = config.getBlock("LaveCore", 309).getInt();
            furnaceDummyIOID = config.getBlock("Furnace IO", 310).getInt();
            furnaceDummyActiveIOID = config.getBlock("Furnace IO Active", 311).getInt();
            furnaceCoreSmelteryID = config.getBlock("Smeltery Core", 312).getInt();
            furnaceCoreSmelteryActiveID = config.getBlock("Smeltery Inacive", 313).getInt();
            furnaceDummySmelteryID = config.getBlock("Smeltery Dummy Core", 314).getInt();
            furnaceSmelteryBrickID = config.getBlock("Smeltery Brick", 315).getInt();
            copperIngotID = config.getItem("CopperIngot", 318).getInt();
            textureOverlayID = config.getBlock("Texture Overlay ID", 319).getInt();
            aluminiumIngotID = config.getItem("Aluminium Ingot", 320).getInt();
            tinIngotID = config.getItem("Tin Ingot", 322).getInt();
            paintBrushID = config.getItem("Paint Brush", 323).getInt();
            smelterOverlayID = config.getBlock("SmelterOverlay", 324).getInt();
            
            furnaceCoreMultiID = config.getBlock("Multi Core Furnace", 325).getInt();
            furnaceCoreMultiActiveID = config.getBlock("Multi Core Active Furnace", 326).getInt();
            furnaceMultiDummyID = config.getBlock("Multi Furnace Dummy ID", 327).getInt();
            furnaceDummyMultiRedstoneID = config.getBlock("Multi Furnace Redstone Dummy", 328).getInt();
            furnaceDummyMultiIronID = config.getBlock("Multi Furnace Iron Dummy", 329).getInt();
            furnaceDummyMultiEmeraldID = config.getBlock("Multi Furnace Emerald Dummy", 330).getInt();
            
            bannedBlocks = config.get(Configuration.CATEGORY_GENERAL, "Banned Block ID's",
            		new int[] 	{0, Block.wood.blockID, Block.planks.blockID,
        			Block.dirt.blockID, Block.ice.blockID,
        			Block.snow.blockID, Block.bookShelf.blockID,
        			Block.leaves.blockID, Block.melon.blockID,
        			Block.pumpkin.blockID, Block.tnt.blockID,
        			Block.cloth.blockID, Block.hay.blockID,
        			Block.grass.blockID, Block.bedrock.blockID,
        			Block.oreDiamond.blockID, Block.oreIron.blockID,
        			Block.oreEmerald.blockID, Block.oreGold.blockID,}).getIntList();
            
            smelteryInput = config.get(Configuration.CATEGORY_GENERAL, "Smeltery Input ID", 
            		new int[] {}).getIntList();
            
            smelteryOutput = config.get(Configuration.CATEGORY_GENERAL, "Smeltery Ouput ID, must line up with inputs,for each one in inputs, must list two here or crashes happen", 
            		new int[] {}).getIntList();
            		

            useTextures = config.get(Configuration.CATEGORY_GENERAL, "Use Vanilla Texture For Overlay?", true).getBoolean(true);
            textureName = config.get(Configuration.CATEGORY_GENERAL, "Overlay Texture Name (from assets folder)", "hopper_top").getString();
          
            
        }
        catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " has had a problem loading its general configuration");
        }
        finally {
        	config.save();
        }
    }
    
    public static void set(String categoryName, String propertyName, String newValue) {

    	config.load();
        if (config.getCategoryNames().contains(categoryName)) {
            if (config.getCategory(categoryName).containsKey(propertyName)) {
            	config.getCategory(categoryName).get(propertyName).set(newValue);
            }
        }
        config.save();
    }
}
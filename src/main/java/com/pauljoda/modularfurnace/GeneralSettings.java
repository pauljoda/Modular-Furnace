package com.pauljoda.modularfurnace;

import java.io.File;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

import com.pauljoda.modularfurnace.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;

public class GeneralSettings {

    private static Configuration config;

	
	//Banned Blocks
	public static String[] bannedBlocks;

	//checks to see if we are using resource pack or not
	public static boolean useTextures;
	public static String textureName;
	

    public static void init(File configFile) {

        config = new Configuration(configFile);

        try {
        	config.load();

            bannedBlocks = config.get("Settings", "Banned Block Unlocalized Names",
            		new String[] 	{Blocks.log.getUnlocalizedName(), Blocks.planks.getUnlocalizedName(),
        			Blocks.dirt.getUnlocalizedName(), Blocks.ice.getUnlocalizedName(),
        			Blocks.snow.getUnlocalizedName(), Blocks.bookshelf.getUnlocalizedName(),
        			Blocks.leaves.getUnlocalizedName(), Blocks.melon_block.getUnlocalizedName(),
        			Blocks.pumpkin.getUnlocalizedName(), Blocks.tnt.getUnlocalizedName(),
        			Blocks.wool.getUnlocalizedName(), Blocks.hay_block.getUnlocalizedName(),
        			Blocks.grass.getUnlocalizedName(), Blocks.bedrock.getUnlocalizedName(),
        			Blocks.diamond_ore.getUnlocalizedName(), Blocks.iron_ore.getUnlocalizedName(),
        			Blocks.emerald_ore.getUnlocalizedName(), Blocks.gold_ore.getUnlocalizedName(),}).getStringList();
            		
            useTextures = config.get(Configuration.CATEGORY_GENERAL, "Use Vanilla Texture For Overlay?", true).getBoolean(true);
            textureName = config.get(Configuration.CATEGORY_GENERAL, "Overlay Texture Name (from assets folder)", "hopper_top").getString();
          
            
        }
        catch (Exception e) {
            FMLLog.log(Level.ERROR, e, Reference.MOD_NAME + " has had a problem loading its general configuration");
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
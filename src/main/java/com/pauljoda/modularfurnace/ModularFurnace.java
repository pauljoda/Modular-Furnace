package com.pauljoda.modularfurnace;

import java.io.File;

import com.pauljoda.modularfurnace.blocks.BlockManager;
import com.pauljoda.modularfurnace.client.ClientProxy;
import com.pauljoda.modularfurnace.common.CommonProxy;
import com.pauljoda.modularfurnace.lib.Reference;
import com.pauljoda.modularfurnace.util.GeneralSettings;
import com.pauljoda.modularfurnace.util.VersionChecking;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

//I prefer to use a Reference class to label my mod. Makes it ... modular ;)
@Mod(name = Reference.MOD_NAME, modid = Reference.MOD_ID, version = Reference.Version)

public class ModularFurnace {
	@Instance("modularfurnace")
	public static ModularFurnace instance;
	@SidedProxy( clientSide="com.pauljoda.modularfurnace.client.ClientProxy", serverSide="com.pauljoda.modularfurnace.common.CommonProxy")
	public static CommonProxy proxy;

	//Creates the Creative Tab
	public static CreativeTabs tabModularFurnace = new CreativeTabs("tabModularFurnace"){

		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Item.getItemFromBlock(BlockManager.crafterInactive);
		}
	};


	@EventHandler
	public void preInit(FMLPreInitializationEvent event){

		GeneralSettings.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.CHANNEL_NAME.toLowerCase() + File.separator + "ModularFurnace.cfg"));

		//Grabs the blocks and their recipies
		BlockManager.registerBlocks();
		BlockManager.register();
		BlockManager.registerCraftingRecipes();
		
		FMLCommonHandler.instance().bus().register(new GeneralSettings());

		VersionChecking.execute();
	}


	@EventHandler
	public void init(FMLInitializationEvent event) {



		//Sets up tileEnties, which are the backbone of this mod
		proxy.registerTileEntities();

		//Custom Block renders, for alpha layering
		ClientProxy.setCustomRenderers();

		//Lets get Gui
		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);   
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		//Anyone there?  
	}
}

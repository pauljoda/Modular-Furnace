package modularfurnace;

import java.io.File;

import modularfurnace.versionchecker.ConfigurationHandler;
import modularfurnace.versionchecker.LogHelper;
import modularfurnace.versionchecker.VersionHelper;
import modularfurnace.versionchecker.VersionTickHandler;

import modularfurnace.blocks.BlockManager;
import modularfurnace.client.ClientProxy;
import modularfurnace.common.CommonProxy;
import modularfurnace.lib.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

//I prefer to use a Reference class to label my mod. Makes it ... modular ;)
@Mod(name = Reference.MOD_NAME, modid = Reference.MOD_ID, version = Reference.Version)
@NetworkMod(channels = { Reference.MOD_NAME },clientSideRequired=true, serverSideRequired=false)

public class ModularFurnace {
    @Instance("modularfurnace")
    public static ModularFurnace instance;
    @SidedProxy( clientSide="modularfurnace.client.ClientProxy", serverSide="modularfurnace.common.CommonProxy")
    public static CommonProxy proxy;
   
    //Creates the Creative Tab
    public static CreativeTabs tabModularFurnace = new CreativeModularFurnace(CreativeTabs.getNextID(), Reference.MOD_ID);
  

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){

        // Initialize the log helper
        LogHelper.init();

        // Initialize the configuration
        ConfigurationHandler.init(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.CHANNEL_NAME.toLowerCase() + File.separator);

        // Conduct the version check and log the result
        VersionHelper.execute();
        
        // Initialize the Version Check Tick Handler (Client only)
        TickRegistry.registerTickHandler(new VersionTickHandler(), Side.CLIENT);

        
   
    }

   
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	
    	//Custom Block renders, for alpha layering
    	ClientProxy.setCustomRenderers();
    	
    	//Labels the creative tab
    	LanguageRegistry.instance().addStringLocalization("itemGroup." + Reference.MOD_ID, "en_US", "Modular Furnace");
        
    	//Grabs the blocks and their recipies
        BlockManager.registerBlocks();
        BlockManager.registerCraftingRecipes();
        
        //Sets up tileEnties, which are the backbone of this mod
        proxy.registerTileEntities();
        
        //naming the crap out of those containers
        LanguageRegistry.instance().addStringLocalization("multifurnace.container.multifurnace", "Modular Furnace");
        LanguageRegistry.instance().addStringLocalization("multifurnace.container.multifurnacecore", "Modular Furnace");
        LanguageRegistry.instance().addStringLocalization("multifurnace.container.multifurnacecoresmeltery", "Modular Furnace Smeltery");
        
        //Lets get Gui
        NetworkRegistry.instance().registerGuiHandler(this, proxy);
        
        //Lets make things work with our neighbors
        OreDictionary.registerOre("ingotCopper", new ItemStack(BlockManager.copperIngot));
        OreDictionary.registerOre("ingotTin", new ItemStack(BlockManager.tinIngot));
        OreDictionary.registerOre("ingotAluminium", new ItemStack(BlockManager.aluminiumIngot));
        MinecraftForge.setBlockHarvestLevel(BlockManager.overLayTexture, "pickaxe", 0);


        
    
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
      //Anyone there?  
    }
    
}

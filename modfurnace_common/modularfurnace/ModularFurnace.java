package modularfurnace;

import modularfurnace.blocks.BlockManager;
import modularfurnace.common.CommonProxy;
import modularfurnace.lib.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
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

@Mod(name = Reference.MOD_NAME, modid = Reference.MOD_ID, version = Reference.Version)
@NetworkMod(channels = { Reference.MOD_NAME },clientSideRequired=true, serverSideRequired=false)

public class ModularFurnace {
    @Instance("modularfurnace")
    public static ModularFurnace instance;
    @SidedProxy( clientSide="modularfurnace.client.ClientProxy", serverSide="modularfurnace.common.CommonProxy")
    public static CommonProxy proxy;
    
    public static CreativeTabs tabModularFurnace = new CreativeModularFurnace(CreativeTabs.getNextID(), Reference.MOD_ID);
    
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


	
	public static boolean useTextures;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        
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

        useTextures = config.get(Configuration.CATEGORY_GENERAL, "Use vanilla textures?", false).getBoolean(false);
        
        
        config.save();
   
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	
    	LanguageRegistry.instance().addStringLocalization("itemGroup." + Reference.MOD_ID, "en_US", "Modular Furnace");
        
        BlockManager.registerBlocks();
        BlockManager.registerCraftingRecipes();
        
        proxy.registerTileEntities();
        
        LanguageRegistry.instance().addStringLocalization("multifurnace.container.multifurnace", "Modular Furnace");
        
        NetworkRegistry.instance().registerGuiHandler(this, proxy);
        
    
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        
    }
    
}

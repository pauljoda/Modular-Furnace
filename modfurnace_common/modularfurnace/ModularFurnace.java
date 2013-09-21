package modularfurnace;

import net.minecraftforge.common.Configuration;
import modularfurnace.blocks.BlockManager;
import modularfurnace.common.CommonProxy;
import modularfurnace.lib.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(name = Reference.MOD_NAME, modid = Reference.MOD_ID, version = Reference.Version)
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class ModularFurnace {
    @Instance("modularfurnace")
    public static ModularFurnace instance;
    @SidedProxy(clientSide="modularfurnace.client.ClientProxy", serverSide="modularfurnace.common.CommonProxy")
    public static CommonProxy proxy;
    
    
    public static int furnaceCoreID;
    public static int furnaceDummyID;
    public static int furnaceDummyIDRedstone;
    public static int furnaceDummyIDGlowStone;
    public static int furnaceDummyIDDiamond;
    public static int crafterInactiveID;
    public static int crafterActiveID;
    public static int furnaceDummyIDEmerald;
	public static int furnaceCoreActiveID;
	
	public static boolean useTextures;

    @PreInit
    public void preInit(FMLPreInitializationEvent event){
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        
        furnaceCoreID = config.getBlock("Furnace Core", 300).getInt();
        furnaceDummyID = config.getBlock("Furnace Dummy", 301).getInt();
        furnaceDummyIDRedstone = config.getBlock("Furnace Dummy Redstone", 302).getInt();
        furnaceDummyIDGlowStone = config.getBlock("Furnace Dummy GlowStone", 303).getInt();
        furnaceDummyIDDiamond = config.getBlock("Furnace Dummy Diamond", 304).getInt();
        crafterInactiveID = config.getBlock("Crafter Inactive", 305).getInt();
        crafterActiveID = config.getBlock("Crafter Active", 308).getInt();
        furnaceDummyIDEmerald = config.getBlock("Furnace Dummy Emerald", 307).getInt();
        furnaceCoreActiveID = config.getBlock("Furnace Core Active", 306).getInt();
        
        useTextures = config.get(Configuration.CATEGORY_GENERAL, "Use vanilla textures?", false).getBoolean(false);
        
        
        config.save();
   
    }
    
    @Init
    public void init(FMLInitializationEvent event) {
        
        BlockManager.registerBlocks();
        BlockManager.registerCraftingRecipes();
        
        proxy.registerTileEntities();
        
        LanguageRegistry.instance().addStringLocalization("multifurnace.container.multifurnace", "Modular Furnace");
        
        NetworkRegistry.instance().registerGuiHandler(this, proxy);
        
    
    }
    
    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
        
    }
    
}

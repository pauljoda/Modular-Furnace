package modularfurnace;

import modularfurnace.blocks.BlockManager;
import modularfurnace.common.CommonProxy;
import modularfurnace.lib.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
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
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

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
	
	//Id's associated with ore conversion
	public static int copperOreUniversalID;
	public static int copperIngotID;
	public static int aluminiumOreUniversalID;
	public static int aluminiumIngotID;
	public static int tinOreUniversalID;
	public static int tinIngotID;

	//checks to see if we are using resouce pack or not
	public static boolean useTextures;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        
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
        copperOreUniversalID = config.getBlock("Copper Ore", 317).getInt();
        copperIngotID = config.getBlock("CopperIngot", 318).getInt();
        aluminiumOreUniversalID = config.getBlock("Aluminium Ore", 319).getInt();
        aluminiumIngotID = config.getBlock("Aluminium Ingot", 320).getInt();
        tinOreUniversalID = config.getBlock("Tin Ore", 321).getInt();
        tinIngotID = config.getBlock("Tin Ingot", 322).getInt();
        
        useTextures = config.get(Configuration.CATEGORY_GENERAL, "Use vanilla textures?", false).getBoolean(false);
        
        
        config.save();
   
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	
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
        
        //Lets get Gui's
        NetworkRegistry.instance().registerGuiHandler(this, proxy);
        
        //Lets make things work with our neighbors
        OreDictionary.registerOre("ingotCopper", new ItemStack(BlockManager.copperIngot));
        OreDictionary.registerOre("ingotTin", new ItemStack(BlockManager.tinIngot));
        OreDictionary.registerOre("ingotAluminium", new ItemStack(BlockManager.aluminiumIngot));
        
        OreDictionary.registerOre("oreCopper", new ItemStack(BlockManager.copperOreUniversal));
        OreDictionary.registerOre("oreTin", new ItemStack(BlockManager.tinOreUniversal));
        OreDictionary.registerOre("oreAluminum", new ItemStack(BlockManager.aluminiumOreUniversal));
        
        
        
        //Adding support for vanilla
        GameRegistry.addSmelting(BlockManager.aluminiumOreUniversal.blockID, new ItemStack(BlockManager.aluminiumIngot), 0.5f);
        GameRegistry.addSmelting(BlockManager.copperOreUniversal.blockID, new ItemStack(BlockManager.copperIngot), 0.5f);
        GameRegistry.addSmelting(BlockManager.tinOreUniversal.blockID, new ItemStack(BlockManager.tinIngot), 0.5f);
        
    
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
      //Anyone there?  
    }
    
}

package modularfurnace;

import modularfurnace.blocks.BlockManager;
import modularfurnace.client.ClientProxy;
import modularfurnace.common.CommonProxy;
import modularfurnace.lib.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
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
	public static int copperIngotID;
	public static int aluminiumIngotID;
	public static int tinIngotID;
	public static int textureOverlayID;
	public static int smelterOverlayID;
	
	//PaintBrush
	public static int paintBrushID;

	//checks to see if we are using resource pack or not
	public static boolean useTextures;
	public static String textureName;
	
	

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
        copperIngotID = config.getBlock("CopperIngot", 318).getInt();
        textureOverlayID = config.getBlock("Texture Overlay ID", 319).getInt();
        aluminiumIngotID = config.getBlock("Aluminium Ingot", 320).getInt();
        tinIngotID = config.getBlock("Tin Ingot", 322).getInt();
        paintBrushID = config.getBlock("Paint Brush", 323).getInt();
        smelterOverlayID = config.getBlock("SmelterOverlay", 324).getInt();

        useTextures = config.get(Configuration.CATEGORY_GENERAL, "Use overlay?", false).getBoolean(false);
        textureName = config.get(Configuration.CATEGORY_GENERAL, "Overlay Texture Name (from assets folder)", "hopper_top").getString();
        
        
        
        config.save();
   
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	
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

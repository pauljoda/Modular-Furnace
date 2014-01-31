package modularfurnace.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import modularfurnace.GeneralSettings;
import modularfurnace.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class BlockManager
{
	public static Block furnaceCore = null;
	public static Block furnaceDummy = null;
	public static Block furnaceDummyRedstone = null;
	public static Block furnaceDummyGlowStone = null;
	public static Block furnaceDummyDiamond = null;
	public static Block crafterInactive = null;
	public static Block crafterActive = null;
	public static Block furnaceDummyEmerald = null;
	public static Block furnaceCoreActive = null;
	public static Block lavaCore = null;
	public static Block furnaceDummyIO = null;
	public static Block furnaceDummyActiveIO = null;
	public static Block furnaceCoreSmeltery = null;
	public static Block furnaceCoreSmelteryActive = null;
	public static Block furnaceDummySmeltery = null;
	public static Block furnaceSmelteryBrick = null;
	public static Block furnaceCoreMulti = null;
	public static Block furnaceCoreMultiActive = null;
	public static Block furnaceMultiDummy = null;
	public static Block furnaceDummyMultiRedstone = null;
	public static Block furnaceDummyMultiIron = null;
	public static Block furnaceDummyMultiEmerald = null;
	
	//OverLays
	public static Block overLayTexture = null;
	public static Block smelterOverlay = null;
	

	//Items
	public static Item copperIngot = null;
	public static Item aluminiumIngot = null;
	public static Item tinIngot = null;
	
	//PaintBrush
	public static Item paintBrush = null;

	public static void registerBlocks()
	{
		//Creation
		furnaceCore = new BlockFurnaceCore(Reference.furnaceCoreID, false);
		furnaceCoreActive = new BlockFurnaceCore(Reference.furnaceCoreActiveID, true).setLightValue(0.875F);
		furnaceDummy = new BlockFurnaceDummy(Reference.furnaceDummyID);
		furnaceDummyRedstone = new BlockFurnaceDummy(Reference.furnaceDummyIDRedstone);
		furnaceDummyGlowStone = new BlockFurnaceDummy(Reference.furnaceDummyIDGlowStone);
		furnaceDummyDiamond = new BlockFurnaceDummy(Reference.furnaceDummyIDDiamond);
		crafterInactive = new BlockCrafterInactive(Reference.crafterInactive, Material.wood);
		crafterActive = new BlockCrafterActive(Reference.crafterActive);
		furnaceDummyEmerald = new BlockFurnaceDummy(Reference.furnaceDummyIDEmerald);
		lavaCore = new BlockLavaCore(Reference.lavaCore, Material.iron);
		furnaceDummyIO = new BlockFurnaceDummyIO(Reference.furnaceDummyIOID, Material.rock);
		furnaceDummyActiveIO = new BlockFurnaceDummyIOActive(Reference.furnaceDummyActiveIOID);
		furnaceCoreSmeltery = new BlockFurnaceCoreSmeltery(Reference.furnaceCoreSmelteryID, false);
		furnaceCoreSmelteryActive = new BlockFurnaceCoreSmeltery(Reference.furnaceCoreSmelteryActiveID, true);
		furnaceDummySmeltery = new BlockFurnaceDummySmeltery(Reference.furnaceDummySmelteryID);
		furnaceSmelteryBrick = new BlockFurnaceSmelteryBrick(Reference.furnaceSmelteryBrickID);
		
		furnaceCoreMulti = new BlockMultiFurnaceCore(Reference.furnaceCoreMultiID, false);
		furnaceCoreMultiActive =  new BlockMultiFurnaceCore(Reference.furnaceCoreMultiActiveID, true);
		furnaceMultiDummy = new BlockMultiFurnaceDummy(Reference.furnaceMultiDummyID);
		furnaceDummyMultiRedstone = new BlockMultiFurnaceDummy(Reference.furnaceDummyMultiRedstoneID);
		furnaceDummyMultiIron = new BlockMultiFurnaceDummy(Reference.furnaceDummyMultiIronID);
		furnaceDummyMultiEmerald = new BlockMultiFurnaceDummy(Reference.furnaceDummyMultiEmeraldID);
		
		
		copperIngot = new MetalCopperIngot(Reference.copperIngotID);
		aluminiumIngot = new MetalAluminiumIngot(Reference.aluminiumIngotID);
		tinIngot = new MetalTinIngot(Reference.tinIngotID);

		if(GeneralSettings.useTextures)
		overLayTexture = new BlockTextureOverlay(Reference.textureOverlayID, GeneralSettings.textureName, "overLayTexture", true);
		else
		overLayTexture = new BlockTextureOverlay(Reference.textureOverlayID, "custom_overlay", "overLayTexture", true);
	
		smelterOverlay = new BlockTextureOverlay(Reference.smelterOverlayID, "smelterOverlay","smelterOverlay", false);
	
		
		paintBrush = new ItemPaintBrush(Reference.paintBrushID);

		//Register
		GameRegistry.registerBlock(furnaceCore, "blockFurnaceCore");
		GameRegistry.registerBlock(furnaceDummy, "blockFurnaceDummy");
		GameRegistry.registerBlock(furnaceDummyRedstone, "blockFurnaceDummyRedstone");
		GameRegistry.registerBlock(furnaceDummyGlowStone, "blockFurnaceDummyGlowStone");
		GameRegistry.registerBlock(crafterInactive, "crafterInactive");
		GameRegistry.registerBlock(crafterActive, "crafterActive");
		GameRegistry.registerBlock(furnaceDummyDiamond, "blockFurnaceDummyDiamond");
		GameRegistry.registerBlock(furnaceDummyEmerald, "blockFurnaceDummyEmerald");
		GameRegistry.registerBlock(lavaCore, "blockLavaCore");
		GameRegistry.registerBlock(furnaceDummyIO, "furnaceDummyIO");
		GameRegistry.registerBlock(furnaceDummyActiveIO, "furnaceDummyActiveIO");
		GameRegistry.registerBlock(furnaceCoreSmeltery, "furnaceCoreSmeltery");
		GameRegistry.registerBlock(furnaceDummySmeltery, "blockFurnaceDummySmeltery");
		GameRegistry.registerBlock(furnaceSmelteryBrick, "furnaceSmelteryBrick");
		
		GameRegistry.registerBlock(furnaceCoreMulti, "furnaceCoreMulti");
		GameRegistry.registerBlock(furnaceMultiDummy, "furnaceMultiDummy");
		GameRegistry.registerBlock(furnaceDummyMultiRedstone, "furnaceDummyMultiRedstone");
		GameRegistry.registerBlock(furnaceDummyMultiIron, "furnaceDummyMultiIron");
		GameRegistry.registerBlock(furnaceDummyMultiEmerald, "furnaceDummyMultiEmerald");
		
		GameRegistry.registerItem(copperIngot, "copperIngot");
		GameRegistry.registerItem(aluminiumIngot, "aluminiumIngot");
		GameRegistry.registerItem(tinIngot, "tinIngot");

		GameRegistry.registerBlock(overLayTexture, "overLayTexture");
		GameRegistry.registerBlock(smelterOverlay, "smelterOverlay");


		
		GameRegistry.registerItem(paintBrush, "paintBrush");
		

		//Add Name
		LanguageRegistry.addName(furnaceCore, "Modular Furnace Core");
		LanguageRegistry.addName(furnaceDummy, "Modular Furnace Dummy CobbleStone");
		LanguageRegistry.addName(furnaceDummyRedstone, "Modular Furnace Dummy Redstone");
		LanguageRegistry.addName(furnaceDummyGlowStone, "Modular Furnace Dummy Iron");
		LanguageRegistry.addName(crafterInactive, "Crafting Core");
		LanguageRegistry.addName(crafterActive, "Crafting Core Active");
		LanguageRegistry.addName(furnaceDummyDiamond, "Modular Furnace Dummy Diamond");
		LanguageRegistry.addName(furnaceDummyEmerald, "Modular Furance Dummy Emerald");
		LanguageRegistry.addName(lavaCore, "Lava Core");
		LanguageRegistry.addName(furnaceDummyIO, "Modular Furnace IO");
		LanguageRegistry.addName(furnaceDummyActiveIO, "Modular Furnace IO Active");
		LanguageRegistry.addName(furnaceCoreSmeltery, "Modular Furnace Core Smeltery");
		LanguageRegistry.addName(furnaceDummySmeltery, "Modular Furnace Smeltery Dummy");
		LanguageRegistry.addName(furnaceSmelteryBrick, "Modular Furnace Smeltery Brick");
		
		LanguageRegistry.addName(furnaceCoreMulti, "Multi Furnace Core");
		
		LanguageRegistry.addName(copperIngot, "Copper Ingot");
		LanguageRegistry.addName(aluminiumIngot, "Aluminium Ingot");
		LanguageRegistry.addName(tinIngot, "Tin Ingot");
		
		LanguageRegistry.addName(overLayTexture, "Tough Glass");
		LanguageRegistry.addName(smelterOverlay, "Tinted Glass");


	
		LanguageRegistry.addName(paintBrush, "Paint Brush");




	}

	public static void registerCraftingRecipes()
	{
		CraftingManager.getInstance().addRecipe(new ItemStack(furnaceCore, 1),
				"XXX",
				"XxX",
				"XXX", 'X', Item.ingotIron, 'x', Block.furnaceIdle);

		CraftingManager.getInstance().addRecipe(new ItemStack(furnaceDummyIO, 1),
				"XXX",
				"XxX",
				"XXX", 'X', Block.cobblestone, 'x', Block.dispenser);

		CraftingManager.getInstance().addRecipe(new ItemStack(crafterInactive, 1),
				"XxX",
				"xCx",
				"XxX", 'X', Item.ingotIron, 'x', new ItemStack(Item.dyePowder, 9, 4), 'C', Block.workbench); 

		CraftingManager.getInstance().addRecipe(new ItemStack(lavaCore, 1),
				"OXO",
				"XCX",
				"OXO", 'X', Block.fenceIron, 'C', Item.bucketLava, 'O', Block.obsidian); 

		CraftingManager.getInstance().addRecipe(new ItemStack(furnaceCoreSmeltery, 1),
				"XXX",
				"XxX",
				"XXX", 'X', BlockManager.furnaceSmelteryBrick, 'x', BlockManager.furnaceCore);
		
		CraftingManager.getInstance().addRecipe(new ItemStack(furnaceCoreMulti, 1),
				"XXX",
				"XxX",
				"XXX", 'X', Block.furnaceIdle, 'x', BlockManager.furnaceCore);
		
		CraftingManager.getInstance().addRecipe(new ItemStack(furnaceSmelteryBrick, 1),
				"OXO",
				"XOX",
				"OXO", 'X', Block.stoneBrick, 'O', Block.stone); 
		
		CraftingManager.getInstance().addRecipe(new ItemStack(overLayTexture, 8),
				"XXX",
				"XOX",
				"XXX", 'X', Item.ingotIron, 'O', Block.glass); 
		
        ItemStack ink =  new ItemStack(Item.dyePowder, 1, 0);
		CraftingManager.getInstance().addShapelessRecipe(new ItemStack(smelterOverlay, 1), ink, new ItemStack(Block.glass, 1));
		
		
	}
}

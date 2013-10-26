package modularfurnace.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
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
	
	//Ores
	public static Block copperOreUniversal = null;
	public static Block aluminiumOreUniversal = null;
	public static Block tinOreUniversal = null;
	
	//Items
	public static Item copperIngot = null;
	public static Item aluminiumIngot = null;
	public static Item tinIngot = null;

	public static void registerBlocks()
	{
		furnaceCore = new BlockFurnaceCore(Reference.furnaceCoreID, false);
		furnaceCoreActive = new BlockFurnaceCore(Reference.furnaceCoreActiveID, true);
		furnaceDummy = new BlockFurnaceDummy(Reference.furnaceDummyID);
		furnaceDummyRedstone = new BlockFurnaceDummyRedstone(Reference.furnaceDummyIDRedstone);
		furnaceDummyGlowStone = new BlockFurnaceDummyIron(Reference.furnaceDummyIDGlowStone);
		furnaceDummyDiamond = new BlockFurnaceDummyDiamond(Reference.furnaceDummyIDDiamond);
		crafterInactive = new BlockCrafterInactive(Reference.crafterInactive, Material.wood);
		crafterActive = new BlockCrafterActive(Reference.crafterActive);
		furnaceDummyEmerald = new BlockFurnaceDummyEmerald(Reference.furnaceDummyIDEmerald);
		lavaCore = new BlockLavaCore(Reference.lavaCore, Material.iron);
		furnaceDummyIO = new BlockFurnaceDummyIO(Reference.furnaceDummyIOID, Material.rock);
		furnaceDummyActiveIO = new BlockFurnaceDummyIOActive(Reference.furnaceDummyActiveIOID);
		furnaceCoreSmeltery = new BlockFurnaceCoreSmeltery(Reference.furnaceCoreSmelteryID, false);
		furnaceCoreSmelteryActive = new BlockFurnaceCoreSmeltery(Reference.furnaceCoreSmelteryActiveID, true);
		furnaceDummySmeltery = new BlockFurnaceDummySmeltery(Reference.furnaceDummySmelteryID);
		furnaceSmelteryBrick = new BlockFurnaceSmelteryBrick(Reference.furnaceSmelteryBrickID);
		
		copperOreUniversal = new BlockCopperOreUniversal(Reference.copperOreUniversalID);
		copperIngot = new MetalCopperIngot(Reference.copperIngotID);

		aluminiumOreUniversal = new BlockAluminiumOreUniversal(Reference.aluminiumOreUniversalID);
		aluminiumIngot = new MetalAluminiumIngot(Reference.aluminiumIngotID);
		
		tinOreUniversal = new BlockTinOreUniversal(Reference.tinOreUniversalID);
		tinIngot = new MetalTinIngot(Reference.tinIngotID);

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
		
		GameRegistry.registerBlock(copperOreUniversal, "copperOreUniversal");
		GameRegistry.registerItem(copperIngot, "copperIngot");
		
		GameRegistry.registerBlock(aluminiumOreUniversal, "aluminiumOreUniversal");
		GameRegistry.registerItem(aluminiumIngot, "aluminiumIngot");
		
		GameRegistry.registerBlock(tinOreUniversal, "tinOreUniversal");
		GameRegistry.registerItem(tinIngot, "tinIngot");
		

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
		
		LanguageRegistry.addName(copperOreUniversal, "Copper Ore");
		LanguageRegistry.addName(copperIngot, "Copper Ingot");
		
		LanguageRegistry.addName(aluminiumOreUniversal, "Aluminium Ore");
		LanguageRegistry.addName(aluminiumIngot, "Aluminium Ingot");
		
		LanguageRegistry.addName(tinOreUniversal, "Tin Ore");
		LanguageRegistry.addName(tinIngot, "Tin Ingot");




	}

	public static void registerCraftingRecipes()
	{
		CraftingManager.getInstance().addRecipe(new ItemStack(furnaceCore, 1),
				"XXX",
				"XxX",
				"XXX", 'X', Block.stoneBrick, 'x', Block.furnaceIdle);

		CraftingManager.getInstance().addRecipe(new ItemStack(furnaceDummyIO, 1),
				"XXX",
				"XxX",
				"XXX", 'X', Block.stoneBrick, 'x', Block.dispenser);

		CraftingManager.getInstance().addRecipe(new ItemStack(crafterInactive, 1),
				"XxX",
				"xCx",
				"XxX", 'X', Item.ingotIron, 'x', new ItemStack(Item.dyePowder, 9, 4), 'C', Block.workbench); 

		CraftingManager.getInstance().addRecipe(new ItemStack(lavaCore, 1),
				"OXO",
				"XCX",
				"OXO", 'X', Block.glass, 'C', Item.bucketLava, 'O', Block.obsidian); 

		CraftingManager.getInstance().addRecipe(new ItemStack(furnaceCoreSmeltery, 1),
				"XXX",
				"XxX",
				"XXX", 'X', BlockManager.furnaceSmelteryBrick, 'x', BlockManager.furnaceCore);
		
		CraftingManager.getInstance().addRecipe(new ItemStack(furnaceSmelteryBrick, 1),
				"OXO",
				"XOX",
				"OXO", 'X', Block.brick, 'O', Block.obsidian); 


	}
}

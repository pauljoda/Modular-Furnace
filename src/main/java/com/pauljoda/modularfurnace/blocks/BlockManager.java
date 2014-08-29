package com.pauljoda.modularfurnace.blocks;

import com.pauljoda.modularfurnace.util.GeneralSettings;
import com.pauljoda.modularfurnace.ModularFurnace;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class BlockManager
{
	public static Block furnaceCore;
	public static Block furnaceDummy;
	public static Block furnaceDummyRedstone;
	public static Block furnaceDummyGlowStone;
	public static Block furnaceDummyDiamond;
	public static Block crafterInactive;
	public static Block crafterActive;
	public static Block furnaceDummyEmerald;
	public static Block furnaceCoreActive;
	public static Block furnaceDummyIO;
	public static Block furnaceDummyActiveIO;
	public static Block furnaceAddition;
	public static Block furnaceAdditionActive;

	//OverLays
	public static Block overLayTexture;
	public static Block smelterOverlay;

	public static void registerBlocks()
	{
		//Creation
		furnaceCore = new BlockFurnaceCore(false).setBlockName("blockFurnaceCore").setCreativeTab(ModularFurnace.tabModularFurnace);
		furnaceCoreActive = new BlockFurnaceCore(true).setLightLevel(1F).setBlockName("blockFurnaceCoreActive");
		furnaceDummy = new BlockFurnaceDummy();
		furnaceDummyRedstone = new BlockFurnaceDummy();
		furnaceDummyGlowStone = new BlockFurnaceDummy();
		furnaceDummyDiamond = new BlockFurnaceDummy();
		crafterInactive = new BlockCrafterInactive(Material.wood);
		crafterActive = new BlockCrafterActive();
		furnaceDummyEmerald = new BlockFurnaceDummy();
		furnaceDummyIO = new BlockFurnaceDummyIO(Material.rock);
		furnaceDummyActiveIO = new BlockFurnaceDummyIOActive();
		furnaceAddition = new BlockFurnaceAddition();
		furnaceAdditionActive = new BlockFurnaceAdditionActive();

		if(GeneralSettings.useTextures)
			overLayTexture = new BlockTextureOverlay(GeneralSettings.textureName, "overLayTexture", true);
		else
			overLayTexture = new BlockTextureOverlay("modularfurnace:custom_overlay", "overLayTexture", true);

		smelterOverlay = new BlockTextureOverlay("modularfurnace:smelterOverlay","smelterOverlay", false);
	}

	public static void register()
	{
		//Register
		GameRegistry.registerBlock(furnaceCore, "modularfurnace:blockFurnaceCore");
		GameRegistry.registerBlock(furnaceCoreActive, "modularfurnace:blockFurnaceCoreActive");
		GameRegistry.registerBlock(furnaceDummy, "modularfurnace:blockFurnaceDummy");
		GameRegistry.registerBlock(furnaceDummyRedstone, "modularfurnace:blockFurnaceDummyRedstone");
		GameRegistry.registerBlock(furnaceDummyGlowStone, "modularfurnace:blockFurnaceDummyGlowStone");
		GameRegistry.registerBlock(crafterInactive, "modularfurnace:crafterInactive");
		GameRegistry.registerBlock(crafterActive, "modularfurnace:crafterActive");
		GameRegistry.registerBlock(furnaceDummyDiamond, "modularfurnace:blockFurnaceDummyDiamond");
		GameRegistry.registerBlock(furnaceDummyEmerald, "modularfurnace:blockFurnaceDummyEmerald");
		GameRegistry.registerBlock(furnaceDummyIO, "modularfurnace:furnaceDummyIO");
		GameRegistry.registerBlock(furnaceDummyActiveIO, "modularfurnace:furnaceDummyActiveIO");
		GameRegistry.registerBlock(furnaceAddition, "modularfurnace:furnaceAddition");
		GameRegistry.registerBlock(furnaceAdditionActive, "modularfurnace:furnaceAdditionActive");
		GameRegistry.registerBlock(overLayTexture, "modularfurnace:overLayTexture");
		GameRegistry.registerBlock(smelterOverlay, "modularfurnace:smelterOverlay");

	}

	public static void registerCraftingRecipes()
	{
		CraftingManager.getInstance().addRecipe(new ItemStack(furnaceCore, 1),
				"XXX",
				"XxX",
				"XXX", 'X', Items.iron_ingot, 'x', Blocks.furnace);

		CraftingManager.getInstance().addRecipe(new ItemStack(furnaceDummyIO, 1),
				"XXX",
				"XxX",
				"XXX", 'X', Blocks.cobblestone, 'x', Blocks.dispenser);
		
		CraftingManager.getInstance().addRecipe(new ItemStack(furnaceAddition, 1),
				"XXX",
				"XxX",
				"XXX", 'X', Items.iron_ingot, 'x', furnaceCore);

		CraftingManager.getInstance().addRecipe(new ItemStack(crafterInactive, 1),
				"XxX",
				"xCx",
				"XxX", 'X', Items.iron_ingot, 'x', new ItemStack(Items.dye, 9, 4), 'C', Blocks.crafting_table); 

		CraftingManager.getInstance().addRecipe(new ItemStack(overLayTexture, 8),
				"XXX",
				"XOX",
				"XXX", 'X', Items.iron_ingot, 'O', Blocks.glass); 

		ItemStack ink =  new ItemStack(Items.dye, 1, 0);
		CraftingManager.getInstance().addShapelessRecipe(new ItemStack(smelterOverlay, 1), ink, new ItemStack(Blocks.glass, 1));
	}

}

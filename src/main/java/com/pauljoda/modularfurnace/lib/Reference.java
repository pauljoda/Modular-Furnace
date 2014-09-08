package com.pauljoda.modularfurnace.lib;

import com.pauljoda.modularfurnace.blocks.BlockManager;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import com.pauljoda.modularfurnace.util.GeneralSettings;
import com.pauljoda.modularfurnace.tileentity.TileEntityFurnaceCore;


public class Reference {

	//Used for mcmod.info
	public static final String MOD_ID = "modularfurnace";
	public static final String MOD_NAME = "Modular Furnace";
	public static final String Version = "3.3";
	public static final String MCVersion = "1.7.10";
	public static final String CHANNEL_NAME = MOD_ID;

	public static final String UPDATE_TOOLTIP = "An updated version of this mod is available";

	//GUI ids
	public static int modularFurnaceGui = 0;
	public static int modularFurnaceGuiCrafter = 1;

	//Getting GUI for Furnace
	public static int getGui(World world, int x, int y, int z)
	{
		TileEntityFurnaceCore tileEntity = (TileEntityFurnaceCore)world.getTileEntity(x, y, z);

		if(tileEntity.crafterEnabled)
		{
			return 1;
		}
		return 0;
	}

	//Banned blocks for formation of the furnace
	/*	public static int[] badBlocks =
		{0, Block.wood.blockID, Block.planks.blockID,
			Block.dirt.blockID, Block.ice.blockID,
			Block.snow.blockID, Block.bookShelf.blockID,
			Block.leaves.blockID, Block.melon.blockID,
			Block.pumpkin.blockID, Block.tnt.blockID,
			Block.cloth.blockID, Block.hay.blockID,
			Block.grass.blockID, Block.bedrock.blockID};
	 */

	//Blocks that are from my mod, used to prevent overlaying on reload
	public static String[] modularTiles = 
		{BlockManager.furnaceDummy.getUnlocalizedName(),
		BlockManager.furnaceDummyRedstone.getUnlocalizedName(),
		BlockManager.furnaceDummyGlowStone.getUnlocalizedName(),
		BlockManager.furnaceDummyDiamond.getUnlocalizedName(),
		BlockManager.crafterActive.getUnlocalizedName(),
		BlockManager.furnaceDummyEmerald.getUnlocalizedName(),
		BlockManager.furnaceDummyActiveIO.getUnlocalizedName(),
		BlockManager.furnaceAdditionActive.getUnlocalizedName()
		};

	//Checks if the block is valid to form furnace
	public static boolean isValidBlock(String blockId)
	{

		for(int i = 0; i < GeneralSettings.bannedBlocks.length; i++)
		{
			if(blockId.equals(GeneralSettings.bannedBlocks[i]))
			{
				return false;
			}
		}

		if(blockId.equals(BlockManager.crafterInactive.getUnlocalizedName()) || blockId.equals(BlockManager.furnaceDummyIO.getUnlocalizedName()) || blockId.equals(Blocks.redstone_block.getUnlocalizedName()) || blockId.equals(BlockManager.furnaceAddition.getUnlocalizedName()))
			return true;

		return true;
	}

	//Checks if it is an active dummy
	public static boolean isModularTile(String blockId) {
		for(int i = 0; i < modularTiles.length; i++)
		{
			if(blockId.equals(modularTiles[i]))
				return true;
		}
		return false;
	}

	public static boolean isBadBlock(Block blockId) {

		if(blockId == BlockManager.crafterInactive || blockId == BlockManager.crafterActive || blockId == BlockManager.furnaceDummyIO || blockId == Blocks.redstone_block || blockId == BlockManager.furnaceAddition || blockId == BlockManager.furnaceAdditionActive)
			return false;
		
		if(blockId.hasTileEntity(0))
			return true;

		if(!blockId.isNormalCube())
			return true;

		int oreDictCheck = OreDictionary.getOreID(new ItemStack(blockId));
		int isWood = OreDictionary.getOreID("logWood");
		int isPlank = OreDictionary.getOreID("plankWood");
		if(oreDictCheck == isWood || oreDictCheck == isPlank)
			return true;

		return false;
	}

	public static double getSpeedMultiplierForBlock(Block block)
	{
		if(block == Blocks.redstone_block)
			return 1.2;
		else if(block == Blocks.gold_block)
			return 1;
		else if(block == Blocks.diamond_block)
			return 3;
		else if(block == Blocks.netherrack)
			return 1;
		else if(block == Blocks.lapis_block)
			return 1;
		else if(block == Blocks.sandstone)
			return 0.3;
		else if(block == Blocks.brick_block)
			return 0.5;
		else if(block == Blocks.soul_sand)
			return 0.3;
		else if(block == Blocks.nether_brick)
			return 1.0;
		else if(block == Blocks.hardened_clay)
			return 0.6;
		else
			return 0;
	}

	public static double getEfficiencyMultiplierForBlock(Block block)
	{
		if(block == Blocks.iron_block)
			return 0.2;
		else if(block == Blocks.coal_block)
			return 0.1;
		else if(block == Blocks.redstone_block)
			return -1;
		else if(block == Blocks.diamond_block)
			return 0.1;
		else if(block == Blocks.netherrack)
			return -0.7;
		else if(block == Blocks.stone)
			return 0.05;
		else if(block == Blocks.stonebrick)
			return 0.1;
		else if(block == Blocks.sand)
			return -0.5;
		else if(block == Blocks.lapis_block)
			return 0.2;
		else if(block == Blocks.sandstone)
			return 0.01;
		else if(block == Blocks.nether_brick)
			return -0.5;
		else if(block == Blocks.quartz_block)
			return 0.2;
		else if(block == Blocks.hardened_clay)
			return 0.1;
		else
			return 0.0;
	}
}
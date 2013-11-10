package modularfurnace.lib;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import modularfurnace.ModularFurnace;
import modularfurnace.tileentity.TileEntityFurnaceCore;


public class Reference {

	public static final String MOD_ID = "modularfurnace";
	public static final String MOD_NAME = "Modular Furnace";
	public static final String Version = "1.0";

	public static int furnaceCoreID = ModularFurnace.furnaceCoreID;
	public static int furnaceCoreActiveID = ModularFurnace.furnaceCoreActiveID;
	public static int furnaceDummyID = ModularFurnace.furnaceDummyID;
	public static int furnaceDummyIDRedstone = ModularFurnace.furnaceDummyIDRedstone;
	public static int furnaceDummyIDGlowStone = ModularFurnace.furnaceDummyIDGlowStone;
	public static int furnaceDummyIDDiamond = ModularFurnace.furnaceDummyIDDiamond;
	public static int crafterInactive = ModularFurnace.crafterInactiveID;
	public static int crafterActive = ModularFurnace.crafterActiveID;
	public static int furnaceDummyIDEmerald = ModularFurnace.furnaceDummyIDEmerald;
	public static int lavaCore = ModularFurnace.laveCoreID;
	public static int furnaceDummyIOID = ModularFurnace.furnaceDummyIOID;
	public static int furnaceDummyActiveIOID = ModularFurnace.furnaceDummyActiveIOID;
	public static int furnaceCoreSmelteryID = ModularFurnace.furnaceCoreSmelteryID;
	public static int furnaceCoreSmelteryActiveID = ModularFurnace.furnaceCoreSmelteryActiveID;
	public static int furnaceDummySmelteryID = ModularFurnace.furnaceDummySmelteryID;
	public static int furnaceSmelteryBrickID = ModularFurnace.furnaceSmelteryBrickID;
	public static int copperIngotID = ModularFurnace.copperIngotID;
	public static int aluminiumIngotID = ModularFurnace.aluminiumIngotID;
	public static int tinIngotID = ModularFurnace.tinIngotID;
	public static int paintBrushID = ModularFurnace.paintBrushID;
	public static int textureOverlayID = ModularFurnace.textureOverlayID;
	public static int smelterOverlayID = ModularFurnace.smelterOverlayID;

	public static int modularFurnaceGui = 0;
	public static int modularFurnaceGuiCrafter = 1;
	public static int modularFurnaceSmelteryGui = 2;

	public static int getGui(World world, int x, int y, int z)
	{
		TileEntityFurnaceCore tileEntity = (TileEntityFurnaceCore)world.getBlockTileEntity(x, y, z);
		//TileEntityFurnaceCoreSmeltery tileEntity1 = (TileEntityFurnaceCoreSmeltery)world.getBlockTileEntity(x, y, z);

		if(tileEntity.crafterEnabled)
		{
			return 1;
		}
		return 0;
	}

	public static int getGuiSmeltery(World world, int x, int y, int z)
	{

		return 2;
	}

	public static int[] badBlocks =
		{0, Block.wood.blockID, Block.planks.blockID,
			Block.dirt.blockID, Block.ice.blockID,
			Block.snow.blockID, Block.bookShelf.blockID,
			Block.leaves.blockID, Block.melon.blockID,
			Block.pumpkin.blockID, Block.tnt.blockID,
			Block.cloth.blockID, Block.hay.blockID,
			Block.hardenedClay.blockID, Block.grass.blockID,
			Block.stoneBrick.blockID};
	
	public static int[] modularTiles = 
		{furnaceDummyID,
		furnaceDummyIDRedstone,
		furnaceDummyIDGlowStone,
		furnaceDummyIDDiamond,
		crafterActive,
		furnaceDummyIDEmerald,
		furnaceDummyActiveIOID
		};


	public static boolean isValidBlock(int blockId)
	{
		for(int i = 0; i < badBlocks.length; i++)
		{
			if(blockId == badBlocks[i])
			{
				return false;
			}
		}
		if(blockId == crafterInactive || blockId == furnaceDummyIOID)
			return true;
		if(!Block.isNormalCube(blockId))
			return false;
		else if(Block.blocksList[blockId].hasTileEntity(blockId))
			return false;
		
		
		return true;
	}

	public static boolean isModularTile(int blockId) {
		for(int i = 0; i < modularTiles.length; i++)
		{
			if(blockId == modularTiles[i])
				return true;
		}
		return false;
	}
}





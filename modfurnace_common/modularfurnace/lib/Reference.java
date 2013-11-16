package modularfurnace.lib;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import modularfurnace.GeneralSettings;
import modularfurnace.ModularFurnace;
import modularfurnace.tileentity.TileEntityFurnaceCore;


public class Reference {

	//Used for mcmod.info
	public static final String MOD_ID = "modularfurnace";
	public static final String MOD_NAME = "Modular Furnace";
	public static final String Version = "2.6";
    public static final String CHANNEL_NAME = MOD_ID;
    public static final String VERSION_NUMBER = "2.6";
    public static final int VERSION_CHECK_ATTEMPTS = 3;



	//Inbetween for id storage
	public static int furnaceCoreID = GeneralSettings.furnaceCoreID;
	public static int furnaceCoreActiveID = GeneralSettings.furnaceCoreActiveID;
	public static int furnaceDummyID = GeneralSettings.furnaceDummyID;
	public static int furnaceDummyIDRedstone = GeneralSettings.furnaceDummyIDRedstone;
	public static int furnaceDummyIDGlowStone = GeneralSettings.furnaceDummyIDGlowStone;
	public static int furnaceDummyIDDiamond = GeneralSettings.furnaceDummyIDDiamond;
	public static int crafterInactive = GeneralSettings.crafterInactiveID;
	public static int crafterActive = GeneralSettings.crafterActiveID;
	public static int furnaceDummyIDEmerald = GeneralSettings.furnaceDummyIDEmerald;
	public static int lavaCore = GeneralSettings.laveCoreID;
	public static int furnaceDummyIOID = GeneralSettings.furnaceDummyIOID;
	public static int furnaceDummyActiveIOID = GeneralSettings.furnaceDummyActiveIOID;
	public static int furnaceCoreSmelteryID = GeneralSettings.furnaceCoreSmelteryID;
	public static int furnaceCoreSmelteryActiveID = GeneralSettings.furnaceCoreSmelteryActiveID;
	public static int furnaceDummySmelteryID = GeneralSettings.furnaceDummySmelteryID;
	public static int furnaceSmelteryBrickID = GeneralSettings.furnaceSmelteryBrickID;
	public static int copperIngotID = GeneralSettings.copperIngotID;
	public static int aluminiumIngotID = GeneralSettings.aluminiumIngotID;
	public static int tinIngotID = GeneralSettings.tinIngotID;
	public static int paintBrushID = GeneralSettings.paintBrushID;
	public static int textureOverlayID = GeneralSettings.textureOverlayID;
	public static int smelterOverlayID = GeneralSettings.smelterOverlayID;

	//GUI ids
	public static int modularFurnaceGui = 0;
	public static int modularFurnaceGuiCrafter = 1;
	public static int modularFurnaceSmelteryGui = 2;

	//Getting GUI for Furnace
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

	//GUI for smeltery
	public static int getGuiSmeltery(World world, int x, int y, int z)
	{

		return 2;
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
	public static int[] modularTiles = 
		{furnaceDummyID,
		furnaceDummyIDRedstone,
		furnaceDummyIDGlowStone,
		furnaceDummyIDDiamond,
		crafterActive,
		furnaceDummyIDEmerald,
		furnaceDummyActiveIOID,
		};

	//Checks if the block is valid to form furnace
	public static boolean isValidBlock(int blockId)
	{
		for(int i = 0; i < GeneralSettings.bannedBlocks.length; i++)
		{
			if(blockId == GeneralSettings.bannedBlocks[i])
			{
				return false;
			}
		}
		if(blockId == crafterInactive || blockId == furnaceDummyIOID || blockId == Block.blockRedstone.blockID)
			return true;
		if(!Block.isNormalCube(blockId))
			return false;
		else if(Block.blocksList[blockId].hasTileEntity(blockId))
			return false;
		
		String oreDict = OreDictionary.getOreName(blockId);
		int oreDictCheck = OreDictionary.getOreID(oreDict);
		int isWood = OreDictionary.getOreID("logWood");
		int isPlank = OreDictionary.getOreID("plankWood");
		if(oreDictCheck == isWood || oreDictCheck == isPlank)
			return false;
		
				
		
		return true;
	}

	//Checks if it is an active dummy
	public static boolean isModularTile(int blockId) {
		for(int i = 0; i < modularTiles.length; i++)
		{
			if(blockId == modularTiles[i])
				return true;
		}
		return false;
	}
}





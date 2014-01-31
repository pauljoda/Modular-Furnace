package modularfurnace.tileentity;

import java.util.Random;

import modularfurnace.blocks.BlockManager;
import modularfurnace.blocks.BlockMultiFurnaceCore;
import modularfurnace.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityFurnaceMultiCore extends TileEntity implements ISidedInventory
{
	//Automation related 
	private static final int[] slots_top = new int[] {0,1,2,3,4,5,6,7,8};
	private static final int[] slots_bottom = new int[] {10,11,12,13,14,15,16,17,18};
	private static final int[] slots_sides = new int[] {9};

	//Storage for blocks if they have meaning
	public int redstoneBlocksInFurnace = 0;
	public int redstoneMultiplier = 6;
	public int cookSpeed = 300;
	public int ironBlocksInFurnace = 0;
	public boolean emeralds;

	//Furnace related things
	private ItemStack[] furnaceItems = new ItemStack[19];
	public int furnaceBurnTime;
	public int currentItemBurnTime;
	public int furnaceCookTime;
	private String field_94130_e;

	//Booleans
	public boolean isValidMultiblock = false;
	public boolean diamonds = false;

	//Randomizer
	Random r = new Random();

	public TileEntityFurnaceMultiCore()
	{
	}


	//Turns dummies back to their respective blocks
	public void invalidateMultiblock()
	{
		int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);

		ironBlocksInFurnace = 0;
		redstoneBlocksInFurnace = 0;
		diamonds = false;
		emeralds = false;

		revertDummies();
		isValidMultiblock = false;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metadata, 2);

	}

	//Called to check if this is already formed
	public boolean getIsValid()
	{
		return isValidMultiblock;
	}

	//Checks to see if the structure is valid
	public boolean checkIfProperlyFormed()
	{
		int dir = (worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
		int depthMultiplier = ((dir == 2 || dir == 4) ? 1 : -1);
		boolean forwardZ = ((dir == 2) || (dir == 3));

		/*
		 *          FORWARD     BACKWARD
		 * North:   -z              +z
		 * South:   +z              -z
		 * East:    +x              -x
		 * West:    -x              +x
		 * 
		 * Should move BACKWARD for depth (facing = direction of block face, not direction of player looking at face)
		 */

		for(int horiz = -1; horiz <= 1; horiz++)    // Horizontal (X or Z)
		{
			for(int vert = -1; vert <= 1; vert++)   // Vertical (Y)
			{
				for(int depth = 0; depth <= 2; depth++) // Depth (Z or X)
				{
					int x = xCoord + (forwardZ ? horiz : (depth * depthMultiplier));
					int y = yCoord + vert;
					int z = zCoord + (forwardZ ? (depth * depthMultiplier) : horiz);

					int blockId = worldObj.getBlockId(x, y, z);

					if(horiz == 0 && vert == 0)
					{
						if(depth == 0)  // Looking at self, move on!
							continue;

						if(depth == 1)  // Center must be air!
						{
							if(blockId != 0)
							{
								return false;
							}
							else                    		
								continue;

						}
					}

					//if(blockId != Block.cobblestone.blockID && blockId != Block.blockRedstone.blockID && blockId != Block.blockIron.blockID && blockId != Block.blockDiamond.blockID && blockId != ModularFurnace.crafterInactiveID && blockId != Block.blockEmerald.blockID && blockId != ModularFurnace.furnaceDummyIOID)
					if(!Reference.isValidBlock(blockId))
					{
						if(Reference.isModularTile(blockId))
							return true;

						return false;
					}
				}

			}

		}

		return true;
	}

	//Converts blocks into their respective dummies
	public void convertDummies()
	{
		int dir = (worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
		int depthMultiplier = ((dir == 2 || dir == 4) ? 1 : -1);
		boolean forwardZ = ((dir == 2) || (dir == 3));


		/*
		 *          FORWARD     BACKWARD
		 * North:   -z              +z
		 * South:   +z              -z
		 * East:    +x              -x
		 * West:    -x              +x
		 * 
		 * Should move BACKWARD for depth (facing = direction of block face, not direction of player looking at face)
		 */

		for(int horiz = -1; horiz <= 1; horiz++)    // Horizontal (X or Z)
		{
			for(int vert = -1; vert <= 1; vert++)   // Vertical (Y)
			{
				for(int depth = 0; depth <= 2; depth++) // Depth (Z or X)
				{
					int x = xCoord + (forwardZ ? horiz : (depth * depthMultiplier));
					int y = yCoord + vert;
					int z = zCoord + (forwardZ ? (depth * depthMultiplier) : horiz);

					if(horiz == 0 && vert == 0 && depth == 1)
					{

						continue;

					}
					if(horiz == 0 && vert == 0)
						if(depth == 0)
							continue;
					if(worldObj.getBlockId(x, y, z) == Block.blockRedstone.blockID)
					{
						this.redstoneBlocksInFurnace = this.redstoneBlocksInFurnace + 1;
						int icon = worldObj.getBlockId(x, y, z);
						int metadata = worldObj.getBlockMetadata(x, y, z);

						worldObj.setBlock(x, y, z, Reference.furnaceDummyMultiRedstoneID);

						worldObj.markBlockForUpdate(x, y, z);
						TileEntityMultiFurnaceDummy dummyTE = (TileEntityMultiFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);

						if(icon == BlockManager.furnaceDummyMultiRedstone.blockID)
						{
							icon = dummyTE.icon;
							metadata = dummyTE.metadata;
						}
						else
						{
							dummyTE.icon = icon;
							dummyTE.metadata = metadata;
						}

						dummyTE.setCore(this);

					}

					else if(worldObj.getBlockId(x, y, z) == Block.blockIron.blockID)
					{
						this.ironBlocksInFurnace = ironBlocksInFurnace + 1;
						int icon = worldObj.getBlockId(x, y, z);
						int metadata = worldObj.getBlockMetadata(x, y, z);

						worldObj.setBlock(x, y, z, Reference.furnaceDummyMultiIronID);

						worldObj.markBlockForUpdate(x, y, z);
						TileEntityMultiFurnaceDummy dummyTE = (TileEntityMultiFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);

						if(icon == BlockManager.furnaceDummyMultiIron.blockID)
						{
							icon = dummyTE.icon;
							metadata = dummyTE.metadata;
						}
						else
						{
							dummyTE.icon = icon;
							dummyTE.metadata = metadata;
						}

						dummyTE.setCore(this);
					}

					else if(worldObj.getBlockId(x, y, z) == Block.blockEmerald.blockID)
					{
						emeralds = true;
						int icon = worldObj.getBlockId(x, y, z);
						int metadata = worldObj.getBlockMetadata(x, y, z);

						worldObj.setBlock(x, y, z, Reference.furnaceDummyMultiEmeraldID);

						worldObj.markBlockForUpdate(x, y, z);
						TileEntityMultiFurnaceDummy dummyTE = (TileEntityMultiFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);

						if(icon == BlockManager.furnaceDummyMultiEmerald.blockID)
						{
							icon = dummyTE.icon;
							metadata = dummyTE.metadata;
						}
						else
						{
							dummyTE.icon = icon;
							dummyTE.metadata = metadata;
						}

						dummyTE.setCore(this);
					}

					else if(!Reference.isModularTile(worldObj.getBlockId(x, y, z)) && worldObj.getBlockId(x, y, z) != 0)
					{

						int icon = worldObj.getBlockId(x, y, z);
						int metadata = worldObj.getBlockMetadata(x, y, z);

						worldObj.setBlock(x, y, z, Reference.furnaceMultiDummyID);

						worldObj.markBlockForUpdate(x, y, z);
						TileEntityMultiFurnaceDummy dummyTE = (TileEntityMultiFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);

						if(icon == BlockManager.furnaceMultiDummy.blockID)
						{
							icon = dummyTE.icon;
							metadata = dummyTE.metadata;
						}
						else
						{
							dummyTE.icon = icon;
							dummyTE.metadata = metadata;
						}

						dummyTE.setCore(this);

					}

				}
			}

			isValidMultiblock = true;
		}
	}

	//Turns dummies back to their blocks
	private void revertDummies()
	{

		int dir = (worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
		int depthMultiplier = ((dir == 2 || dir == 4) ? 1 : -1);
		boolean forwardZ = ((dir == 2) || (dir == 3));
		isValidMultiblock = false;


		/*
		 *          FORWARD     BACKWARD
		 * North:   -z              +z
		 * South:   +z              -z
		 * East:    +x              -x
		 * West:    -x              +x
		 * 
		 * Should move BACKWARD for depth (facing = direction of block face, not direction of player looking at face)
		 */


		for(int horiz = -1; horiz <= 1; horiz++)    // Horizontal (X or Z)
		{
			for(int vert = -1; vert <= 1; vert++)   // Vertical (Y)
			{
				for(int depth = 0; depth <= 2; depth++) // Depth (Z or X)
				{
					int x = xCoord + (forwardZ ? horiz : (depth * depthMultiplier));
					int y = yCoord + vert;
					int z = zCoord + (forwardZ ? (depth * depthMultiplier) : horiz);

					int blockId = worldObj.getBlockId(x, y, z);

					if(horiz == 0 && vert == 0 && depth == 1)
					{
						continue;     	
					}
					if(horiz == 0 && vert == 0)
						if(depth == 0)
							continue;

					if(blockId == BlockManager.furnaceMultiDummy.blockID)
					{
						TileEntityMultiFurnaceDummy dummyTE = (TileEntityMultiFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);

						worldObj.setBlock(x, y, z, dummyTE.getBlock().blockID);
						worldObj.setBlockMetadataWithNotify(x, y, z, dummyTE.metadata, 2);

						worldObj.markBlockForUpdate(x, y, z);
						continue;
					}

					if(blockId == BlockManager.furnaceDummyMultiRedstone.blockID)
					{
						worldObj.setBlock(x, y, z, Block.blockRedstone.blockID);
						worldObj.markBlockForUpdate(x, y, z);
						continue;

					}
					if(blockId == BlockManager.furnaceDummyMultiIron.blockID)
					{
						worldObj.setBlock(x, y, z, Block.blockIron.blockID);
						worldObj.markBlockForUpdate(x, y, z);
						continue;

					}
					if(blockId == BlockManager.furnaceDummyMultiEmerald.blockID)
					{
						worldObj.setBlock(x, y, z, Block.blockEmerald.blockID);
						worldObj.markBlockForUpdate(x, y, z);
						continue;
					}

					worldObj.markBlockForUpdate(x, y, z);
				}
			}

		}

		isValidMultiblock = false;
	}


	//Furnace stuff
	@Override
	public void updateEntity()
	{
		boolean flag = this.furnaceBurnTime > 0;
		boolean flag1 = false;

		if (this.furnaceBurnTime > 0)
		{
			--this.furnaceBurnTime;
		}

		if (!this.worldObj.isRemote)
		{
			if (this.furnaceBurnTime == 0 && this.canSmelt())
			{
				this.currentItemBurnTime = this.furnaceBurnTime = this.scaledBurnTime();

				if (this.furnaceBurnTime > 0)
				{
					flag1 = true;

					if (this.furnaceItems[9] != null)
					{
						--this.furnaceItems[9].stackSize;

						if (this.furnaceItems[9].stackSize == 0)
						{
							this.furnaceItems[9] = this.furnaceItems[9].getItem().getContainerItemStack(furnaceItems[9]);
						}
					}
				}
			}

			if (this.isBurning() && this.canSmelt())
			{
				++this.furnaceCookTime;

				if (this.furnaceCookTime == this.getSpeedMultiplier())
				{
					this.furnaceCookTime = 0;
					this.smeltItem();
					flag1 = true;
				}
			}
			else
			{
				this.furnaceCookTime = 0;
			}

			if (flag != this.furnaceBurnTime > 0)
			{
				flag1 = true;
				BlockMultiFurnaceCore.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);			}
		}

		if (flag1)
		{
			this.onInventoryChanged();
		}
	}

	//Furnace stuff
	@Override
	public int getSizeInventory()
	{
		return furnaceItems.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return furnaceItems[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int count)
	{       
		if(this.furnaceItems[slot] != null)
		{
			ItemStack itemStack;

			itemStack = furnaceItems[slot].splitStack(count);

			if(furnaceItems[slot].stackSize <= 0)
				furnaceItems[slot] = null;

			return itemStack;
		}

		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if(furnaceItems[slot] != null)
		{
			ItemStack stack = furnaceItems[slot];
			furnaceItems[slot] = null;
			return stack;
		}

		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack)
	{
		furnaceItems[slot] = itemStack;

		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
			itemStack.stackSize = getInventoryStackLimit();
	}

	@Override
	public String getInvName()
	{
		return this.isInvNameLocalized() ? this.field_94130_e : "multifurnace.container.multifurnacecore";
	}

	public void setGuiDisplayName(String par1Str)
	{
		this.field_94130_e = par1Str;
	}


	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer)
	{
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : entityPlayer.getDistanceSq((double)xCoord + 0.5, (double)yCoord + 0.5, (double)zCoord + 0.5) <= 64.0;
	}

	@Override
	public void openChest() { }

	@Override
	public void closeChest() { }

	//Re-writen for the I/O block
	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		if(par1 > 9 && par1 <= 18)
			return false;
		if(par1 == 9 && isItemFuel(par2ItemStack))
			return true;
		if(par1 >= 0 && par1 < 9)
			return true;
		else
			return false;

	}

	public static boolean isItemFuel(ItemStack par0ItemStack)
	{
		return getItemBurnTime(par0ItemStack) > 0;
	}

	public static int getItemBurnTime(ItemStack par0ItemStack)
	{
		if (par0ItemStack == null)
		{
			return 0;
		}
		else
		{
			int i = par0ItemStack.getItem().itemID;
			Item item = par0ItemStack.getItem();

			if (par0ItemStack.getItem() instanceof ItemBlock && Block.blocksList[i] != null)
			{
				Block block = Block.blocksList[i];

				if (block == Block.woodSingleSlab)
				{
					return 150;
				}

				if (block.blockMaterial == Material.wood)
				{
					return 300;
				}

				if (block == Block.coalBlock)
				{
					return 16000;
				}
			}

			if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) return 200;
			if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) return 200;
			if (item instanceof ItemHoe && ((ItemHoe) item).getMaterialName().equals("WOOD")) return 200;
			if (i == Item.stick.itemID) return 100;
			if (i == Item.coal.itemID) return 1600;
			if (i == Item.bucketLava.itemID) return 20000;
			if (i == Block.sapling.blockID) return 100;
			if (i == Item.blazeRod.itemID) return 2400;
			return GameRegistry.getFuelValue(par0ItemStack);
		}
	}



	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);

		tagCompound.getInteger("BlockMeta");
		isValidMultiblock = tagCompound.getBoolean("isValidMultiblock");


		NBTTagList itemsTag = tagCompound.getTagList("Items");
		furnaceItems = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < itemsTag.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)itemsTag.tagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.furnaceItems.length)
			{
				this.furnaceItems[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}


		this.furnaceBurnTime = tagCompound.getShort("BurnTime");
		this.furnaceCookTime = tagCompound.getShort("CookTime");
		this.currentItemBurnTime = this.scaledBurnTime();
		this.redstoneBlocksInFurnace = tagCompound.getShort("RedstoneBlock"); 
		this.ironBlocksInFurnace = tagCompound.getShort("IronStoneBlocks");
		this.diamonds = tagCompound.getBoolean("Diamonds");
		this.emeralds = tagCompound.getBoolean("Emeralds");


		if (tagCompound.hasKey("CustomName"))
		{
			this.field_94130_e = tagCompound.getString("CustomName");
		}

	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);

		tagCompound.setBoolean("isValidMultiblock", isValidMultiblock);

		tagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
		tagCompound.setShort("CookTime", (short)this.furnaceCookTime);
		tagCompound.setShort("RedstoneBlock", (short)this.redstoneBlocksInFurnace);
		tagCompound.setShort("IronStoneBlocks", (short)this.ironBlocksInFurnace);
		tagCompound.setBoolean("Diamonds", (boolean)this.diamonds);
		tagCompound.setBoolean("Emeralds",(boolean)this.emeralds);



		NBTTagList itemsList = new NBTTagList();

		for (int i = 0; i < this.furnaceItems.length; ++i)
		{
			if (this.furnaceItems[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				this.furnaceItems[i].writeToNBT(nbttagcompound1);
				itemsList.appendTag(nbttagcompound1);
			}
		}
		tagCompound.setTag("Items", itemsList);

		if (this.isInvNameLocalized())
		{
			tagCompound.setString("CustomName", this.field_94130_e);
		}

	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet)
	{
		readFromNBT(packet.data);
	}


	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int scaleVal)
	{ 
		int scale = this.getSpeedMultiplier();
		return this.furnaceCookTime * scaleVal / scale;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int scaleVal)
	{
		if(currentItemBurnTime == 0)
		{
			currentItemBurnTime = getSpeedMultiplier();
		} 
		return furnaceBurnTime * scaleVal / this.currentItemBurnTime;
	}

	public boolean isBurning()
	{
		return furnaceBurnTime > 0;
	}

	private boolean canSmelt()
	{
		if(!hasItems())
			return false;
		else
		{
			for(int i = 0; i <= 8; i++)
				if(FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItems[i])!= null)
				{

					{
						if(furnaceItems[i + 10] != null)
						{
							if(furnaceItems[i + 10].stackSize == furnaceItems[i + 10].getMaxStackSize())
								return false;

							if(furnaceItems[i] != null)
							{
								if(FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItems[i]).itemID != furnaceItems[i + 10].itemID)
									return false;
							}
						}
					}
					return true;
				}
				return false;
		}
	}

	private boolean hasItems()
	{
		for(int i = 0; i <= 8; i++)
		{
			if(furnaceItems[i] != null)
				return true;
		}
		return false;
	}

	public void smeltItem()
	{
		if (this.canSmelt())
		{
			for(int i = 0; i <= 8; i++)
			{
				if(furnaceItems[i] != null && FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItems[i]) != null)
				{
					ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItems[i]);

					if (this.furnaceItems[i + 10] == null)
					{
						this.furnaceItems[i + 10] = itemstack.copy();
					}
					else if (this.furnaceItems[i + 10].isItemEqual(itemstack))
					{
						furnaceItems[i + 10].stackSize += itemstack.stackSize;
					}

					--this.furnaceItems[i].stackSize;

					if (this.furnaceItems[i].stackSize <= 0)
					{
						this.furnaceItems[i] = null;
					}
				}
			}
		}
	}

	//Used in cook progress
	public int getSpeedMultiplier()
	{
		int red = countRedstone();
		return  (cookSpeed - (red * redstoneMultiplier));
	}

	//Counts redstone blocks in structure
	public int countRedstone()
	{
		int output = 0;
		int dir = (worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
		int depthMultiplier = ((dir == 2 || dir == 4) ? 1 : -1);
		boolean forwardZ = ((dir == 2) || (dir == 3));

		/*
		 *          FORWARD     BACKWARD
		 * North:   -z              +z
		 * South:   +z              -z
		 * East:    +x              -x
		 * West:    -x              +x
		 * 
		 * Should move BACKWARD for depth (facing = direction of block face, not direction of player looking at face)
		 */

		for(int horiz = -1; horiz <= 1; horiz++)    // Horizontal (X or Z)
		{
			for(int vert = -1; vert <= 1; vert++)   // Vertical (Y)
			{
				for(int depth = 0; depth <= 2; depth++) // Depth (Z or X)
				{
					int x = xCoord + (forwardZ ? horiz : (depth * depthMultiplier));
					int y = yCoord + vert;
					int z = zCoord + (forwardZ ? (depth * depthMultiplier) : horiz);

					int blockId = worldObj.getBlockId(x, y, z);

					if(horiz == 0 && vert == 0)
					{
						if(depth == 0)  // Looking at self, move on!
							continue;

						if(depth == 1)  // Center must be air!
						{
							continue;
						}
					}
					if(blockId == Reference.furnaceDummyMultiRedstoneID || blockId == Block.blockRedstone.blockID)
					{
						output++;
					}

				}
			}
		}	

		return output;
	}

	//Counts Iron Blocks in structure
	public int countIron()
	{
		int output = 0;
		int dir = (worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
		int depthMultiplier = ((dir == 2 || dir == 4) ? 1 : -1);
		boolean forwardZ = ((dir == 2) || (dir == 3));

		/*
		 *          FORWARD     BACKWARD
		 * North:   -z              +z
		 * South:   +z              -z
		 * East:    +x              -x
		 * West:    -x              +x
		 * 
		 * Should move BACKWARD for depth (facing = direction of block face, not direction of player looking at face)
		 */

		for(int horiz = -1; horiz <= 1; horiz++)    // Horizontal (X or Z)
		{
			for(int vert = -1; vert <= 1; vert++)   // Vertical (Y)
			{
				for(int depth = 0; depth <= 2; depth++) // Depth (Z or X)
				{
					int x = xCoord + (forwardZ ? horiz : (depth * depthMultiplier));
					int y = yCoord + vert;
					int z = zCoord + (forwardZ ? (depth * depthMultiplier) : horiz);

					int blockId = worldObj.getBlockId(x, y, z);

					if(horiz == 0 && vert == 0)
					{
						if(depth == 0)  // Looking at self, move on!
							continue;

						if(depth == 1)  // Center must be air!
						{
							continue;
						}
					}
					if(blockId == Reference.furnaceDummyMultiIronID || blockId == Block.blockIron.blockID)
					{
						output++;
					}

				}
			}
		}	

		return output;
	}

	//Reworked for I/O
	@Override
	public int[] getAccessibleSlotsFromSide(int par1)
	{
		switch(par1)
		{
		case 0 : return slots_bottom;
		case 1 : return slots_top;
		default : return slots_sides;

		}
		//return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
	}

	@Override
	public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
	{
		return this.isItemValidForSlot(par1, par2ItemStack);
	}
	@Override
	public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
	{
		return par3 != 0 || par1 != 1 || par2ItemStack.itemID == Item.bucketEmpty.itemID;
	}

	//Algorithm Behind the performance of the furnace
	/*public int scaledBurnTime()
	{
		int output = TileEntityFurnace.getItemBurnTime(furnaceItems[1]);

		output = (output + ((TileEntityFurnace.getItemBurnTime(furnaceItems[1]) / 8) * this.ironBlocksInFurnace)) - ((TileEntityFurnace.getItemBurnTime(furnaceItems[1]) / 10) * this.redstoneBlocksInFurnace);

		return output;

	}
	 */
	public double scaledBurnTimeTest(double input)
	{
		double output = input;

		for(int i = 0; i <= this.countIron(); i++)
		{
			output = (input / 4) + output;
		}

		for(int j = 0; j <= this.countRedstone(); j++)
		{
			if(output > 0)
				output = output - (input / 25);

			if(output <= 0)
				output = 10;
		}


		return output;

	}

	public double getEfficiency()
	{

		double current = this.scaledBurnTimeTest(16000);
		double output = 0;
		if(current >= 16000)
			output = (current / 16000) - 1;
		else
			output = -(16000 / current);

		return output;

	}


	public int scaledBurnTime()
	{

		if (redstoneBlocksInFurnace > 0 && ironBlocksInFurnace == 0)
		{
			return ((TileEntityFurnace.getItemBurnTime(furnaceItems[9])) - ((TileEntityFurnace.getItemBurnTime(furnaceItems[9]) / 25) * (redstoneBlocksInFurnace - 1))); 
		}

		if (redstoneBlocksInFurnace == 0 && ironBlocksInFurnace >0)
		{
			return (TileEntityFurnace.getItemBurnTime(furnaceItems[9]) + ((TileEntityFurnace.getItemBurnTime(furnaceItems[9]) / 25) * ironBlocksInFurnace));
		}
		if (redstoneBlocksInFurnace > 0 && ironBlocksInFurnace > 0)
		{

			return (((TileEntityFurnace.getItemBurnTime(furnaceItems[9]) + ((TileEntityFurnace.getItemBurnTime(furnaceItems[9]) / 25) * ironBlocksInFurnace)) - ((TileEntityFurnace.getItemBurnTime(furnaceItems[9]) / 25) * redstoneBlocksInFurnace)));
		}

		else
			return TileEntityFurnace.getItemBurnTime(furnaceItems[9]);
	}



}
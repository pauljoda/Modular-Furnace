package modularfurnace.tileentity;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modularfurnace.blocks.BlockFurnaceCore;
import modularfurnace.blocks.BlockManager;
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

public class TileEntityFurnaceCore extends TileEntity implements ISidedInventory
{
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {2, 1};
	private static final int[] slots_sides = new int[] {1};
	private static final int[] slots_output_only = new int [] {2};


	public int redstoneBlocksInFurnace = 0;
	public int redstoneMultiplier = 6;
	public int cookSpeed = 175;
	public int ironBlocksInFurnace = 0;
	public boolean emeralds;


	private ItemStack[] furnaceItems = new ItemStack[3];
	public int furnaceBurnTime;
	public int currentItemBurnTime;
	public int furnaceCookTime;
	private String field_94130_e;

	public boolean isValidMultiblock = false;
	public boolean diamonds = false;
	public boolean crafterEnabled = false;


	Random r = new Random();

	public TileEntityFurnaceCore()
	{
	}

	

	public void invalidateMultiblock()
	{
		int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);

		ironBlocksInFurnace = 0;
		redstoneBlocksInFurnace = 0;
		diamonds = false;
		crafterEnabled = false;
		emeralds = false;

		revertDummies();
		isValidMultiblock = false;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metadata, 2);

	}

	public boolean getIsValid()
	{
		return isValidMultiblock;
	}
	
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
							if(blockId != 0 && blockId != Reference.lavaCore)
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
						worldObj.setBlock(x, y, z, Reference.furnaceDummyIDRedstone);
						worldObj.markBlockForUpdate(x, y, z);
						TileEntityFurnaceDummy dummyTE = (TileEntityFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);
						dummyTE.setCore(this);
						
					}

					else if(worldObj.getBlockId(x, y, z) == Block.blockIron.blockID)
					{
						this.ironBlocksInFurnace = ironBlocksInFurnace + 1;
						worldObj.setBlock(x, y, z, Reference.furnaceDummyIDGlowStone);
						worldObj.markBlockForUpdate(x, y, z);
						TileEntityFurnaceDummy dummyTE = (TileEntityFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);
						dummyTE.setCore(this);
					}

					else if(worldObj.getBlockId(x, y, z) == Block.blockDiamond.blockID)
					{
						diamonds = true;
						worldObj.setBlock(x, y, z, Reference.furnaceDummyIDDiamond);
						worldObj.markBlockForUpdate(x, y, z);
						TileEntityFurnaceDummy dummyTE = (TileEntityFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);
						dummyTE.setCore(this);
					}

					else if(worldObj.getBlockId(x, y, z) == Block.blockEmerald.blockID)
					{
						emeralds = true;
						worldObj.setBlock(x, y, z, Reference.furnaceDummyIDEmerald);
						worldObj.markBlockForUpdate(x, y, z);
						TileEntityFurnaceDummy dummyTE = (TileEntityFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);
						dummyTE.setCore(this);
					}

					else if(worldObj.getBlockId(x, y, z) == Reference.crafterInactive)
					{
						crafterEnabled = true;
						worldObj.setBlock(x, y, z, Reference.crafterActive);
						worldObj.markBlockForUpdate(x, y, z);
						TileEntityFurnaceDummy dummyTE = (TileEntityFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);
						dummyTE.setCore(this);
					}

					else if(worldObj.getBlockId(x, y, z) == Reference.furnaceDummyIOID)
					{
						worldObj.setBlock(x, y, z, Reference.furnaceDummyActiveIOID);
						worldObj.markBlockForUpdate(x, y, z);
						TileEntityFurnaceDummy dummyTE = (TileEntityFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);
						dummyTE.setCore(this);
					}
					else
					{
						int icon = worldObj.getBlockId(x, y, z);
						worldObj.setBlock(x, y, z, Reference.furnaceDummyID);
						worldObj.markBlockForUpdate(x, y, z);
						TileEntityFurnaceDummy dummyTE = (TileEntityFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);
						
						if(icon == BlockManager.furnaceDummy.blockID)
							icon = dummyTE.icon;
							
						dummyTE.icon = icon;
						dummyTE.setCore(this);
					}

				}
			}

			isValidMultiblock = true;
		}
	}


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

					if(blockId == BlockManager.furnaceDummy.blockID)
					{
						TileEntityFurnaceDummy dummyTE = (TileEntityFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);
						worldObj.setBlock(x, y, z, dummyTE.getBlock().blockID);
						worldObj.markBlockForUpdate(x, y, z);
						continue;
					}

					if(blockId == BlockManager.crafterActive.blockID)
					{
						worldObj.setBlock(x, y, z, Reference.crafterInactive);
						worldObj.markBlockForUpdate(x, y, z);
						continue;
					}

					if(blockId == BlockManager.furnaceDummyRedstone.blockID)
					{
						worldObj.setBlock(x, y, z, Block.blockRedstone.blockID);
						worldObj.markBlockForUpdate(x, y, z);
						continue;

					}
					if(blockId == BlockManager.furnaceDummyGlowStone.blockID)
					{
						worldObj.setBlock(x, y, z, Block.blockIron.blockID);
						worldObj.markBlockForUpdate(x, y, z);
						continue;

					}
					if(blockId == BlockManager.furnaceDummyDiamond.blockID)
					{
						worldObj.setBlock(x, y, z, Block.blockDiamond.blockID);
						worldObj.markBlockForUpdate(x, y, z);
						continue;
					}
					if(blockId == BlockManager.furnaceDummyEmerald.blockID)
					{
						worldObj.setBlock(x, y, z, Block.blockEmerald.blockID);
						worldObj.markBlockForUpdate(x, y, z);
						continue;
					}
					if(blockId == BlockManager.furnaceDummyActiveIO.blockID)
					{
						worldObj.setBlock(x, y, z, Reference.furnaceDummyIOID);
						worldObj.markBlockForUpdate(x, y, z);
						continue;
					}

					worldObj.markBlockForUpdate(x, y, z);
				}
			}

		}

		isValidMultiblock = false;
	}

	public boolean checkIfCrafting()
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
								return false;
							else
								continue;
						}
					}
					if(blockId == Reference.crafterActive || blockId == Reference.crafterInactive)
					{
						return true;
					}

				}
			}
		}
		return false;
	}



	@Override
	public void updateEntity()
	{
		if(!isValidMultiblock)
			return;


		boolean flag1 = false;
		boolean flag = furnaceBurnTime > 0;
		int i = 1;
		i = i++;



		if(furnaceBurnTime > 0)
			--furnaceBurnTime;

		if(!this.worldObj.isRemote)
		{
			if(furnaceBurnTime == 0 && canSmelt())
			{

				this.currentItemBurnTime = this.furnaceBurnTime = this.scaledBurnTime();

				if(furnaceBurnTime > 0)
				{
					flag1 = true;

					if(this.furnaceItems[1] != null)
					{
						--this.furnaceItems[1].stackSize;

						if(this.furnaceItems[1].stackSize == 0)
						{
							this.furnaceItems[1] = this.furnaceItems[1].getItem().getContainerItemStack(furnaceItems[1]); 
						}
					}
				}     
			}

			if(isBurning() && canSmelt())
			{
				furnaceCookTime++;

				if(furnaceCookTime  == this.getSpeedMultiplier())
				{
					furnaceCookTime = 0;
					smeltItem();
					flag1 = true;
				}
			}
			else
			{
				furnaceCookTime = 0;
			}

			if(flag != furnaceBurnTime > 0)
			{
				flag1 = true;
				BlockFurnaceCore.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}

		if(flag1)
			onInventoryChanged();
	}

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

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		if(par1 == 2)
			return false;
		if(par1 == 1 && isItemFuel(par2ItemStack))
			return true;
		if(par1 == 0)
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
		this.crafterEnabled = tagCompound.getBoolean("Enabled");
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
		tagCompound.setBoolean("Enabled", (boolean)this.crafterEnabled);
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
		if(furnaceItems[0] == null)
			return false;
		else
		{
			ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(furnaceItems[0]);
			if(itemStack == null)
				return false;
			if(furnaceItems[2] == null)
				return true;
			if(!furnaceItems[2].isItemEqual(itemStack))
				return false;

			int resultingStackSize = furnaceItems[2].stackSize + itemStack.stackSize;
			return (resultingStackSize <= getInventoryStackLimit() && resultingStackSize <= itemStack.getMaxStackSize());
		}
	}

	public void smeltItem()
	{
		if (this.canSmelt())
		{
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItems[0]);

			if (this.furnaceItems[2] == null)
			{
				this.furnaceItems[2] = itemstack.copy();
			}
			else if (this.furnaceItems[2].isItemEqual(itemstack))
			{
				furnaceItems[2].stackSize += itemstack.stackSize;
			}

			--this.furnaceItems[0].stackSize;

			if (this.furnaceItems[0].stackSize <= 0)
			{
				this.furnaceItems[0] = null;
			}
		}
	}
	public int getSpeedMultiplier()
	{
		int red = countRedstone();
		return  (cookSpeed - (red * redstoneMultiplier));
	}

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
					 if(blockId == Reference.furnaceDummyIDRedstone || blockId == Block.blockRedstone.blockID)
					 {
						 output++;
					 }

				 }
			 }
		 }	

		 return output;
	}

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
					if(blockId == Reference.furnaceDummyIDGlowStone || blockId == Block.blockIron.blockID)
					{
						output++;
					}

				}
			}
		}	

		return output;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int par1)
	{
		switch(par1)
		{
		case 0 : return slots_bottom;
		case 1 : return slots_top;
		case 2 : return slots_output_only;
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

	public int scaledBurnTime()
	{

		if (redstoneBlocksInFurnace > 0 && ironBlocksInFurnace == 0)
		{
			return ((TileEntityFurnace.getItemBurnTime(furnaceItems[1])) - ((TileEntityFurnace.getItemBurnTime(furnaceItems[1]) / 25) * (redstoneBlocksInFurnace - 1))); 
		}

		if (redstoneBlocksInFurnace == 0 && ironBlocksInFurnace >0)
		{
			return (TileEntityFurnace.getItemBurnTime(furnaceItems[1]) + ((TileEntityFurnace.getItemBurnTime(furnaceItems[1]) / 25) * ironBlocksInFurnace));
		}
		if (redstoneBlocksInFurnace > 0 && ironBlocksInFurnace > 0)
		{

			return (((TileEntityFurnace.getItemBurnTime(furnaceItems[1]) + ((TileEntityFurnace.getItemBurnTime(furnaceItems[1]) / 25) * ironBlocksInFurnace)) - ((TileEntityFurnace.getItemBurnTime(furnaceItems[1]) / 25) * redstoneBlocksInFurnace)));
		}

		else
			return TileEntityFurnace.getItemBurnTime(furnaceItems[1]);
	}

}
package modularfurnace.tileentity;

import java.util.Random;

import modularfurnace.blocks.BlockFurnaceCoreSmeltery;
import modularfurnace.blocks.BlockManager;
import modularfurnace.lib.ModularFurnacesSmelteryRecipies;
import modularfurnace.lib.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityFurnaceCoreSmeltery extends TileEntity implements ISidedInventory
{
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {2, 1, 3};
	private static final int[] slots_sides = new int[] {1};
	private static final int[] slots_output_only = new int [] {2, 3};

	private ItemStack[] furnaceItems = new ItemStack[5];
	public int furnaceBurnTime;
	public int currentItemBurnTime;
	public int furnaceCookTime;
	private String field_94130_e;

	private boolean isValidMultiblock = false;


	Random r = new Random();

	public TileEntityFurnaceCoreSmeltery()
	{
	}

	public boolean getIsValid()
	{
		return isValidMultiblock;
	}

	public void invalidateMultiblock()
	{
		int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metadata, 2);

		revertDummies();
		isValidMultiblock = false;
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
							if(blockId != Reference.lavaCore)
							{
								return false;
							}
							else                    		
								continue;

						}
					}

					if(blockId != BlockManager.furnaceSmelteryBrick.blockID)
						return false;
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
					if(worldObj.getBlockId(x, y, z) == BlockManager.furnaceSmelteryBrick.blockID)
					{
						worldObj.setBlock(x, y, z, Reference.furnaceDummySmelteryID);
						worldObj.markBlockForUpdate(x, y, z);
						TileEntityFurnaceDummySmeltery dummy = (TileEntityFurnaceDummySmeltery)worldObj.getBlockTileEntity(x, y, z);
						dummy.setCore(this);

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

					if(horiz == 0 && vert == 0 && (depth == 0 || depth == 1))
						continue;

					if(blockId == BlockManager.furnaceDummySmeltery.blockID)
					{
						worldObj.setBlock(x, y, z, BlockManager.furnaceSmelteryBrick.blockID);
						worldObj.markBlockForUpdate(x, y, z);
						continue;
					}

					worldObj.markBlockForUpdate(x, y, z);
				}
			}
		}

		isValidMultiblock = false;
	}


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
				this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItems[1]);

				if (this.furnaceBurnTime > 0)
				{
					flag1 = true;

					if (this.furnaceItems[1] != null)
					{
						--this.furnaceItems[1].stackSize;

						if (this.furnaceItems[1].stackSize == 0)
						{
							this.furnaceItems[1] = this.furnaceItems[1].getItem().getContainerItemStack(furnaceItems[1]);
						}
					}
				}
			}

			if (this.isBurning() && this.canSmelt())
			{
				++this.furnaceCookTime;

				if (this.furnaceCookTime == 200)
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
				BlockFurnaceCoreSmeltery.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}

		if (flag1)
		{
			this.onInventoryChanged();
		}
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
		return this.isInvNameLocalized() ? this.field_94130_e : "multifurnace.container.multifurnacecoresmeltery";
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

			if (i == Item.flint.itemID) return 800;
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
		this.currentItemBurnTime = getItemBurnTime(this.furnaceItems[1]);

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
		System.out.println("Is valid? " + (isValidMultiblock ? "Yes" : "No"));

		tagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
		tagCompound.setShort("CookTime", (short)this.furnaceCookTime);

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

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1)
	{
		return this.furnaceCookTime * par1 / 200;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1)
	{
		if (this.currentItemBurnTime == 0)
		{
			this.currentItemBurnTime = 200;
		}

		return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
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
			int resultingStackSize = 0;
			int resultingStackSize1 = 0;
			ItemStack itemStack = ModularFurnacesSmelteryRecipies.smelting().getSmeltingResult(furnaceItems[0]);
			if(itemStack == null)
				return false;
			if(furnaceItems[2] == null && furnaceItems[3] == null)
				return true;
			if(furnaceItems[2] != null)
			{
				resultingStackSize = furnaceItems[2].stackSize + itemStack.stackSize;
				if(furnaceItems[3] != null)
				{
					resultingStackSize1 = furnaceItems[3].stackSize + itemStack.stackSize;
					if(!furnaceItems[2].isItemEqual(itemStack) && !furnaceItems[3].isItemEqual(itemStack))
						return false;
				}
			}
			
			return ( (resultingStackSize <= getInventoryStackLimit() &&  resultingStackSize1 <= getInventoryStackLimit()) && resultingStackSize <= itemStack.getMaxStackSize());

		}
	}

	public void smeltItem()
	{
		if (this.canSmelt())
		{
			ItemStack itemstack = ModularFurnacesSmelteryRecipies.smelting().getSmeltingResult(this.furnaceItems[0]);
			ItemStack itemstack2 = ModularFurnacesSmelteryRecipies.smelting().getSmeltingResult2(this.furnaceItems[0]);

			if (this.furnaceItems[2] == null)
			{
				this.furnaceItems[2] = itemstack.copy();
			}
			else if (this.furnaceItems[2].isItemEqual(itemstack))
			{
				furnaceItems[2].stackSize += itemstack.stackSize;

			}
			if (this.furnaceItems[3] == null)
			{
				this.furnaceItems[3] = itemstack2.copy();
			}
			else if (this.furnaceItems[3].isItemEqual(itemstack2))
			{
				furnaceItems[3].stackSize += itemstack2.stackSize;

			}

			--this.furnaceItems[0].stackSize;

			if (this.furnaceItems[0].stackSize <= 0)
			{
				this.furnaceItems[0] = null;
			}
		}
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

}

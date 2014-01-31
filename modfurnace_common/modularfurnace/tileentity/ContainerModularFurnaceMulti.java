package modularfurnace.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerModularFurnaceMulti extends Container
{
	private TileEntityFurnaceMultiCore tileEntity;
	private int lastCookTime;
	private int lastBurnTime;
	private int lastItemBurnTime;

	public ContainerModularFurnaceMulti(InventoryPlayer playerInventory, TileEntityFurnaceMultiCore tileEntity)
	{
		this.tileEntity = tileEntity;

		// Input
		addSlotToContainer(new Slot(tileEntity, 0, 8, 13));
		addSlotToContainer(new Slot(tileEntity, 1, 26, 13));
		addSlotToContainer(new Slot(tileEntity, 2, 44, 13));
		addSlotToContainer(new Slot(tileEntity, 3, 8, 31));
		addSlotToContainer(new Slot(tileEntity, 4, 26, 31));
		addSlotToContainer(new Slot(tileEntity, 5, 44, 31));
		addSlotToContainer(new Slot(tileEntity, 6, 8, 49));
		addSlotToContainer(new Slot(tileEntity, 7, 26, 49));
		addSlotToContainer(new Slot(tileEntity, 8, 44, 49));

		// Fuel
		addSlotToContainer(new Slot(tileEntity, 9, 80, 64));

		// Output
		addSlotToContainer(new SlotFurnace(playerInventory.player, tileEntity, 10, 116, 13));
		addSlotToContainer(new SlotFurnace(playerInventory.player, tileEntity, 11, 134, 13));
		addSlotToContainer(new SlotFurnace(playerInventory.player, tileEntity, 12, 152, 13));
		addSlotToContainer(new SlotFurnace(playerInventory.player, tileEntity, 13, 116, 31));
		addSlotToContainer(new SlotFurnace(playerInventory.player, tileEntity, 14, 134, 31));
		addSlotToContainer(new SlotFurnace(playerInventory.player, tileEntity, 15, 152, 31));
		addSlotToContainer(new SlotFurnace(playerInventory.player, tileEntity, 16, 116, 49));
		addSlotToContainer(new SlotFurnace(playerInventory.player, tileEntity, 17, 134, 49));
		addSlotToContainer(new SlotFurnace(playerInventory.player, tileEntity, 18, 152, 49));


		bindPlayerInventory(playerInventory);
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
	{
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, this.tileEntity.furnaceCookTime);
		par1ICrafting.sendProgressBarUpdate(this, 1, this.tileEntity.furnaceBurnTime);
		par1ICrafting.sendProgressBarUpdate(this, 2, this.tileEntity.currentItemBurnTime);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting icrafting = (ICrafting)this.crafters.get(i);

			if (this.lastCookTime != this.tileEntity.furnaceCookTime)
			{

				icrafting.sendProgressBarUpdate(this, 0, this.tileEntity.furnaceCookTime);
			}

			if (this.lastBurnTime != this.tileEntity.furnaceBurnTime)
			{
				icrafting.sendProgressBarUpdate(this, 1, this.tileEntity.furnaceBurnTime);
			}

			if (this.lastItemBurnTime != this.tileEntity.currentItemBurnTime)
			{
				icrafting.sendProgressBarUpdate(this, 2, this.tileEntity.currentItemBurnTime);
			}
		}

		this.lastCookTime = this.tileEntity.furnaceCookTime;
		this.lastBurnTime = this.tileEntity.furnaceBurnTime;
		this.lastItemBurnTime = this.tileEntity.currentItemBurnTime;
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
		{
			this.tileEntity.furnaceCookTime = par2;
		}

		if (par1 == 1)
		{
			this.tileEntity.furnaceBurnTime = par2;
		}

		if (par1 == 2)
		{
			this.tileEntity.currentItemBurnTime = par2;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer)
	{
		return tileEntity.isUseableByPlayer(entityPlayer);
	}

	private void bindPlayerInventory(InventoryPlayer playerInventory)
	{
		// Inventory
		for(int y = 0; y < 3; y++)
			for(int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));

		// Action Bar
		for(int x = 0; x < 9; x++)
			addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 142));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 >= 10 && par2 <= 18)
			{
				if (!this.mergeItemStack(itemstack1, 19, 54, true))
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (par2 > 9)
			{
				if (FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null)
				{
					if (!this.mergeItemStack(itemstack1, 0, 9, false))
					{
						return null;
					}
				}
				else if (TileEntityFurnace.isItemFuel(itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, 9, 10, false))
					{
						return null;
					}
				}
				else if (par2 >= 18 && par2 < 45)
				{
					if (!this.mergeItemStack(itemstack1, 45, 54, false))
					{
						return null;
					}
				}
				else if (par2 >= 45 && par2 < 54 && !this.mergeItemStack(itemstack1, 19, 45, false))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 19, 54, false))
			{
				return null;
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

}
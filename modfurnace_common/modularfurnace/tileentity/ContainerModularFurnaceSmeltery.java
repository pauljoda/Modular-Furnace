package modularfurnace.tileentity;

import modularfurnace.lib.ModularFurnacesSmelteryRecipies;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerModularFurnaceSmeltery extends Container
{
    private TileEntityFurnaceCoreSmeltery tileEntity;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;
    
    public ContainerModularFurnaceSmeltery(InventoryPlayer playerInventory, TileEntityFurnaceCoreSmeltery tileEntity)
    {
        this.tileEntity = tileEntity;
        
        // Input
        addSlotToContainer(new Slot(tileEntity, 0, 80, 24));
        //addSlotToContainer(new Slot(tileEntity, 4, 153, 6));
        
        // Fuel
        addSlotToContainer(new Slot(tileEntity, 1, 80, 60));
        
        // Output
        
        addSlotToContainer(new SlotFurnace(playerInventory.player, tileEntity, 2, 142, 42));
        addSlotToContainer(new SlotFurnace(playerInventory.player, tileEntity, 3, 17, 42));

         
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

            if (par2 == 2 || par2 == 3)
            {
                if (!this.mergeItemStack(itemstack1, 5, 40, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (par2 != 1 && par2 != 0)
            {
                if (ModularFurnacesSmelteryRecipies.smelting().getSmeltingResult(itemstack1) != null)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return null;
                    }
                }
        
                else if (TileEntityFurnaceCoreSmeltery.isItemFuel(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 4 && par2 < 31)
                {
                    if (!this.mergeItemStack(itemstack1, 31, 40, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 31 && par2 < 40 && !this.mergeItemStack(itemstack1, 4, 31, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 4, 40, false))
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
package modularfurnace.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;

public class ContainerModularFurnaceCrafter extends Container
{
    private TileEntityFurnaceCore tileEntity;
    private int lastCookTime = 0;
    private int lastBurnTime = 0;
    private int lastItemBurnTime = 0;
    
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();
    private World worldObj;
    
    public ContainerModularFurnaceCrafter(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5, TileEntityFurnaceCore tileEntity)
    {
        
        this.worldObj = par2World;
        this.addSlotToContainer(new SlotCrafting(par1InventoryPlayer.player, this.craftMatrix, this.craftResult, 19, 9, 45));
        int l;
        int i1;
        this.tileEntity = tileEntity;
        
        // Input
        addSlotToContainer(new Slot(tileEntity, 0, 88, 29));
        
        // Fuel
        addSlotToContainer(new Slot(tileEntity, 1, 88, 65));
        
        // Output
        addSlotToContainer(new SlotModularFurnace(par1InventoryPlayer.player, tileEntity, 2, 148, 47));
        
        bindPlayerInventory(par1InventoryPlayer);
        
        for (l = 0; l < 3; ++l)
        {
            for (i1 = 0; i1 < 3; ++i1)
            {
                this.addSlotToContainer(new Slot(this.craftMatrix, i1 + l * 3, 32 + i1 * 18, 9 + l * 18));
            }
        }

        for (l = 0; l < 3; ++l)
        {
            for (i1 = 0; i1 < 9; ++i1)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
            }
        }

        for (l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, l, 8 + l * 18, 142));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
    
    }
    
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
    }
    
    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);

        if (!this.worldObj.isRemote)
        {
            for (int i = 0; i < 9; ++i)
            {
                ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

                if (itemstack != null)
                {
                    par1EntityPlayer.dropPlayerItem(itemstack);
                }
            }
        }
    }

    
    public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot)
    {
        return par2Slot.inventory != this.craftResult && super.func_94530_a(par1ItemStack, par2Slot);
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
                icrafting.sendProgressBarUpdate(this, 0, this.tileEntity.furnaceCookTime);

            if (this.lastBurnTime != this.tileEntity.furnaceBurnTime)
                icrafting.sendProgressBarUpdate(this, 1, this.tileEntity.furnaceBurnTime);

            if (this.lastItemBurnTime != this.tileEntity.currentItemBurnTime)
                icrafting.sendProgressBarUpdate(this, 2, this.tileEntity.currentItemBurnTime);
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
    public ItemStack transferStackInSlot(EntityPlayer player, int slot)
    {
        ItemStack stack = null;
        Slot slotObject = (Slot)inventorySlots.get(slot);
        
        if(slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();
            
            // Merges the item into the player inventory
            if(slot < 3)
            {
                if(!this.mergeItemStack(stackInSlot, 3, 39, true))
                    return null;
            }
            else if(!this.mergeItemStack(stackInSlot, 0, 3, false))
                return null;
            
            if(stackInSlot.stackSize == 0)
                slotObject.putStack(null);
            else
                slotObject.onSlotChanged();
            
            if(stackInSlot.stackSize == stack.stackSize)
                return null;
            
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        
        if (slotObject != null && slotObject.getHasStack())
        {
            ItemStack itemstack1 = slotObject.getStack();
            stack = itemstack1.copy();

            if (slot == 0)
            {
                if (!this.mergeItemStack(itemstack1, 10, 46, true))
                {
                    return null;
                }

                slotObject.onSlotChange(itemstack1, stack);
            }
            else if (slot >= 10 && slot < 37)
            {
                if (!this.mergeItemStack(itemstack1, 37, 46, false))
                {
                    return null;
                }
            }
            else if (slot >= 37 && slot < 46)
            {
                if (!this.mergeItemStack(itemstack1, 10, 37, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 10, 46, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slotObject.putStack((ItemStack)null);
            }
            else
            {
                slotObject.onSlotChanged();
            }

            if (itemstack1.stackSize == stack.stackSize)
            {
                return null;
            }

            slotObject.onPickupFromSlot(player, itemstack1);
        }
        
        return stack;
    }
}

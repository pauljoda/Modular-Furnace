package modularfurnace.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityFurnaceDummy extends TileEntity implements ISidedInventory
{
    TileEntityFurnaceCore tileEntityCore;
    int coreX;
    int coreY;
    int coreZ;
    
    public TileEntityFurnaceDummy()
    {
    }
    
    public void setCore(TileEntityFurnaceCore core)
    {
        coreX = core.xCoord;
        coreY = core.yCoord;
        coreZ = core.zCoord;
        tileEntityCore = core;
    }
    
    public TileEntityFurnaceCore getCore()
    {
        if(tileEntityCore == null)
            tileEntityCore = (TileEntityFurnaceCore)worldObj.getBlockTileEntity(coreX, coreY, coreZ);
        
        return tileEntityCore;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        
        coreX = tagCompound.getInteger("CoreX");
        coreY = tagCompound.getInteger("CoreY");
        coreZ = tagCompound.getInteger("CoreZ");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        
        tagCompound.setInteger("CoreX", coreX);
        tagCompound.setInteger("CoreY", coreY);
        tagCompound.setInteger("CoreZ", coreZ);
    }
    
    @Override
    public int getSizeInventory() {
        // TODO Auto-generated method stub
        return tileEntityCore.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        // TODO Auto-generated method stub
        return tileEntityCore.getStackInSlot(i);
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        // TODO Auto-generated method stub
        return tileEntityCore.decrStackSize(i, j);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        // TODO Auto-generated method stub
        return tileEntityCore.getStackInSlotOnClosing(i);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        // TODO Auto-generated method stub
        tileEntityCore.setInventorySlotContents(i, itemstack);
    }

    @Override
    public String getInvName() {
        // TODO Auto-generated method stub
        return tileEntityCore.getInvName();
    }

    @Override
    public boolean isInvNameLocalized() {
        // TODO Auto-generated method stub
        return tileEntityCore.isInvNameLocalized();
    }

    @Override
    public int getInventoryStackLimit() {
        // TODO Auto-generated method stub
        return tileEntityCore.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        // TODO Auto-generated method stub
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : entityplayer.getDistanceSq((double)xCoord + 0.5, (double)yCoord + 0.5, (double)zCoord + 0.5) <= 64.0;
    }

    @Override
    public void openChest() { }

    @Override
    public void closeChest() { }

    @Override



    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return null;
    }
    
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return tileEntityCore.isItemValidForSlot(i, itemstack);
	}

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return tileEntityCore.canInsertItem(par1, par2ItemStack, par3);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return tileEntityCore.canExtractItem(par1, par2ItemStack, par3);
    }


}

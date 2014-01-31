package modularfurnace.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMultiFurnaceDummy extends TileEntity implements ISidedInventory
{
	public int slot = 4;
	TileEntityFurnaceMultiCore tileEntityCore;
	int coreX;
	int coreY;
	int coreZ;
	public int icon = 0;
	int metadata = 0;



	public TileEntityMultiFurnaceDummy()
	{
	}

	public void setCore(TileEntityFurnaceMultiCore core)
	{
		coreX = core.xCoord;
		coreY = core.yCoord;
		coreZ = core.zCoord;
		tileEntityCore = core;
	}

	public TileEntityFurnaceMultiCore getCore()
	{
		if(tileEntityCore == null)
			tileEntityCore = (TileEntityFurnaceMultiCore)worldObj.getBlockTileEntity(coreX, coreY, coreZ);

		return tileEntityCore;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);

		coreX = tagCompound.getInteger("CoreX");
		coreY = tagCompound.getInteger("CoreY");
		coreZ = tagCompound.getInteger("CoreZ");

		slot = tagCompound.getInteger("Slot");
		icon = tagCompound.getInteger("Icon");
		metadata = tagCompound.getInteger("Meta");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);

		tagCompound.setInteger("CoreX", coreX);
		tagCompound.setInteger("CoreY", coreY);
		tagCompound.setInteger("CoreZ", coreZ);

		tagCompound.setInteger("Slot", slot);
		tagCompound.setInteger("Icon", icon);
		tagCompound.setInteger("Meta", metadata);
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

	public Block getBlock()
	{

		if(Block.blocksList[this.icon] == null)
			return Block.cobblestone;
		
		return Block.blocksList[this.icon];
	}
	
	public int getMeta()
	{
		return metadata;
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
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return tileEntityCore.isItemValidForSlot(i, itemstack);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {

		tileEntityCore = this.getCore();
		return tileEntityCore.getAccessibleSlotsFromSide(var1);

	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {

		tileEntityCore = this.getCore();
		return tileEntityCore.isItemValidForSlot(i, itemstack);

	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		// TODO Auto-generated method stub
		tileEntityCore = this.getCore();
		if(slot <= 3)
		{
			j = slot;
			return tileEntityCore.canExtractItem(i, itemstack, j);
		}
		else
		{
			return tileEntityCore.canExtractItem(i, itemstack, j);
		}
	}

}
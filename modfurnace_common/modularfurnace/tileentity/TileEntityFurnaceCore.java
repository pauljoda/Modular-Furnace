package modularfurnace.tileentity;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modularfurnace.ModularFurnace;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityFurnaceCore extends TileEntity implements ISidedInventory
{
    private static final int[] slots_top = new int[] {0};
    private static final int[] slots_bottom = new int[] {2, 1};
    private static final int[] slots_sides = new int[] {1};

	
    public int redstoneBlocksInFurnace = 0;
    public int redstoneMultiplier = 6;
    public int cookSpeed = 151;
    public int ironBlocksInFurnace = 0;
    public static boolean emeralds;
  
    private ItemStack[] furnaceItems = new ItemStack[50];
    public int furnaceBurnTime = 0;
    public int currentItemBurnTime = 0;
    public int furnaceCookTime = 0;
 
    private boolean isValidMultiblock = false;
    public boolean diamonds = false;
    public boolean crafterEnabled = false;
    
    Random r = new Random();
    
    public TileEntityFurnaceCore()
    {
    }
    
    public boolean getIsValid()
    {
        return isValidMultiblock;
    }
    
    public void invalidateMultiblock()
    {
        isValidMultiblock = false;
        
        int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        metadata = metadata & BlockFurnaceCore.MASK_DIR;
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metadata, 2);
        
        furnaceBurnTime = 0;
        currentItemBurnTime = 0;
        furnaceCookTime = 0;
        redstoneBlocksInFurnace = 0;
        ironBlocksInFurnace = 0;
        diamonds = false;
        crafterEnabled = false;
        emeralds = false;
        
        
        
        revertDummies();
        isValidMultiblock = false;
    }
    
    public boolean checkIfProperlyFormed()
    {
        int dir = (getBlockMetadata() & BlockFurnaceCore.MASK_DIR);
        
        int depthMultiplier = ((dir == BlockFurnaceCore.META_DIR_NORTH || dir == BlockFurnaceCore.META_DIR_WEST) ? 1 : -1);
        boolean forwardZ = ((dir == BlockFurnaceCore.META_DIR_NORTH) || (dir == BlockFurnaceCore.META_DIR_SOUTH));
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
                 
                    if(blockId != Block.cobblestone.blockID && blockId != Block.blockRedstone.blockID && blockId != Block.blockIron.blockID && blockId != Block.blockDiamond.blockID && blockId != ModularFurnace.crafterInactiveID && blockId != Block.blockEmerald.blockID)
                        return false;
                }
               
            }
            
        }
        
        return true;
    }
    
    public void convertDummies()
    {
        int dir = (getBlockMetadata() & BlockFurnaceCore.MASK_DIR);
        
        int depthMultiplier = ((dir == BlockFurnaceCore.META_DIR_NORTH || dir == BlockFurnaceCore.META_DIR_WEST) ? 1 : -1);
        boolean forwardZ = ((dir == BlockFurnaceCore.META_DIR_NORTH) || (dir == BlockFurnaceCore.META_DIR_SOUTH));

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
                    
                    if(horiz == 0 && vert == 0 && (depth == 0 || depth == 1))
                        continue;
                    if(worldObj.getBlockId(x, y, z) == Block.blockRedstone.blockID)
                    {
                        this.redstoneBlocksInFurnace = redstoneBlocksInFurnace + 1;
                        worldObj.setBlock(x, y, z, Reference.furnaceDummyIDRedstone);
                        worldObj.markBlockForUpdate(x, y, z);
                        TileEntityFurnaceDummy dummyTE = (TileEntityFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);
                        dummyTE.setCore(this);
                    }
                    
                    if(worldObj.getBlockId(x, y, z) == Block.blockIron.blockID)
                    {
                        this.ironBlocksInFurnace = ironBlocksInFurnace + 1;
                        worldObj.setBlock(x, y, z, Reference.furnaceDummyIDGlowStone);
                        worldObj.markBlockForUpdate(x, y, z);
                        TileEntityFurnaceDummy dummyTE = (TileEntityFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);
                        dummyTE.setCore(this);
                    }
                    
                    if(worldObj.getBlockId(x, y, z) == Block.blockDiamond.blockID)
                    {
                        diamonds = true;
                        worldObj.setBlock(x, y, z, Reference.furnaceDummyIDDiamond);
                        worldObj.markBlockForUpdate(x, y, z);
                        TileEntityFurnaceDummy dummyTE = (TileEntityFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);
                        dummyTE.setCore(this);
                    }
                    
                    if(worldObj.getBlockId(x, y, z) == Block.blockEmerald.blockID)
                    {
                        emeralds = true;
                        worldObj.setBlock(x, y, z, Reference.furnaceDummyIDEmerald);
                        worldObj.markBlockForUpdate(x, y, z);
                        TileEntityFurnaceDummy dummyTE = (TileEntityFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);
                        dummyTE.setCore(this);
                    }
                    
                    if(worldObj.getBlockId(x, y, z) == Reference.crafterInactive)
                    {
                        crafterEnabled = true;
                        worldObj.setBlock(x, y, z, Reference.crafterActive);
                        worldObj.markBlockForUpdate(x, y, z);
                        TileEntityFurnaceDummy dummyTE = (TileEntityFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);
                        dummyTE.setCore(this);
                    }
                    
                    if(worldObj.getBlockId(x, y, z) == Block.cobblestone.blockID)
                    {
                    worldObj.setBlock(x, y, z, Reference.furnaceDummyID);
                    worldObj.markBlockForUpdate(x, y, z);
                    TileEntityFurnaceDummy dummyTE = (TileEntityFurnaceDummy)worldObj.getBlockTileEntity(x, y, z);
                    dummyTE.setCore(this);
                }
            }
        }
        
        isValidMultiblock = true;
    }
    }
    
    
    private void revertDummies()
    {
        int dir = (getBlockMetadata() & BlockFurnaceCore.MASK_DIR);
       
        int depthMultiplier = ((dir == BlockFurnaceCore.META_DIR_NORTH || dir == BlockFurnaceCore.META_DIR_WEST) ? 1 : -1);
        boolean forwardZ = ((dir == BlockFurnaceCore.META_DIR_NORTH) || (dir == BlockFurnaceCore.META_DIR_SOUTH));
        
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
                    
                    if(blockId == BlockManager.furnaceDummy.blockID)
                    {
                         worldObj.setBlock(x, y, z, Block.cobblestone.blockID);
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
                    worldObj.markBlockForUpdate(x, y, z);
                }
            }
        }
        
        isValidMultiblock = false;
    }
    
    public boolean checkIfCrafting()
    {
        int dir = (getBlockMetadata() & BlockFurnaceCore.MASK_DIR);
        
        int depthMultiplier = ((dir == BlockFurnaceCore.META_DIR_NORTH || dir == BlockFurnaceCore.META_DIR_WEST) ? 1 : -1);
        boolean forwardZ = ((dir == BlockFurnaceCore.META_DIR_NORTH) || (dir == BlockFurnaceCore.META_DIR_SOUTH));
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
        int metadata = getBlockMetadata();
        int isActive = (metadata >> 3);
        
        
        if(furnaceBurnTime > 0)
            furnaceBurnTime--;
        
        if(!this.worldObj.isRemote)
        {
            if(furnaceBurnTime == 0 && canSmelt())
            {
                currentItemBurnTime = furnaceBurnTime = this.scaledBurnTime();
                if(furnaceBurnTime > 0)
                {
                    flag1 = true;
                    
                    if(furnaceItems[1] != null)
                    {
                        furnaceItems[1].stackSize--;
                        
                        if(furnaceItems[1].stackSize == 0)
                            furnaceItems[1] = furnaceItems[1].getItem().getContainerItemStack(furnaceItems[1]);
                    }
                }
            }
            
            if(isBurning() && canSmelt())
            {
                furnaceCookTime++;
                
                if(furnaceCookTime == getSpeedMultiplier())
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
            
            if(isActive == 0 && furnaceBurnTime > 0)
            {
                flag1 = true;
                metadata = getBlockMetadata();
                isActive = 1;
                metadata = (isActive << 3) | (metadata);
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metadata, 2);
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
    
    public void setGuiDisplayName(String par1Str)
    {
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
        return "multifurnace.container.multifurnacecore";
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
        return par1 == 2 ? false : (par1 == 1 ? isItemFuel(par2ItemStack) : true);
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

                if (block == Block.field_111034_cE)
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
        furnaceItems = new ItemStack[getSizeInventory()];
        
        for(int i = 0; i < itemsTag.tagCount(); i++)
        {
            NBTTagCompound slotTag = (NBTTagCompound)itemsTag.tagAt(i);
            byte slot = slotTag.getByte("Slot");
            
            if(slot >= 0 && slot < furnaceItems.length)
                furnaceItems[slot] = ItemStack.loadItemStackFromNBT(slotTag);
        }
        
        furnaceBurnTime = tagCompound.getShort("BurnTime");
        furnaceCookTime = tagCompound.getShort("CookTime");
        currentItemBurnTime = (((TileEntityFurnace.getItemBurnTime(furnaceItems[1]) / ((redstoneBlocksInFurnace) + 1)) * ((ironBlocksInFurnace / 2) + 1)));
        redstoneBlocksInFurnace = tagCompound.getShort("RedstoneBlocks"); 
        ironBlocksInFurnace = tagCompound.getShort("IronStoneBlocks");
        diamonds = tagCompound.getBoolean("Diamonds");
        crafterEnabled = tagCompound.getBoolean("Enabled");
        emeralds = tagCompound.getBoolean("Emeralds");
        
        
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        
        tagCompound.setBoolean("isValidMultiblock", isValidMultiblock);
        System.out.println("Is valid? " + (isValidMultiblock ? "Yes" : "No"));
        
        tagCompound.setShort("BurnTime", (short)furnaceBurnTime);
        tagCompound.setShort("CookTime", (short)furnaceCookTime);
        tagCompound.setShort("RedstoneBlocks", (short)redstoneBlocksInFurnace);
        tagCompound.setShort("IronStoneBlocks", (short)ironBlocksInFurnace);
        tagCompound.setBoolean("Diamonds", (boolean)diamonds);
        tagCompound.setBoolean("Enabled", (boolean)crafterEnabled);
        tagCompound.setBoolean("Emeralds",(boolean)emeralds);
        
       
        NBTTagList itemsList = new NBTTagList();
        
        for(int i = 0; i < furnaceItems.length; i++)
        {
            if(furnaceItems[i] != null)
            {
                NBTTagCompound slotTag = new NBTTagCompound();
                slotTag.setByte("Slot", (byte)i);
                furnaceItems[i].writeToNBT(slotTag);
                itemsList.appendTag(slotTag);
            }
            
            tagCompound.setTag("Items", itemsList);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int scaleVal)
    {
        
        return (int) (furnaceCookTime * scaleVal / getSpeedMultiplier());
    }
    
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int scaleVal)
    {
        if(currentItemBurnTime == 0){
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
        
        int t = r.nextInt(4);
        
        if(canSmelt())
        {
            ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(furnaceItems[0]);
          
            if(diamonds && t == 0 && furnaceItems[0].stackSize > 1)
            {
                if(furnaceItems[2] == null)
                    furnaceItems[2] = itemStack.copy();
                else if(furnaceItems[2].isItemEqual(itemStack))
                    furnaceItems[2].stackSize += itemStack.stackSize;
                    furnaceItems[2].stackSize += itemStack.stackSize;
                
                furnaceItems[0].stackSize--;
                if(furnaceItems[0].stackSize <= 0)
                    furnaceItems[0] = null;
            
            }
      
            if(furnaceItems[2] == null)
                furnaceItems[2] = itemStack.copy();
            else if(furnaceItems[2].isItemEqual(itemStack))
                furnaceItems[2].stackSize += itemStack.stackSize;
            
            furnaceItems[0].stackSize--;
            if(furnaceItems[0].stackSize <= 0)
                furnaceItems[0] = null;
        }
    }
    public int getSpeedMultiplier()
    {
       return  (cookSpeed - (redstoneBlocksInFurnace * redstoneMultiplier));
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
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
    		return ((TileEntityFurnace.getItemBurnTime(furnaceItems[1])) / (redstoneBlocksInFurnace * redstoneBlocksInFurnace));
    	}
    	
    	if (redstoneBlocksInFurnace == 0 && ironBlocksInFurnace >0)
    	{
    		return (TileEntityFurnace.getItemBurnTime(furnaceItems[1]) + ((TileEntityFurnace.getItemBurnTime(furnaceItems[1]) / 25) * ironBlocksInFurnace));
    	}
    	if (redstoneBlocksInFurnace > 0 && ironBlocksInFurnace > 0)
    	{
    		return (((TileEntityFurnace.getItemBurnTime(furnaceItems[1]) + ((TileEntityFurnace.getItemBurnTime(furnaceItems[1]) / 25) * ironBlocksInFurnace)) / (redstoneBlocksInFurnace * redstoneBlocksInFurnace)));
    	}

    	else
    		return TileEntityFurnace.getItemBurnTime(furnaceItems[1]);
    }

    
}
package modularfurnace.blocks;

import java.util.Random;

import modularfurnace.lib.Reference;
import modularfurnace.tileentity.TileEntityFurnaceCore;
import modularfurnace.tileentity.TileEntityFurnaceDummy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFurnaceDummyIOActive extends BlockContainer



{
    public BlockFurnaceDummyIOActive(int blockId)
    {
        
        super(blockId, Material.rock);
        
        setUnlocalizedName("crafterActive");
        setStepSound(Block.soundWoodFootstep);
        setHardness(3.5f);
        
    }
    public int meta = 0;
    
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
  
        return Reference.furnaceDummyIOID;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityFurnaceDummy();
    }
    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("coresidesio");
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6)
    {
        TileEntityFurnaceDummy dummy = (TileEntityFurnaceDummy)world.getBlockTileEntity(x, y, z);
        
        if(dummy != null && dummy.getCore() != null)
            dummy.getCore().invalidateMultiblock();
        
        super.breakBlock(world, x, y, z, par5, par6);
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        TileEntityFurnaceDummy dummy = (TileEntityFurnaceDummy)world.getBlockTileEntity(x, y, z);
        if(player.isSneaking())
        {
        	if(dummy.slot == 4)
        		dummy.slot = 0;
        	
        	dummy.slot = dummy.slot + 1;
        	if(dummy.slot == 3)
        		dummy.slot = 0;
        	
        	this.displaySlot(world, x, y, z, player);
        	
            return false;
        }
        
   
        
        if(dummy != null && dummy.getCore() != null)
        {
            TileEntityFurnaceCore core = dummy.getCore();
            return core.getBlockType().onBlockActivated(world, core.xCoord, core.yCoord, core.zCoord, player, par6, par7, par8, par9);
        }
        
        return true;
    }
    
    private void displaySlot(World world, int x, int y, int z, EntityPlayer player)
    {
        TileEntityFurnaceDummy dummy = (TileEntityFurnaceDummy)world.getBlockTileEntity(x, y, z);

    	switch(dummy.slot)
    	{
    	case 0 :  player.addChatMessage("Slot is Fuel");
    			  break;
    			  
    	case 1 :  player.addChatMessage("Slot is Input");
    			  break;
    			  
    	default : player.addChatMessage("Slot is Output");
    			  break;
    	}
    }
}
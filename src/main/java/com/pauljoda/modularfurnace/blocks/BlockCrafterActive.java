package com.pauljoda.modularfurnace.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.pauljoda.modularfurnace.ModularFurnace;
import com.pauljoda.modularfurnace.client.ClientProxy;
import com.pauljoda.modularfurnace.lib.Reference;
import com.pauljoda.modularfurnace.tileentity.TileEntityFurnaceCore;
import com.pauljoda.modularfurnace.tileentity.TileEntityFurnaceDummy;

public class BlockCrafterActive extends BlockContainer
{
    public BlockCrafterActive()
    {
        super(Material.wood);
        
        setBlockName("crafterActive");
        setStepSound(Block.soundTypeWood);
        setHardness(3.5f);
        
    }
    public int meta = 0;
    
    @Override
    public TileEntity createNewTileEntity(World world, int i)
    {
        return new TileEntityFurnaceDummy();
    }
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("crafting_table_top");
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, Block par5, int par6)
    {
        TileEntityFurnaceDummy dummy = (TileEntityFurnaceDummy)world.getTileEntity(x, y, z);
        
        if(dummy != null && dummy.getCore() != null)
            dummy.getCore().invalidateMultiblock();
        
        super.breakBlock(world, x, y, z, par5, par6);
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if(player.isSneaking())
            return false;
        
        TileEntityFurnaceDummy dummy = (TileEntityFurnaceDummy)world.getTileEntity(x, y, z);
        
        if(dummy != null && dummy.getCore() != null)
        {
            TileEntityFurnaceCore core = dummy.getCore();
            return core.getBlockType().onBlockActivated(world, core.xCoord, core.yCoord, core.zCoord, player, par6, par7, par8, par9);
        }
        
        return true;
    }
    @Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	@Override
	public int getRenderType()
	{
		return ClientProxy.dummyRenderType;
	}

	@Override
	public boolean canRenderInPass(int pass)
	{
		//Set the static var in the client proxy
		ClientProxy.renderPass = pass;
		//the block can render in both passes, so return true always
		return true;
	}
	@Override
	public int getRenderBlockPass()
	{
		return 1;
	}    

}
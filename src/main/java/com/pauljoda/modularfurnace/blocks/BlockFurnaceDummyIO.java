package com.pauljoda.modularfurnace.blocks;

import com.pauljoda.modularfurnace.ModularFurnace;
import com.pauljoda.modularfurnace.client.ClientProxy;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockFurnaceDummyIO extends Block {

    public BlockFurnaceDummyIO(Material par2Material) {
        super(par2Material);
        
        setBlockName("ioBlock");
        setStepSound(Block.soundTypeStone);
        setHardness(3.5f);
        setCreativeTab(ModularFurnace.tabModularFurnace);
        
    }
    
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("dispenser_front_vertical");
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
package com.pauljoda.modularfurnace.blocks;

import java.util.Random;

import com.pauljoda.modularfurnace.ModularFurnace;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockTextureOverlay extends Block {

	public String texture;
	public boolean blastProof;
	public BlockTextureOverlay(String string, String name, boolean bool) {
		super(Material.glass);
		setBlockName(name);
		setCreativeTab(ModularFurnace.tabModularFurnace);
		this.setHardness(3.5F);
		this.setStepSound(soundTypeGlass);	
		this.setLightOpacity(0);
		texture = string;
		blastProof = bool;
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return Item.getItemFromBlock(this);
	}
	
	@Override
	 public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
    {
		if(blastProof)
        return 100000F;
		
		return super.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
    }
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
		
	@Override
	public int getRenderBlockPass()
    {
        return 1;
    }


	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon(texture);
	}

}
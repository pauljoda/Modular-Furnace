package modularfurnace.blocks;

import java.util.Random;

import modularfurnace.ModularFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockFurnaceSmelteryBrick extends Block {

	public BlockFurnaceSmelteryBrick(int par1) {
		super(par1, Material.rock);
		setUnlocalizedName("furnaceSmelteryBrick");
		setCreativeTab(ModularFurnace.tabModularFurnace);


	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{

		return this.blockID;
	}


	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon("smelterBrick");
	}

}
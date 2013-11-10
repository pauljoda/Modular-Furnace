package modularfurnace.blocks;

import java.util.Random;

import modularfurnace.ModularFurnace;
import modularfurnace.client.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockFurnaceSmelteryBrick extends Block {

	public BlockFurnaceSmelteryBrick(int par1) {
		super(par1, Material.rock);
		setUnlocalizedName("furnaceSmelteryBrick");
		setCreativeTab(ModularFurnace.tabModularFurnace);
		this.setHardness(3.0F);

	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{

		return this.blockID;
	}


	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon("stonebrick");
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
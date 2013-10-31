package modularfurnace.blocks;

import java.util.Random;

import modularfurnace.ModularFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class BlockTextureOverlay extends Block {

	public String texture;
	public boolean blastProof;
	public BlockTextureOverlay(int par1, String string, String name, boolean bool) {
		super(par1,Material.glass);
		setUnlocalizedName(name);
		setCreativeTab(ModularFurnace.tabModularFurnace);
		this.setHardness(3.5F);
		this.setStepSound(soundGlassFootstep);	
		this.setLightOpacity(0);
		texture = string;
		blastProof = bool;
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return this.blockID;
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
	public void registerIcons(IconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon(texture);
	}

}
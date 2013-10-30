package modularfurnace.blocks;

import java.util.Random;

import modularfurnace.ModularFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class BlockTextureOverlay extends Block {

	public BlockTextureOverlay(int par1) {
		super(par1, Material.glass);
		setUnlocalizedName("overLayTexture");
		setCreativeTab(ModularFurnace.tabModularFurnace);
		this.setHardness(3.5F);
		this.setStepSound(soundGlassFootstep);
		this.setLightOpacity(0);
		
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return this.blockID;
	}
	
	@Override
	 public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
    {
        return 100000F;
    }
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}


	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon(ModularFurnace.textureName);
	}

}
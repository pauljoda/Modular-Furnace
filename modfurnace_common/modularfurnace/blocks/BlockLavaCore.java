package modularfurnace.blocks;

import modularfurnace.ModularFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockLavaCore extends Block
{
    public BlockLavaCore(int par1, Material par2Material) {
        super(par1, par2Material);
		
		setUnlocalizedName("blockLavaCore");
		setHardness(5f);
		setCreativeTab(ModularFurnace.tabModularFurnace);
		setLightValue(1F);
	}
	
	   @Override
	    public void registerIcons(IconRegister iconRegister)
	    {
	        blockIcon = iconRegister.registerIcon("lavacore");
	    }
}
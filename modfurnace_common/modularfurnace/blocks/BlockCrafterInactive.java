package modularfurnace.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;


public class BlockCrafterInactive extends Block {

    public BlockCrafterInactive(int par1, Material par2Material) {
        super(par1, par2Material);
        
        setUnlocalizedName("crafterInactive");
        setStepSound(Block.soundStoneFootstep);
        setHardness(3.5f);
        setCreativeTab(CreativeTabs.tabDecorations);
        
    }

}

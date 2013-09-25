package modularfurnace.blocks;

import modularfurnace.ModularFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;


public class BlockCrafterInactive extends Block {

    public BlockCrafterInactive(int par1, Material par2Material) {
        super(par1, par2Material);
        
        setUnlocalizedName("crafterInactive");
        setStepSound(Block.soundStoneFootstep);
        setHardness(3.5f);
        setCreativeTab(ModularFurnace.tabModularFurnace);
        
    }
    
    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("crafterInactive");
    }

}

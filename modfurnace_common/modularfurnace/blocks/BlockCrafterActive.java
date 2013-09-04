package modularfurnace.blocks;

import java.util.Random;

import modularfurnace.lib.Reference;
import modularfurnace.tileentity.TileEntityFurnaceCore;
import modularfurnace.tileentity.TileEntityFurnaceDummy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCrafterActive extends BlockContainer


{
    public BlockCrafterActive(int blockId)
    {
        
        super(blockId, Material.rock);
        
        setUnlocalizedName("crafterActive");
        setStepSound(Block.soundWoodFootstep);
        setHardness(3.5f);
        setCreativeTab(CreativeTabs.tabDecorations);
    }
    public int meta = 0;
    
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
  
        return Reference.crafterInactive;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityFurnaceDummy();
    }
    
    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("crafterActive");
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6)
    {
        TileEntityFurnaceDummy dummy = (TileEntityFurnaceDummy)world.getBlockTileEntity(x, y, z);
        
        if(dummy != null && dummy.getCore() != null)
            dummy.getCore().invalidateMultiblock();
        
        super.breakBlock(world, x, y, z, par5, par6);
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if(player.isSneaking())
            return false;
        
        TileEntityFurnaceDummy dummy = (TileEntityFurnaceDummy)world.getBlockTileEntity(x, y, z);
        
        if(dummy != null && dummy.getCore() != null)
        {
            TileEntityFurnaceCore core = dummy.getCore();
            return core.getBlockType().onBlockActivated(world, core.xCoord, core.yCoord, core.zCoord, player, par6, par7, par8, par9);
        }
        
        return true;
    }
}
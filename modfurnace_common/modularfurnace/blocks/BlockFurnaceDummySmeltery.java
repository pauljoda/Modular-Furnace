package modularfurnace.blocks;

import java.util.Random;

import modularfurnace.client.ClientProxy;
import modularfurnace.tileentity.TileEntityFurnaceCoreSmeltery;
import modularfurnace.tileentity.TileEntityFurnaceDummySmeltery;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFurnaceDummySmeltery extends BlockContainer


{
    public BlockFurnaceDummySmeltery(int blockId)
    {
        
        super(blockId, Material.rock);
        
        setUnlocalizedName("blockFurnaceDummySmeltery");
        setStepSound(Block.soundStoneFootstep);
        setHardness(3.5f);
    }
    public int meta = 0;
    
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
  
        return Block.brick.blockID;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityFurnaceDummySmeltery();
    }
    
    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("smelterBrick");
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6)
    {
        TileEntityFurnaceDummySmeltery dummy = (TileEntityFurnaceDummySmeltery)world.getBlockTileEntity(x, y, z);
        
        if(dummy != null && dummy.getCore() != null)
            dummy.getCore().invalidateMultiblock();
        
        super.breakBlock(world, x, y, z, par5, par6);
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if(player.isSneaking())
            return false;
        
        TileEntityFurnaceDummySmeltery dummy = (TileEntityFurnaceDummySmeltery)world.getBlockTileEntity(x, y, z);
        
        if(dummy != null && dummy.getCore() != null)
        {
            TileEntityFurnaceCoreSmeltery core = dummy.getCore();
            return core.getBlockType().onBlockActivated(world, core.xCoord, core.yCoord, core.zCoord, player, par6, par7, par8, par9);
        }
        
        return true;
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
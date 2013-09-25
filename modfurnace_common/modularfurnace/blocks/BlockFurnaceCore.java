package modularfurnace.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import modularfurnace.ModularFurnace;
import modularfurnace.lib.Reference;
import modularfurnace.tileentity.TileEntityFurnaceCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFurnaceCore extends BlockContainer
{
    public static final int META_ISACTIVE = 0x00000008;
    public static final int MASK_DIR = 0x00000007;
    public static final int META_DIR_NORTH = 0x00000001;
    public static final int META_DIR_SOUTH = 0x00000002;
    public static final int META_DIR_EAST = 0x00000003;
    public static final int META_DIR_WEST = 0x00000000;
    
    private final boolean isActive;
    private static boolean keepFurnaceInventory;
    private final Random furnaceRand = new Random();

    
    @SideOnly(Side.CLIENT)
    private Icon furnaceIconTop;
    @SideOnly(Side.CLIENT)
    private Icon furnaceIconFront;
	private Icon furnaceIconFrontLit;

    
    public BlockFurnaceCore(int blockId, boolean par2)
    {
        super(blockId, Material.rock);
        this.isActive = par2;
        
        setUnlocalizedName("blockFurnaceCore");
        setStepSound(Block.soundStoneFootstep);
        setHardness(3.5f);
        setCreativeTab(ModularFurnace.tabModularFurnace);
    }
    
    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        return ((world.getBlockMetadata(x, y, z) >> 3) == 0 ? 0 : 15); 
    }
    
    
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
    	int facing = META_DIR_WEST;
    	int metadata = 0;
    	
        int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
            facing = META_DIR_NORTH;
        }

        if (l == 1)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
            facing = META_DIR_EAST;
        }

        if (l == 2)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
            facing = META_DIR_SOUTH;
        }

        if (l == 3)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
            facing = META_DIR_WEST;
        }

        if (par6ItemStack.hasDisplayName())
        {
            ((TileEntityFurnaceCore)par1World.getBlockTileEntity(par2, par3, par4)).setGuiDisplayName(par6ItemStack.getDisplayName());
        }
        metadata |= facing;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, metadata, 2);
    }

	@Override
	public Icon getIcon(int side, int metadata)
	{
		int facing = (metadata & MASK_DIR);
		
		return (side == getSideFromFacing(facing) ? (isActive ? furnaceIconFrontLit : furnaceIconFront) : blockIcon);
	}

    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("coresides");
        this.furnaceIconTop = par1IconRegister.registerIcon("coresides");
        this.furnaceIconFrontLit = par1IconRegister.registerIcon("ovenlit");
        this.furnaceIconFront = par1IconRegister.registerIcon("oven");
    }
    
    
    
	private static int getSideFromFacing(int facing)
	{
		switch(facing)
		{
		case META_DIR_WEST:
			return 4;
			
		case META_DIR_EAST:
			return 5;
			
		case META_DIR_NORTH:
			return 2;
			
		case META_DIR_SOUTH:
			return 3;
			
		default:
			return 4;
		}
	}

	   public static void updateFurnaceBlockState(boolean par0, World par1World, int par2, int par3, int par4)
	    {
	        int l = par1World.getBlockMetadata(par2, par3, par4);
	        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
	        keepFurnaceInventory = true;
	        

	        if (par0)
	        {
	            par1World.setBlock(par2, par3, par4, Reference.furnaceCoreActiveID);
	        }
	        else
	        {
	            par1World.setBlock(par2, par3, par4, Reference.furnaceCoreID);
	        }

	        keepFurnaceInventory = false;
	        par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);

	        if (tileentity != null)
	        {
	            tileentity.validate();
	            par1World.setBlockTileEntity(par2, par3, par4, tileentity);
	        }
	    }
	   
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
    	 TileEntityFurnaceCore tileEntity = (TileEntityFurnaceCore)world.getBlockTileEntity(x, y, z);
        if(player.isSneaking())
        {
            return false;
        }

        if(tileEntity != null)
        {
            // Determine if the Multiblock is currently known to be valid
            if(!tileEntity.getIsValid())
            {
                if(tileEntity.checkIfProperlyFormed())
                {
                    tileEntity.convertDummies();
                     
                }
            }
            
            // Check if the multi-block structure has been formed.
            if(tileEntity.getIsValid())
            {

            	player.openGui(ModularFurnace.instance, Reference.getGui(world, x, y, z), world, x, y, z);
             
            }
        
        }
        
        return true;
    }
    

	@Override
	  public void randomDisplayTick(World world, int x, int y, int z, Random prng)
    {
		int metadata = world.getBlockMetadata(x, y, z);
    
        if (this.isActive)
        {
        	int facing = metadata & MASK_DIR;
    		
    		double yMod = (0.3 * prng.nextDouble());
    		double xMod = -0.02;
    		double zMod = (0.75 - (0.5 * prng.nextDouble()));
    		double temp = 0.0;
    		
    		switch(facing)
    		{
    		case META_DIR_EAST:
    			xMod += 1.04;
    			break;
    			
    		case META_DIR_NORTH:
    			temp = xMod;
    			xMod = zMod;
    			zMod = temp;
    			break;
    			
    		case META_DIR_SOUTH:
    			temp = xMod;
    			xMod = zMod;
    			zMod = temp + 1.04;
    			break;
    		
    		default:
    			break;
    		}
    		
    		world.spawnParticle("smoke", x + xMod, y + yMod, z + zMod, 0, 0, 0);
    		world.spawnParticle("flame", x + xMod, y + yMod, z + zMod, 0, 0, 0);
        }
    }
	
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityFurnaceCore();
    }
    
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
    	TileEntityFurnaceCore tileentityfurnace = (TileEntityFurnaceCore)par1World.getBlockTileEntity(par2, par3, par4);

        if (!keepFurnaceInventory)
        {

            if (tileentityfurnace != null)
            {
            	tileentityfurnace.invalidateMultiblock();
            	
                for (int j1 = 0; j1 < tileentityfurnace.getSizeInventory(); ++j1)
                {
                    ItemStack itemstack = tileentityfurnace.getStackInSlot(j1);

                    if (itemstack != null)
                    {
                        float f = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int k1 = this.furnaceRand.nextInt(21) + 10;

                            if (k1 > itemstack.stackSize)
                            {
                                k1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= k1;
                            EntityItem entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (double)((float)this.furnaceRand.nextGaussian() * f3);
                            entityitem.motionY = (double)((float)this.furnaceRand.nextGaussian() * f3 + 0.2F);
                            entityitem.motionZ = (double)((float)this.furnaceRand.nextGaussian() * f3);
                            par1World.spawnEntityInWorld(entityitem);
                            
                        }
                        
                    }
                }

                par1World.func_96440_m(par2, par3, par4, par5);
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        
    }
}
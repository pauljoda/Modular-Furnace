package modularfurnace.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import modularfurnace.ModularFurnace;
import modularfurnace.client.ClientProxy;
import modularfurnace.lib.Reference;
import modularfurnace.tileentity.TileEntityFurnaceCore;
import modularfurnace.tileentity.TileEntityFurnaceMultiCore;
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

public class BlockMultiFurnaceCore extends BlockContainer {

	private static boolean keepFurnaceInventory;
	private final boolean isActive;
	private final Random furnaceRand = new Random();

	@SideOnly(Side.CLIENT)
	private Icon furnaceIconTop;
	@SideOnly(Side.CLIENT)
	private Icon furnaceIconFront;

	public BlockMultiFurnaceCore(int blockId, boolean par2)
	{
		super(blockId, Material.rock);
		this.isActive = par2;

		setUnlocalizedName("blockMultiFurnaceCore");
		setStepSound(Block.soundStoneFootstep);
		setHardness(3.5f);
		setCreativeTab(ModularFurnace.tabModularFurnace);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		return ((world.getBlockMetadata(x, y, z) >> 3) == 0 ? 0 : 15); 
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return BlockManager.furnaceCoreMulti.blockID;
	}

	@Override
	public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return BlockManager.furnaceCoreMulti.blockID;
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		super.onBlockAdded(par1World, par2, par3, par4);
		this.setDefaultDirection(par1World, par2, par3, par4);
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (l == 0)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
		}

		if (l == 1)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);

		}

		if (l == 2)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);

		}

		if (l == 3)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);

		}

		if (par6ItemStack.hasDisplayName())
		{
			((TileEntityFurnaceCore)par1World.getBlockTileEntity(par2, par3, par4)).setGuiDisplayName(par6ItemStack.getDisplayName());
		}
	}

	private void setDefaultDirection(World par1World, int par2, int par3, int par4)
	{
		if (!par1World.isRemote)
		{
			int l = par1World.getBlockId(par2, par3, par4 - 1);
			int i1 = par1World.getBlockId(par2, par3, par4 + 1);
			int j1 = par1World.getBlockId(par2 - 1, par3, par4);
			int k1 = par1World.getBlockId(par2 + 1, par3, par4);
			byte b0 = 3;

			if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
			{
				b0 = 3;
			}

			if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
			{
				b0 = 2;
			}

			if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
			{
				b0 = 5;
			}

			if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
			{
				b0 = 4;
			}

			par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
		}
	}


	@Override
	public Icon getIcon(int par1, int par2)
	{
		if(par1 == par2)
			return this.furnaceIconFront;
		return par1 == 1 ? this.furnaceIconTop : (par1 == 0 ? this.furnaceIconTop : (par1 != par2 ? this.blockIcon : this.furnaceIconFront));     
	}


	@SideOnly(Side.CLIENT)

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon("furnace_side");
		this.furnaceIconFront = par1IconRegister.registerIcon(this.isActive ? "furnace_front_on" : "furnace_front_off");
		this.furnaceIconTop = par1IconRegister.registerIcon("furnace_top");
	}

	public static void updateFurnaceBlockState(boolean par0, World par1World, int par2, int par3, int par4)
	{
		int l = par1World.getBlockMetadata(par2, par3, par4);
		TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
		keepFurnaceInventory = true;


		if (par0)
		{
			par1World.setBlock(par2, par3, par4, Reference.furnaceCoreMultiActiveID);
		}
		else
		{
			par1World.setBlock(par2, par3, par4, Reference.furnaceCoreMultiID);
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
		TileEntityFurnaceMultiCore tileEntity = (TileEntityFurnaceMultiCore)world.getBlockTileEntity(x, y, z);
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
				player.openGui(ModularFurnace.instance, 3, world, x, y, z);
			}

		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityFurnaceMultiCore();
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntityFurnaceMultiCore tileentityfurnace = (TileEntityFurnaceMultiCore)par1World.getBlockTileEntity(par2, par3, par4);

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

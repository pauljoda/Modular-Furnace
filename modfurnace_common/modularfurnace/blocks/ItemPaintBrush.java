package modularfurnace.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modularfurnace.ModularFurnace;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPaintBrush extends Item {
	int block = 0;
	int location = 0;

	public ItemPaintBrush(int par1) {
		super(par1);
		setCreativeTab(ModularFurnace.tabModularFurnace);
		this.setUnlocalizedName("paintBrush");

	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon("paintBrush");

	}
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{

		if(par2EntityPlayer.isSneaking())
			if(!par3World.blockHasTileEntity(par4, par5, par6))
				block  = par3World.getBlockId(par4, par5, par6);

		if(!par2EntityPlayer.isSneaking())
		{
			if(!par3World.blockHasTileEntity(par4, par5, par6))
			{
				location = par3World.getBlockId(par4, par5, par6);
				for(int depth = -2; depth <= 2; depth++)
				{
					for(int row = -2; row <=2; row++)
					{
						for(int line = -2; line <= 2; line++)
						{
							if(par3World.getBlockId(par4 + row, par5 + depth, par6 + line) == location)
							{
								par3World.setBlock(par4 + row, par5 + depth, par6 + line, block);
							}
						}
					}
				}
			}
		}

		par3World.setBlock(par4, par5, par6, block);

		return false;
	}
}
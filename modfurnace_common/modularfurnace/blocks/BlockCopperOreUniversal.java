package modularfurnace.blocks;

import java.util.List;
import java.util.Random;

import modularfurnace.ModularFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class BlockCopperOreUniversal extends Block {
	private final String setInfo;
	private final String setColor;


	public BlockCopperOreUniversal(int par1) {
		super(par1, Material.rock);
		setUnlocalizedName("copperOreUniversal");
		setCreativeTab(ModularFurnace.tabModularFurnace);
		setInfo = "Modular Furance : Used in Conversion";
		setColor = "\u00A70";

	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{

		return this.blockID;
	}


	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon("modularOreCopper");
	}

	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean bool) {
		list.add(setToolTipData());
	}
	private String setToolTipData(){
		return this.setColor + this.setInfo;
	}
}

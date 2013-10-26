package modularfurnace.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modularfurnace.ModularFurnace;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class MetalAluminiumIngot extends Item {

	public MetalAluminiumIngot(int par1) {
		super(par1);
		setCreativeTab(ModularFurnace.tabModularFurnace);
		this.setUnlocalizedName("aluminiumIngot");
		
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon("modularIngotAluminium");

	}
}
package com.pauljoda.modularfurnace.client;

import com.pauljoda.modularfurnace.client.gui.GuiModularFurnace;
import com.pauljoda.modularfurnace.client.gui.GuiModularFurnaceEnabled;
import com.pauljoda.modularfurnace.common.CommonProxy;
import com.pauljoda.modularfurnace.tileentity.TileEntityFurnaceCore;
import cpw.mods.fml.client.registry.RenderingRegistry;
import com.pauljoda.modularfurnace.blocks.renderer.DummyRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy
{
	
	public static int renderPass;

	 public static int dummyRenderType;
     
     public static void setCustomRenderers()
     {
             dummyRenderType = RenderingRegistry.getNextAvailableRenderId();
             RenderingRegistry.registerBlockHandler(new DummyRenderer());
     }
	
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		//TileEntityFurnaceCoreSmeltery tileEntity1 = (TileEntityFurnaceCoreSmeltery)world.getBlockTileEntity(x, y, z);


		if(tileEntity != null)
			if(tileEntity instanceof TileEntityFurnaceCore)
			{
				TileEntityFurnaceCore tileEntity1 = (TileEntityFurnaceCore)world.getTileEntity(x, y, z);

				if(tileEntity1.checkIfCrafting())
				{

					return new GuiModularFurnaceEnabled(player.inventory, world, x, y, z, tileEntity1);
				}
				else
				{
					return new GuiModularFurnace(player.inventory, tileEntity1);
				}
			}
		
		return null;
	}

}


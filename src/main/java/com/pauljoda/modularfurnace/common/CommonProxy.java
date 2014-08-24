package com.pauljoda.modularfurnace.common;

import com.pauljoda.modularfurnace.tileentity.ContainerModularFurnace;
import com.pauljoda.modularfurnace.tileentity.ContainerModularFurnaceCrafter;
import com.pauljoda.modularfurnace.tileentity.TileEntityFurnaceCore;
import com.pauljoda.modularfurnace.tileentity.TileEntityFurnaceDummy;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler
{
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityFurnaceCore.class, "tileEntityFurnaceCore");
		GameRegistry.registerTileEntity(TileEntityFurnaceDummy.class, "tileEntityFurnaceDummy");
	}


	@Override
	public Object getServerGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		//TileEntityFurnaceCoreSmeltery tileEntity1 = (TileEntityFurnaceCoreSmeltery)world.getBlockTileEntity(x, y, z);

		if(tileEntity != null)
			if(tileEntity instanceof TileEntityFurnaceCore)
			{
				TileEntityFurnaceCore tileEntity1 = (TileEntityFurnaceCore)world.getTileEntity(x, y, z);
				if(tileEntity1.checkIfCrafting())
				{
					return new ContainerModularFurnaceCrafter(player.inventory, world, x, y, z, tileEntity1);  
				}
				else
				{
					return new ContainerModularFurnace(player.inventory, tileEntity1);
				}
			}

		
		return null;
	}

	@Override
	public Object getClientGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}

}

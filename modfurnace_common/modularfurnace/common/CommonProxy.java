package modularfurnace.common;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import modularfurnace.tileentity.ContainerModularFurnace;
import modularfurnace.tileentity.ContainerModularFurnaceCrafter;
import modularfurnace.tileentity.ContainerModularFurnaceSmeltery;
import modularfurnace.tileentity.TileEntityFurnaceCore;
import modularfurnace.tileentity.TileEntityFurnaceCoreSmeltery;
import modularfurnace.tileentity.TileEntityFurnaceDummy;
import modularfurnace.tileentity.TileEntityFurnaceDummySmeltery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler
{
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityFurnaceCore.class, "tileEntityFurnaceCore");
		GameRegistry.registerTileEntity(TileEntityFurnaceDummy.class, "tileEntityFurnaceDummy");
		GameRegistry.registerTileEntity(TileEntityFurnaceCoreSmeltery.class, "tileEntityFurnaceCoreSmeltery");
		GameRegistry.registerTileEntity(TileEntityFurnaceDummySmeltery.class, "tileEntityFurnaceDummySmeltery");
	}


	@Override
	public Object getServerGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		//TileEntityFurnaceCoreSmeltery tileEntity1 = (TileEntityFurnaceCoreSmeltery)world.getBlockTileEntity(x, y, z);

		if(tileEntity != null)
			if(tileEntity instanceof TileEntityFurnaceCore)
			{
				TileEntityFurnaceCore tileEntity1 = (TileEntityFurnaceCore)world.getBlockTileEntity(x, y, z);
				if(tileEntity1.checkIfCrafting())
				{
					return new ContainerModularFurnaceCrafter(player.inventory, world, x, y, z, tileEntity1);  
				}
				else
				{
					return new ContainerModularFurnace(player.inventory, tileEntity1);
				}
			}

		if(tileEntity instanceof TileEntityFurnaceCoreSmeltery)
		{
			TileEntityFurnaceCoreSmeltery tileEntitySmelt = (TileEntityFurnaceCoreSmeltery)world.getBlockTileEntity(x, y, z);
			return new ContainerModularFurnaceSmeltery(player.inventory, tileEntitySmelt);
		}


		return null;
	}

	@Override
	public Object getClientGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}

}

package modularfurnace.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import modularfurnace.blocks.renderer.DummyRenderer;
import modularfurnace.client.gui.GuiModularFurnace;
import modularfurnace.client.gui.GuiModularFurnaceEnabled;
import modularfurnace.client.gui.GuiModularFurnaceMulti;
import modularfurnace.client.gui.GuiModularFurnaceSmeltery;
import modularfurnace.common.CommonProxy;
import modularfurnace.tileentity.TileEntityFurnaceCore;
import modularfurnace.tileentity.TileEntityFurnaceCoreSmeltery;
import modularfurnace.tileentity.TileEntityFurnaceMultiCore;
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

		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		//TileEntityFurnaceCoreSmeltery tileEntity1 = (TileEntityFurnaceCoreSmeltery)world.getBlockTileEntity(x, y, z);


		if(tileEntity != null)
			if(tileEntity instanceof TileEntityFurnaceCore)
			{
				TileEntityFurnaceCore tileEntity1 = (TileEntityFurnaceCore)world.getBlockTileEntity(x, y, z);

				if(tileEntity1.checkIfCrafting())
				{

					return new GuiModularFurnaceEnabled(player.inventory, world, x, y, z, tileEntity1);
				}
				else
				{
					return new GuiModularFurnace(player.inventory, tileEntity1);
				}
			}
		
		if(tileEntity instanceof TileEntityFurnaceCoreSmeltery)
		{
			TileEntityFurnaceCoreSmeltery tileEntitySmelt = (TileEntityFurnaceCoreSmeltery)world.getBlockTileEntity(x, y, z);
			return new GuiModularFurnaceSmeltery(player.inventory, tileEntitySmelt);
		}
		
		if(tileEntity instanceof TileEntityFurnaceMultiCore)
		{
			TileEntityFurnaceMultiCore tileEntityMulti = (TileEntityFurnaceMultiCore)world.getBlockTileEntity(x, y, z);
			return new GuiModularFurnaceMulti(player.inventory, tileEntityMulti);
		}

		return null;
	}

}


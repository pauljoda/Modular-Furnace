package modularfurnace.client;

import modularfurnace.client.gui.GuiModularFurnace;
import modularfurnace.client.gui.GuiModularFurnaceEnabled;
import modularfurnace.common.CommonProxy;
import modularfurnace.tileentity.TileEntityFurnaceCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy
{
    
   
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        
        TileEntityFurnaceCore tileEntity = (TileEntityFurnaceCore)world.getBlockTileEntity(x, y, z);
  
        if(tileEntity != null)
            if(tileEntity.checkIfCrafting())
            {

            return new GuiModularFurnaceEnabled(player.inventory, world, x, y, z, tileEntity);
            }
            else
            {
                return new GuiModularFurnace(player.inventory, tileEntity);
            }
        
        return null;
    }
}


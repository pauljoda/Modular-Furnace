package modularfurnace.common;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import modularfurnace.tileentity.ContainerModularFurnace;
import modularfurnace.tileentity.ContainerModularFurnaceCrafter;
import modularfurnace.tileentity.TileEntityFurnaceCore;
import modularfurnace.tileentity.TileEntityFurnaceDummy;
import net.minecraft.entity.player.EntityPlayer;
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
        TileEntityFurnaceCore tileEntity = (TileEntityFurnaceCore)world.getBlockTileEntity(x, y, z);
        
        if(tileEntity != null)
            if(tileEntity.checkIfCrafting())
            {
                return new ContainerModularFurnaceCrafter(player.inventory, world, x, y, z, tileEntity);  
            }
            else
            {
                return new ContainerModularFurnace(player.inventory, tileEntity);
            }
            
        
        return null;
    }
    
    @Override
    public Object getClientGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }
}

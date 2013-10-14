package modularfurnace.lib;

import net.minecraft.world.World;
import modularfurnace.ModularFurnace;
import modularfurnace.tileentity.TileEntityFurnaceCore;


public class Reference {

    public static final String MOD_ID = "modularfurnace";
    public static final String MOD_NAME = "Modular Furnace";
    public static final String Version = "1.0";
    
    public static int furnaceCoreID = ModularFurnace.furnaceCoreID;
    public static int furnaceCoreActiveID = ModularFurnace.furnaceCoreActiveID;
    public static int furnaceDummyID = ModularFurnace.furnaceDummyID;
    public static int furnaceDummyIDRedstone = ModularFurnace.furnaceDummyIDRedstone;
    public static int furnaceDummyIDGlowStone = ModularFurnace.furnaceDummyIDGlowStone;
    public static int furnaceDummyIDDiamond = ModularFurnace.furnaceDummyIDDiamond;
    public static int crafterInactive = ModularFurnace.crafterInactiveID;
    public static int crafterActive = ModularFurnace.crafterActiveID;
    public static int furnaceDummyIDEmerald = ModularFurnace.furnaceDummyIDEmerald;
    public static int lavaCore = ModularFurnace.laveCoreID;
    public static int furnaceDummyIOID = ModularFurnace.furnaceDummyIOID;
    public static int furnaceDummyActiveIOID = ModularFurnace.furnaceDummyActiveIOID;


    
    public static int modularFurnaceGui = 0;
    public static int modularFurnaceGuiCrafter = 1;
    
    public static int getGui(World world, int x, int y, int z)
    {
        TileEntityFurnaceCore tileEntity = (TileEntityFurnaceCore)world.getBlockTileEntity(x, y, z);
        if(tileEntity.crafterEnabled)
        {
            return 1;
        }
        return 0;
    }
   
    
    
    
   
  
}

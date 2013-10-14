package modularfurnace.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import modularfurnace.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class BlockManager
{
    public static Block furnaceCore = null;
    public static Block furnaceDummy = null;
    public static Block furnaceDummyRedstone = null;
    public static Block furnaceDummyGlowStone = null;
    public static Block furnaceDummyDiamond = null;
    public static Block crafterInactive = null;
    public static Block crafterActive = null;
    public static Block furnaceDummyEmerald = null;
    public static Block furnaceCoreActive = null;
    public static Block lavaCore = null;
    public static Block furnaceDummyIO = null;
    public static Block furnaceDummyActiveIO = null;
    
    public static void registerBlocks()
    {
        furnaceCore = new BlockFurnaceCore(Reference.furnaceCoreID, false);
        furnaceCoreActive = new BlockFurnaceCore(Reference.furnaceCoreActiveID, true);
        furnaceDummy = new BlockFurnaceDummy(Reference.furnaceDummyID);
        furnaceDummyRedstone = new BlockFurnaceDummyRedstone(Reference.furnaceDummyIDRedstone);
        furnaceDummyGlowStone = new BlockFurnaceDummyIron(Reference.furnaceDummyIDGlowStone);
        furnaceDummyDiamond = new BlockFurnaceDummyDiamond(Reference.furnaceDummyIDDiamond);
        crafterInactive = new BlockCrafterInactive(Reference.crafterInactive, Material.wood);
        crafterActive = new BlockCrafterActive(Reference.crafterActive);
        furnaceDummyEmerald = new BlockFurnaceDummyEmerald(Reference.furnaceDummyIDEmerald);
        lavaCore = new BlockLavaCore(Reference.lavaCore, Material.iron);
        furnaceDummyIO = new BlockFurnaceDummyIO(Reference.furnaceDummyIOID, Material.rock);
        furnaceDummyActiveIO = new BlockFurnaceDummyIOActive(Reference.furnaceDummyActiveIOID);

        
        GameRegistry.registerBlock(furnaceCore, "blockFurnaceCore");
        GameRegistry.registerBlock(furnaceDummy, "blockFurnaceDummy");
        GameRegistry.registerBlock(furnaceDummyRedstone, "blockFurnaceDummyRedstone");
        GameRegistry.registerBlock(furnaceDummyGlowStone, "blockFurnaceDummyGlowStone");
        GameRegistry.registerBlock(crafterInactive, "crafterInactive");
        GameRegistry.registerBlock(crafterActive, "crafterActive");
        GameRegistry.registerBlock(furnaceDummyDiamond, "blockFurnaceDummyDiamond");
        GameRegistry.registerBlock(furnaceDummyEmerald, "blockFurnaceDummyEmerald");
        GameRegistry.registerBlock(lavaCore, "blockLavaCore");
        GameRegistry.registerBlock(furnaceDummyIO, "furnaceDummyIO");
        GameRegistry.registerBlock(furnaceDummyActiveIO, "furnaceDummyActiveIO");


        
        LanguageRegistry.addName(furnaceCore, "Modular Furnace Core");
        LanguageRegistry.addName(furnaceDummy, "Modular Furnace Dummy CobbleStone");
        LanguageRegistry.addName(furnaceDummyRedstone, "Modular Furnace Dummy Redstone");
        LanguageRegistry.addName(furnaceDummyGlowStone, "Modular Furnace Dummy Iron");
        LanguageRegistry.addName(crafterInactive, "Crafting Core");
        LanguageRegistry.addName(crafterActive, "Crafting Core Active");
        LanguageRegistry.addName(furnaceDummyDiamond, "Modular Furnace Dummy Diamond");
        LanguageRegistry.addName(furnaceDummyEmerald, "Modular Furance Dummy Emerald");
        LanguageRegistry.addName(lavaCore, "Lava Lamp");
        LanguageRegistry.addName(furnaceDummyIO, "Modular Furnace IO");
        LanguageRegistry.addName(furnaceDummyActiveIO, "Modular Furnace IO Active");



        
    }
    
    public static void registerCraftingRecipes()
    {
        CraftingManager.getInstance().addRecipe(new ItemStack(furnaceCore, 1),
                "XXX",
                "XxX",
                "XXX", 'X', Block.stoneBrick, 'x', Block.furnaceIdle);
        
        CraftingManager.getInstance().addRecipe(new ItemStack(furnaceDummyIO, 1),
                "XXX",
                "XxX",
                "XXX", 'X', Block.stoneBrick, 'x', Block.dispenser);
        
        CraftingManager.getInstance().addRecipe(new ItemStack(crafterInactive, 1),
                "XxX",
                "xCx",
                "XxX", 'X', Item.ingotIron, 'x', new ItemStack(Item.dyePowder, 9, 4), 'C', Block.workbench); 
        
     CraftingManager.getInstance().addRecipe(new ItemStack(lavaCore, 1),
                "XXX",
                "XCX",
                "XXX", 'X', Block.glass, 'C', Item.bucketLava); 
                
    }
}

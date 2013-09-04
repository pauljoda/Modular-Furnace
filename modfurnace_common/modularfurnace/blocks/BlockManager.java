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
    
    public static void registerBlocks()
    {
        furnaceCore = new BlockFurnaceCore(Reference.furnaceCoreID);
        furnaceDummy = new BlockFurnaceDummy(Reference.furnaceDummyID);
        furnaceDummyRedstone = new BlockFurnaceDummyRedstone(Reference.furnaceDummyIDRedstone);
        furnaceDummyGlowStone = new BlockFurnaceDummyGlowStone(Reference.furnaceDummyIDGlowStone);
        furnaceDummyDiamond = new BlockFurnaceDummyDiamond(Reference.furnaceDummyIDDiamond);
        crafterInactive = new BlockCrafterInactive(Reference.crafterInactive, Material.wood);
        crafterActive = new BlockCrafterActive(Reference.crafterActive);
        furnaceDummyEmerald = new BlockFurnaceDummyEmerald(Reference.furnaceDummyIDEmerald);
        
        GameRegistry.registerBlock(furnaceCore, "blockFurnaceCore");
        GameRegistry.registerBlock(furnaceDummy, "blockFurnaceDummy");
        GameRegistry.registerBlock(furnaceDummyRedstone, "blockFurnaceDummyRedstone");
        GameRegistry.registerBlock(furnaceDummyGlowStone, "blockFurnaceDummyGlowStone");
        GameRegistry.registerBlock(crafterInactive, "crafterInactive");
        GameRegistry.registerBlock(crafterActive, "crafterActive");
        GameRegistry.registerBlock(furnaceDummyDiamond, "blockFurnaceDummyDiamond");
        GameRegistry.registerBlock(furnaceDummyEmerald, "blockFurnaceDummyEmerald");
        
        LanguageRegistry.addName(furnaceCore, "Modular Furnace Core");
        LanguageRegistry.addName(furnaceDummy, "Modular Furnace Dummy CobbleStone");
        LanguageRegistry.addName(furnaceDummyRedstone, "Modular Furnace Dummy Redstone");
        LanguageRegistry.addName(furnaceDummyGlowStone, "Modular Furnace Dummy");
        LanguageRegistry.addName(crafterInactive, "Crafting Core");
        LanguageRegistry.addName(crafterActive, "Crafting Core Active");
        LanguageRegistry.addName(furnaceDummyDiamond, "Modular Furnace Dummy Diamond");
        LanguageRegistry.addName(furnaceDummyEmerald, "Modular Furance Dummy Emerald");
        
    }
    
    public static void registerCraftingRecipes()
    {
        CraftingManager.getInstance().addRecipe(new ItemStack(furnaceCore, 1),
                "XXX",
                "XxX",
                "XXX", 'X', Block.stoneBrick, 'x', Block.furnaceIdle);
        
        CraftingManager.getInstance().addRecipe(new ItemStack(crafterInactive, 1),
                "XxX",
                "xCx",
                "XxX", 'X', Item.ingotIron, 'x', new ItemStack(Item.dyePowder, 9, 4), 'C', Block.workbench); 
    }
}

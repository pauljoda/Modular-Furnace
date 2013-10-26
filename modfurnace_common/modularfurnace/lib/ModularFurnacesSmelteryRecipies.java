package modularfurnace.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modularfurnace.blocks.BlockManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModularFurnacesSmelteryRecipies    
{
private static final ModularFurnacesSmelteryRecipies smeltingBase = new ModularFurnacesSmelteryRecipies();

/** The list of smelting results. */
private Map smeltingList = new HashMap();
private Map experienceList = new HashMap();
private HashMap<List<Integer>, ItemStack> metaSmeltingList = new HashMap<List<Integer>, ItemStack>();
private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();

/**
 * Used to call methods addSmelting and getSmeltingResult.
 */

public static final ModularFurnacesSmelteryRecipies smelting()
{
    return smeltingBase;
}



private ModularFurnacesSmelteryRecipies()
{
	this.addSmelting(Block.oreDiamond.blockID, new ItemStack(Item.diamond), 1.0F);
    this.addSmelting(Block.oreIron.blockID, new ItemStack(Item.ingotIron), 0.7F);
    this.addSmelting(Block.oreGold.blockID, new ItemStack(Item.ingotGold), 1.0F);
    this.addSmelting(Block.oreNetherQuartz.blockID, new ItemStack(Item.netherQuartz), 0.2F);
    this.addSmelting(Block.cobblestone.blockID, new ItemStack(Block.glass), 0.1F);
    this.addSmelting(Block.oreEmerald.blockID, new ItemStack(Item.emerald), 0.5F);
    
}

/**
 * Adds a smelting recipe.
 */


public void addSmelting(int par1, ItemStack par2ItemStack, float par3)
{
    this.smeltingList.put(Integer.valueOf(par1), par2ItemStack);
    this.experienceList.put(Integer.valueOf(par2ItemStack.itemID), Float.valueOf(par3));
}

/**
 * Returns the smelting result of an item.
 * Deprecated in favor of a metadata sensitive version
 */
@Deprecated
public ItemStack getSmeltingResult(int par1)
{
    return (ItemStack)this.smeltingList.get(Integer.valueOf(par1));
}

public Map getSmeltingList()
{
    return this.smeltingList;
}

@Deprecated //In favor of ItemStack sensitive version
public float getExperience(int par1)
{
    return this.experienceList.containsKey(Integer.valueOf(par1)) ? ((Float)this.experienceList.get(Integer.valueOf(par1))).floatValue() : 0.0F;
}

/**
 * A metadata sensitive version of adding a furnace recipe.
 */
public void addSmelting(int itemID, int metadata, ItemStack itemstack, float experience)
{
    metaSmeltingList.put(Arrays.asList(itemID, metadata), itemstack);
    metaExperience.put(Arrays.asList(itemstack.itemID, itemstack.getItemDamage()), experience);
}

/**
 * Used to get the resulting ItemStack form a source ItemStack
 * @param item The Source ItemStack
 * @return The result ItemStack
 */
public ItemStack getSmeltingResult(ItemStack item) 
{	
	int isOre = OreDictionary.getOreID(item);
	int realCopper = OreDictionary.getOreID("oreCopper");
	int realTin = OreDictionary.getOreID("oreTin");
	int realAluminum = OreDictionary.getOreID("oreAluminum");
	
	if(isOre == realCopper)
	{
		return new ItemStack(BlockManager.copperIngot, 1);
	}
	if(isOre == realTin)
	{
		return new ItemStack(BlockManager.tinIngot, 1);
	}
	if(isOre == realAluminum)
	{
		return new ItemStack(BlockManager.aluminiumIngot, 1);
	}
    if (item == null)
    {
        return null;
    }
    ItemStack ret = (ItemStack)metaSmeltingList.get(Arrays.asList(item.itemID, item.getItemDamage()));
    if (ret != null) 
    {
        return ret;
    }
    return (ItemStack)smeltingList.get(Integer.valueOf(item.itemID));
}

/**
 * Grabs the amount of base experience for this item to give when pulled from the furnace slot.
 */
public float getExperience(ItemStack item)
{
    if (item == null || item.getItem() == null)
    {
        return 0;
    }
    float ret = item.getItem().getSmeltingExperience(item);
    if (ret < 0 && metaExperience.containsKey(Arrays.asList(item.itemID, item.getItemDamage())))
    {
        ret = metaExperience.get(Arrays.asList(item.itemID, item.getItemDamage()));
    }
    if (ret < 0 && experienceList.containsKey(item.itemID))
    {
        ret = ((Float)experienceList.get(item.itemID)).floatValue();
    }
    return (ret < 0 ? 0 : ret);
}

public Map<List<Integer>, ItemStack> getMetaSmeltingList()
{
    return metaSmeltingList;
}
}

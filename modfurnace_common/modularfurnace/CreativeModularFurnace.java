package modularfurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

public class CreativeModularFurnace extends CreativeTabs {

	    public CreativeModularFurnace(int tabID, String tabLabel) {

	        super(tabID, tabLabel);
	    }

	    @Override
	    @SideOnly(Side.CLIENT)
	    /**
	     * the itemID for the item to be displayed on the tab
	     */
	    public int getTabIconItemIndex() {

	        return ModularFurnace.crafterInactiveID;
	    }

	}
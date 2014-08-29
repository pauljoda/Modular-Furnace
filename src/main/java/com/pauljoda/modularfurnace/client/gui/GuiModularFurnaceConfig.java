package com.pauljoda.modularfurnace.client.gui;

import com.pauljoda.modularfurnace.util.GeneralSettings;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.GuiConfig;

public class GuiModularFurnaceConfig extends GuiConfig {
    public GuiModularFurnaceConfig(GuiScreen parent) {
        super(parent,
                new ConfigElement(GeneralSettings.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                "Modular Furnace", false, false, GuiConfig.getAbridgedConfigPath(GeneralSettings.config.toString()));
    }
}
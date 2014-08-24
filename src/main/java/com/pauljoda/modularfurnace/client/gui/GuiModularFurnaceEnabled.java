	package com.pauljoda.modularfurnace.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.pauljoda.modularfurnace.tileentity.ContainerModularFurnaceCrafter;
import com.pauljoda.modularfurnace.tileentity.TileEntityFurnaceCore;

public class GuiModularFurnaceEnabled extends GuiContainer
{
    private TileEntityFurnaceCore tileEntity;
    
    private static final ResourceLocation field_110410_t = new ResourceLocation("modularfurnace:textures/crafter.png");
    private static final ResourceLocation infoPane = new ResourceLocation("modularfurnace:textures/stats.png");

    public GuiModularFurnaceEnabled(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5, TileEntityFurnaceCore tileEntity)
    {
        super(new ContainerModularFurnaceCrafter(par1InventoryPlayer, par2World, par3, par4, par5, tileEntity));
        
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        final String invTitle = " Modular Furnace";
        
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        
        final double redStone = tileEntity.getSpeed() / 8;
        String redStoneResult = String.format("%.2f", redStone) + "x";
        final String redStoneWidth = "" + redStoneResult;
        
        final double ironScale = tileEntity.getScaledEfficiency();
        String ironResult = String.format("%.2f", ironScale) + "x";
        final String iron = ironResult;
        
        final int multiplicity = tileEntity.smeltingMultiplier;
        String multiplicityText = String.valueOf(multiplicity) + "x";

        fontRendererObj.drawString("Stats:", -48 , 6, 4210752);
        fontRendererObj.drawString(iron, -48, 15 , 5000000);
        fontRendererObj.drawString(redStoneWidth, -48, 24, 3999000);
        fontRendererObj.drawString(multiplicityText, -48, 33, 4210752);
        
        fontRendererObj.drawString(invTitle, xSize / 2 - fontRendererObj.getStringWidth(invTitle) / 20, 6, 4210752);
        
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        
        this.mc.getTextureManager().bindTexture(field_110410_t);
        
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        int i1;
        
        if(tileEntity.isBurning())
        {
            i1 = tileEntity.getBurnTimeRemainingScaled(12);
            drawTexturedModalRect(x + 87, y + 49 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }
        
        i1 = tileEntity.getCookProgressScaled(24);
        drawTexturedModalRect(x + 111, y + 46, 176, 14, i1 + 1, 16);
        
        this.mc.getTextureManager().bindTexture(infoPane);
        drawTexturedModalRect(x - 52, y, 0, 0, 50, 75);
    }
}
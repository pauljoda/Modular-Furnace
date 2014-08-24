package com.pauljoda.modularfurnace.client.gui;

import org.lwjgl.opengl.GL11;

import com.pauljoda.modularfurnace.tileentity.ContainerModularFurnace;
import com.pauljoda.modularfurnace.tileentity.TileEntityFurnaceCore;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiModularFurnace extends GuiContainer
{
    private TileEntityFurnaceCore tileEntity;
    
    private static final ResourceLocation background = new ResourceLocation("textures/gui/container/furnace.png");
    private static final ResourceLocation infoPane = new ResourceLocation("modularfurnace:textures/stats.png");
    
    public GuiModularFurnace(InventoryPlayer playerInventory, TileEntityFurnaceCore tileEntity)
    {
        super(new ContainerModularFurnace(playerInventory, tileEntity));
        
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        final String invTitle = "Modular Furnace";
        
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
        
        fontRendererObj.drawString(invTitle, xSize / 2 - fontRendererObj.getStringWidth(invTitle) / 2, 6, 4210752);
        
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        
        this.mc.getTextureManager().bindTexture(background);
        
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
      
        
        int i1;
        
        if(tileEntity.isBurning())
        {
            i1 = tileEntity.getBurnTimeRemainingScaled(12);
            drawTexturedModalRect(x + 56, y + 36 + 12 - i1, 176, 13 - i1, 14, i1 + 2);
          
        }
        i1 = tileEntity.getCookProgressScaled(24);
        drawTexturedModalRect(x + 79, y + 34, 176, 14, i1 + 1, 16);
        
        this.mc.getTextureManager().bindTexture(infoPane);
        drawTexturedModalRect(x - 52, y, 0, 0, 50, 75);
    }
    
    
}

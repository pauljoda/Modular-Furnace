package modularfurnace.client.gui;

import modularfurnace.tileentity.ContainerModularFurnaceMulti;
import modularfurnace.tileentity.TileEntityFurnaceMultiCore;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiModularFurnaceMulti extends GuiContainer
{
    private TileEntityFurnaceMultiCore tileEntity;
    
    private static final ResourceLocation field_110410_t = new ResourceLocation("textures/multifurnace.png");

    
    public GuiModularFurnaceMulti(InventoryPlayer playerInventory, TileEntityFurnaceMultiCore tileEntity)
    {
        super(new ContainerModularFurnaceMulti(playerInventory, tileEntity));
        
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        final String invTitle = "Modular Multi Furnace";
        
        final double redStone = 1.14 + (tileEntity.countRedstone() * .28);
        String redStoneResult = String.format("%.2f", redStone);
        final String redStoneWidth = "" + redStoneResult + "x ";
        
        final double ironScale = tileEntity.getEfficiency();
        String ironResult = String.format("%.2f", ironScale);
        final String iron = ironResult + "x ";

       fontRenderer.drawString(iron, xSize - fontRenderer.getStringWidth(iron), ySize - 96 + 2, 5000000);
       fontRenderer.drawString(redStoneWidth, xSize - fontRenderer.getStringWidth(redStoneWidth) - fontRenderer.getStringWidth(iron), ySize - 96 + 2, 3999000);
        
        fontRenderer.drawString(invTitle, xSize / 2 - fontRenderer.getStringWidth(invTitle) / 2, 3, 4210752);
        
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
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
            drawTexturedModalRect(x + 80, y + 47 + 12 - i1, 176, 13 - i1, 14, i1 + 2);
          
        }
        i1 = tileEntity.getCookProgressScaled(24);
        drawTexturedModalRect(x + 79, y + 30, 176, 14, i1 + 1, 16);
    }
    
    
}
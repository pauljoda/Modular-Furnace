package modularfurnace.client.gui;

import modularfurnace.tileentity.ContainerModularFurnaceSmeltery;
import modularfurnace.tileentity.TileEntityFurnaceCoreSmeltery;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiModularFurnaceSmeltery extends GuiContainer
{
    private TileEntityFurnaceCoreSmeltery tileEntity;
    
    private static final ResourceLocation field_110410_t = new ResourceLocation("textures/smeltery.png");
    
    public GuiModularFurnaceSmeltery(InventoryPlayer playerInventory, TileEntityFurnaceCoreSmeltery tileEntity)
    {
        super(new ContainerModularFurnaceSmeltery(playerInventory, tileEntity));
        
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        final String invTitle = "Modular Furnace Smeltery";
        fontRenderer.drawString(invTitle, xSize / 2 - fontRenderer.getStringWidth(invTitle) / 2, 6, 4210752);
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
            drawTexturedModalRect(x + 81, y + 56 - i1, 176, 12 - i1, 14, i1 + 2);
          
        }
        i1 = tileEntity.getCookProgressScaled(24);
        drawTexturedModalRect(x + 102, y + 42, 176, 14, i1 + 1, 16);
        drawTexturedModalRect(x + 49, y + 41, 176, 30, 23 - i1, 16);

    }
}
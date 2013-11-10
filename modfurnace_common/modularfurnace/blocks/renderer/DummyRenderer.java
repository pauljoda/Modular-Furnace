package modularfurnace.blocks.renderer;

import org.lwjgl.opengl.GL11;

import modularfurnace.client.ClientProxy;
import modularfurnace.tileentity.TileEntityFurnaceDummy;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modularfurnace.blocks.BlockManager;

public class DummyRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {

		render3DInventory(block, metadata, modelID, renderer);

	}

	public static void render3DInventory(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		Tessellator tessellator = Tessellator.instance;

		block.setBlockBoundsForItemRender();
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, metadata));
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, getOveryLay(block));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, metadata));
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, getOveryLay(block));

		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, metadata));
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, getOveryLay(block));

		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, metadata));
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, getOveryLay(block));

		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(-1F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, metadata));
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, getOveryLay(block));

		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, 5));
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, getOveryLay(block));

		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	private static Icon getOveryLay(Block block)
	{
		Icon output;

		if(block.blockID == BlockManager.furnaceCore.blockID || block.blockID == BlockManager.crafterInactive.blockID || block.blockID == BlockManager.furnaceDummyIO.blockID || block.blockID == BlockManager.furnaceCoreSmeltery.blockID || block.blockID == BlockManager.furnaceSmelteryBrick.blockID)
			output = BlockManager.overLayTexture.getIcon(0, 0);
		else if(block.blockID == BlockManager.lavaCore.blockID)
			output = Block.mobSpawner.getIcon(0, 0);
		else
			output = block.getIcon(0, 0);




		return output;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {

		//which render pass are we doing?
				if(ClientProxy.renderPass == 0)
				{
					World world1 = Minecraft.getMinecraft().theWorld;
					TileEntityFurnaceDummy dummy = null;

					if(block.blockID == BlockManager.furnaceDummyRedstone.blockID)
						renderer.renderStandardBlock(Block.blockRedstone, x, y, z);  

					if(block.blockID == BlockManager.furnaceDummy.blockID)
					{
						dummy = (TileEntityFurnaceDummy)world1.getBlockTileEntity(x, y, z);
						renderer.renderStandardBlock(dummy.getBlock(), x, y, z);
					}
					if(block.blockID == BlockManager.furnaceDummyDiamond.blockID)
						renderer.renderStandardBlock(Block.blockDiamond, x, y, z);

					if(block.blockID == BlockManager.furnaceDummyEmerald.blockID)
						renderer.renderStandardBlock(Block.blockEmerald, x, y, z);

					if(block.blockID == BlockManager.furnaceDummyGlowStone.blockID)
						renderer.renderStandardBlock(Block.blockIron, x, y, z);

					if(block.blockID == BlockManager.lavaCore.blockID)
						renderer.renderStandardBlock(Block.lavaMoving, x, y, z);

					if(block.blockID == BlockManager.furnaceDummySmeltery.blockID || block.blockID == BlockManager.furnaceSmelteryBrick.blockID)
						renderer.renderStandardBlock(Block.stoneBrick, x, y, z);

					if(block.blockID == BlockManager.furnaceDummyActiveIO.blockID || block.blockID == BlockManager.furnaceDummyIO.blockID)
						renderer.renderBlockUsingTexture(Block.dispenser, x, y, z, Block.dispenser.getIcon(1, 1));
					
					if(block.blockID == BlockManager.crafterActive.blockID || block.blockID == BlockManager.crafterInactive.blockID)
						renderer.renderBlockUsingTexture(Block.workbench, x, y, z, Block.workbench.getIcon(1, 0));
					
					if(block.blockID == BlockManager.furnaceCore.blockID || block.blockID == BlockManager.furnaceCoreSmeltery.blockID)
					{
						renderer.renderBlockAllFaces(Block.furnaceIdle, x, y, z);    
					}

					if(block.blockID == BlockManager.furnaceCoreActive.blockID || block.blockID == BlockManager.furnaceCoreSmelteryActive.blockID)
						renderer.renderBlockAllFaces(Block.furnaceBurning, x, y, z);    

				}
				else                   
				{

					if(block.blockID == BlockManager.lavaCore.blockID)
						renderer.renderStandardBlock(Block.mobSpawner, x, y, z);
					else if(block.blockID == BlockManager.furnaceDummySmeltery.blockID || block.blockID == BlockManager.furnaceSmelteryBrick.blockID || block.blockID == BlockManager.furnaceCoreSmeltery.blockID || block.blockID == BlockManager.furnaceCoreSmelteryActive.blockID)
						renderer.renderBlockUsingTexture(Block.cauldron, x, y, z, Block.cauldron.getIcon(1, 0));
					else
						renderer.renderStandardBlock(BlockManager.overLayTexture, x, y, z);
				}

				return true;
	}


	@Override
	public boolean shouldRender3DInInventory() {

		return true;
	}

	@Override
	public int getRenderId() {

		return ClientProxy.dummyRenderType;
	}

}

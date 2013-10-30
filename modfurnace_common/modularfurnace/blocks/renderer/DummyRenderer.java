package modularfurnace.blocks.renderer;

import modularfurnace.ModularFurnace;
import modularfurnace.client.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import modularfurnace.blocks.BlockManager;

public class DummyRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {

	}
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {

		//which render pass are we doing?
		if(ClientProxy.renderPass == 0)
		{
			if(block.blockID == BlockManager.furnaceDummyRedstone.blockID)
				renderer.renderStandardBlock(Block.blockRedstone, x, y, z);  

			if(block.blockID == BlockManager.furnaceDummy.blockID)
				renderer.renderStandardBlock(Block.cobblestone, x, y, z);

			if(block.blockID == BlockManager.furnaceDummyDiamond.blockID)
				renderer.renderStandardBlock(Block.blockDiamond, x, y, z);

			if(block.blockID == BlockManager.furnaceDummyEmerald.blockID)
				renderer.renderStandardBlock(Block.blockEmerald, x, y, z);

			if(block.blockID == BlockManager.furnaceDummyGlowStone.blockID)
				renderer.renderStandardBlock(Block.blockIron, x, y, z);

			if(block.blockID == BlockManager.lavaCore.blockID)
				renderer.renderStandardBlock(Block.lavaMoving, x, y, z);

		}
		else if(!ModularFurnace.useTextures)                   
		{

			if(block.blockID == BlockManager.lavaCore.blockID)
				renderer.renderStandardBlock(Block.mobSpawner, x, y, z);
			else
				renderer.renderStandardBlock(BlockManager.overLayTexture, x, y, z);
		}

		return true;
	}



	@Override
	public boolean shouldRender3DInInventory() {

		return false;
	}

	@Override
	public int getRenderId() {

		return ClientProxy.dummyRenderType;
	}

}

package de.maxhenkel.fakeblocks.blocks.tileentity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.corelib.client.RenderUtils;
import de.maxhenkel.fakeblocks.blocks.ModBlocks;
import de.maxhenkel.fakeblocks.blocks.tileentity.FakeBlockTileEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;

@OnlyIn(Dist.CLIENT)
public class FakeBlockRenderer extends TileEntityRenderer<FakeBlockTileEntity> {

    private Minecraft minecraft;

    public FakeBlockRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
        this.minecraft = Minecraft.getInstance();
    }

    @Override
    public void render(FakeBlockTileEntity target, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState state = ModBlocks.FAKE_BLOCK.getDefaultState();
        BlockState block = target.getBlock();
        if (block != null) {
            if (block.getRenderType() == BlockRenderType.MODEL) {
                state = block;
            }
        }

        matrixStackIn.push();
        BlockRendererDispatcher dispatcher = minecraft.getBlockRendererDispatcher();
        int color = minecraft.getBlockColors().getColor(state, null, null, 0);
        dispatcher.getBlockModelRenderer().renderModel(matrixStackIn.getLast(), bufferIn.getBuffer(RenderTypeLookup.func_239221_b_(state)), state, dispatcher.getModelForState(state), RenderUtils.getRed(color), RenderUtils.getGreen(color), RenderUtils.getBlue(color), combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);
        matrixStackIn.pop();
    }


}

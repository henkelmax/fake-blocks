package de.maxhenkel.fakeblocks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL11;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class TileentitySpecialRendererFakeBlock extends TileEntitySpecialRenderer<TileEntityFakeBlock> {

	public TileentitySpecialRendererFakeBlock() {

	}

	@Override
	public void renderTileEntityAt(TileEntityFakeBlock target, double x, double y, double z, float partialTicks,
			int destroyStage) {

		renderBlock(target, x, y, z, partialTicks, target.getWorld());

	}

	public void renderBlock(TileEntityFakeBlock target, double x, double y, double z, float partialTicks, World world) {
		IBlockState iblockstate = target.getRenderingBlock();
		if (iblockstate.getRenderType() == EnumBlockRenderType.MODEL) {

			this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			GlStateManager.pushMatrix();
			GlStateManager.disableLighting();
			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer vertexbuffer = tessellator.getBuffer();

			vertexbuffer.begin(7, DefaultVertexFormats.BLOCK);
			BlockPos blockpos = target.getPos();// new BlockPos(x, y, z);
			GlStateManager.translate((float) (x - (double) blockpos.getX()), (float) (y - (double) blockpos.getY()),
					(float) (z - (double) blockpos.getZ()));
			BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
			blockrendererdispatcher.getBlockModelRenderer().renderModel(world,
					blockrendererdispatcher.getModelForState(iblockstate), iblockstate, blockpos, vertexbuffer, false,
					MathHelper.getPositionRandom(target.getPos()));
			tessellator.draw();

			GlStateManager.enableLighting();
			GlStateManager.popMatrix();

		}

	}

}

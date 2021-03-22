package de.maxhenkel.fakeblocks.blocks.tileentity;

import de.maxhenkel.fakeblocks.Main;
import de.maxhenkel.fakeblocks.blocks.ModBlocks;
import de.maxhenkel.fakeblocks.blocks.tileentity.render.FakeBlockRenderer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ModTileEntities {

    public static TileEntityType<FakeBlockTileEntity> FAKE_BLOCK;

    public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
        FAKE_BLOCK = TileEntityType.Builder.of(FakeBlockTileEntity::new, ModBlocks.FAKE_BLOCK).build(null);
        FAKE_BLOCK.setRegistryName(new ResourceLocation(Main.MODID, "fakeblock"));
        event.getRegistry().register(FAKE_BLOCK);
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientSetup() {
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.FAKE_BLOCK, FakeBlockRenderer::new);
    }
}

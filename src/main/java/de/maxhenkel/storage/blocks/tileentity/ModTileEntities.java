package de.maxhenkel.storage.blocks.tileentity;

import de.maxhenkel.storage.Main;
import de.maxhenkel.storage.blocks.ModBlocks;
import de.maxhenkel.storage.blocks.tileentity.render.FakeBlockRenderer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ModTileEntities {

    public static TileEntityType<FakeBlockTileEntity> FAKE_BLOCK;

    public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
        FAKE_BLOCK = TileEntityType.Builder.create(FakeBlockTileEntity::new, ModBlocks.FAKE_BLOCK).build(null);
        FAKE_BLOCK.setRegistryName(new ResourceLocation(Main.MODID, "fakeblock"));
        event.getRegistry().register(FAKE_BLOCK);
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientSetup() {
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.FAKE_BLOCK, FakeBlockRenderer::new);
    }
}

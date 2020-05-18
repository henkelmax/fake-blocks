package de.maxhenkel.storage.integration.waila;

import de.maxhenkel.storage.blocks.tileentity.FakeBlockTileEntity;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.util.ResourceLocation;

@WailaPlugin
public class PluginFakeBlocks implements IWailaPlugin {

    static final ResourceLocation OBJECT_NAME_TAG = new ResourceLocation("waila", "object_name");
    static final ResourceLocation CONFIG_SHOW_REGISTRY = new ResourceLocation("waila", "show_registry");
    static final ResourceLocation REGISTRY_NAME_TAG = new ResourceLocation("waila", "registry_name");

    @Override
    public void register(IRegistrar registrar) {
        registrar.registerComponentProvider(HUDHandlerFakeBlock.INSTANCE, TooltipPosition.HEAD, FakeBlockTileEntity.class);
        registrar.registerComponentProvider(HUDHandlerFakeBlock.INSTANCE, TooltipPosition.TAIL, FakeBlockTileEntity.class);
    }

}

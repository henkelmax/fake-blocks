package de.maxhenkel.storage.integration.waila;

import de.maxhenkel.storage.blocks.ModBlocks;
import de.maxhenkel.storage.blocks.tileentity.FakeBlockTileEntity;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.*;
import mcp.mobius.waila.utils.ModIdentification;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class HUDHandlerFakeBlock implements IComponentProvider {

    static final HUDHandlerFakeBlock INSTANCE = new HUDHandlerFakeBlock();

    @Override
    public void appendHead(List<ITextComponent> tip, IDataAccessor accessor, IPluginConfig config) {
        Block block = ((FakeBlockTileEntity) accessor.getTileEntity()).getBlock();
        if (block == null) {
            block = ModBlocks.FAKE_BLOCK;
        }
        ITaggableList<ResourceLocation, ITextComponent> tooltip = (ITaggableList<ResourceLocation, ITextComponent>) tip;
        tooltip.setTag(PluginFakeBlocks.OBJECT_NAME_TAG, new StringTextComponent(String.format(Waila.CONFIG.get().getFormatting().getBlockName(), block.getNameTextComponent().getFormattedText())));
        if (config.get(PluginFakeBlocks.CONFIG_SHOW_REGISTRY)) {
            tooltip.setTag(PluginFakeBlocks.REGISTRY_NAME_TAG, new StringTextComponent(String.format(Waila.CONFIG.get().getFormatting().getRegistryName(), block.getRegistryName())));
        }
    }

    @Override
    public void appendTail(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
        tooltip.clear();
        Block block = ((FakeBlockTileEntity) accessor.getTileEntity()).getBlock();
        if (block == null) {
            block = ModBlocks.FAKE_BLOCK;
        }
        tooltip.add(new StringTextComponent(String.format(Waila.CONFIG.get().getFormatting().getModName(), ModIdentification.getModInfo(block).getName())));
    }
}
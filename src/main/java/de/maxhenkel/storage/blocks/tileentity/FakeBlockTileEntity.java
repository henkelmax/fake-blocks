package de.maxhenkel.storage.blocks.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;

public class FakeBlockTileEntity extends TileEntity {

    @Nullable
    protected Block block;

    public FakeBlockTileEntity() {
        super(ModTileEntities.FAKE_BLOCK);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("Block")) {
            block = Registry.BLOCK.getOrDefault(new ResourceLocation(compound.getString("Block")));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (block != null) {
            compound.putString("Block", block.getRegistryName().toString());
        }
        return compound;
    }

    @Nullable
    public Block getBlock() {
        return block;
    }

    public void setBlock(@Nullable Block block) {
        this.block = block;
        markDirty();
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

}

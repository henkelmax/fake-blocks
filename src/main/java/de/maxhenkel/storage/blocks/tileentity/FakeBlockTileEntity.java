package de.maxhenkel.storage.blocks.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class FakeBlockTileEntity extends TileEntity {

    @Nullable
    protected BlockState blockState;

    public FakeBlockTileEntity() {
        super(ModTileEntities.FAKE_BLOCK);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("Block")) {
            blockState = NBTUtil.readBlockState(compound.getCompound("Block"));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (blockState != null) {
            compound.put("Block", NBTUtil.writeBlockState(blockState));
        }
        return compound;
    }

    @Nullable
    public BlockState getBlock() {
        return blockState;
    }

    public void setBlockState(@Nullable BlockState blockState) {
        this.blockState = blockState;
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

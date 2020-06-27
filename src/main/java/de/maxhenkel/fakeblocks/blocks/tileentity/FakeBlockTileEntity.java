package de.maxhenkel.fakeblocks.blocks.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class FakeBlockTileEntity extends TileEntity {

    @Nullable
    protected BlockState blockState;

    public FakeBlockTileEntity() {
        super(ModTileEntities.FAKE_BLOCK);
    }

    @Override
    public void func_230337_a_(BlockState blockState, CompoundNBT compound) {
        super.func_230337_a_(blockState, compound);
        if (compound.contains("Block")) {
            this.blockState = NBTUtil.readBlockState(compound.getCompound("Block"));
        } else {
            this.blockState = null;
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
    public void markDirty() {
        super.markDirty();
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) world;
            serverWorld.getPlayers(player -> player.getDistanceSq(getPos().getX(), getPos().getY(), getPos().getZ()) <= 128D * 128D).forEach(this::syncContents);
        }
    }

    public void syncContents(ServerPlayerEntity player) {
        player.connection.sendPacket(getUpdatePacket());
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        func_230337_a_(getBlockState(), pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

}

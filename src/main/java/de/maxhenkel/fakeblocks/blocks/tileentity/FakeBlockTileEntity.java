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
    public void load(BlockState blockState, CompoundNBT compound) {
        super.load(blockState, compound);
        if (compound.contains("Block")) {
            this.blockState = NBTUtil.readBlockState(compound.getCompound("Block"));
        } else {
            this.blockState = null;
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
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
        setChanged();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) level;
            serverWorld.getPlayers(player -> player.distanceToSqr(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ()) <= 128D * 128D).forEach(this::syncContents);
        }
    }

    public void syncContents(ServerPlayerEntity player) {
        player.connection.send(getUpdatePacket());
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(worldPosition, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        load(getBlockState(), pkt.getTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(new CompoundNBT());
    }

}

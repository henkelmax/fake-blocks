package de.maxhenkel.fakeblocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public abstract class TileEntityFakeBase extends TileEntity{
	
	protected IBlockState block;
	
	private static final String TAG_DOMAIN="domain";
	private static final String TAG_NAME="name";
	private static final String TAG_STATE="state";
	
	public TileEntityFakeBase() {
		this.block=Blocks.STONE.getDefaultState();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setString(TAG_DOMAIN, block.getBlock().getRegistryName().getResourceDomain());
		compound.setString(TAG_NAME, block.getBlock().getRegistryName().getResourcePath());
		compound.setInteger(TAG_STATE, block.getBlock().getMetaFromState(block));
		
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		Block block=Block.REGISTRY.getObject(new ResourceLocation(compound.getString(TAG_DOMAIN), compound.getString(TAG_NAME)));

		this.block=block.getStateFromMeta(compound.getInteger(TAG_STATE));
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 1, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	public IBlockState getRenderingBlock(){
		return block;
	}
	
	public void setRenderingBlock(IBlockState state){
		this.block=state;
	}
}

package de.maxhenkel.fakeblocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public abstract class BlockFakeBase extends BlockContainer{

	public BlockFakeBase(Material materialIn) {
		super(materialIn);
	}
	
	public BlockFakeBase(Material blockMaterialIn, MapColor blockMapColorIn) {
		super(blockMaterialIn, blockMapColorIn);
	}
	
	
}

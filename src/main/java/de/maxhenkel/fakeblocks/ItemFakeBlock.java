package de.maxhenkel.fakeblocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFakeBlock extends Item {

	public static final String NAME = "fakeblock_item";

	public ItemFakeBlock() {
		this.setUnlocalizedName(NAME);
		this.setRegistryName(Main.MODID, NAME);
		this.setCreativeTab(CreativeTabs.DECORATIONS);// change
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack=player.getHeldItem(hand);
		if (stack.func_190916_E() != 0 && player.canPlayerEdit(pos.offset(facing), facing, stack)) {

			BlockPos placePos = pos.offset(facing);
			IBlockState copiedBlock = world.getBlockState(pos);

			if (canDuplicate(copiedBlock, world, pos)) {

				if (world.getBlockState(placePos).getBlock().isReplaceable(world, placePos)) {
					world.setBlockState(placePos, MBlocks.FAKE_BLOCK.getDefaultState());
					TileEntity te = world.getTileEntity(placePos);
					if (te != null && (te instanceof TileEntityFakeBlock)) {
						TileEntityFakeBlock tBlock = (TileEntityFakeBlock) te;
						Block x = world.getBlockState(pos).getBlock();
						TileEntity t = world.getTileEntity(pos);
						if (t != null && (t instanceof TileEntityFakeBase)) {
							TileEntityFakeBase teb = (TileEntityFakeBase) t;
							tBlock.setRenderingBlock(teb.getRenderingBlock());
							copiedBlock = teb.getRenderingBlock();
						} else {
							tBlock.setRenderingBlock(world.getBlockState(pos));
						}

					}

					SoundType soundtype = copiedBlock.getBlock().getSoundType(copiedBlock, world, pos, player);
					world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS,
							(soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					stack.func_190917_f(-1);

					return EnumActionResult.SUCCESS;
				}
			}
		}

		return EnumActionResult.FAIL;
	}
	
	public static boolean canDuplicate(IBlockState state, World world, BlockPos pos) {
		return state.getBlock().isNormalCube(state, world, pos) 
				|| (state.getBlock() instanceof BlockFakeBase)
				|| state.getBlock().equals(Blocks.GLASS);
	}

}

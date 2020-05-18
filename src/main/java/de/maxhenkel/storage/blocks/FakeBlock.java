package de.maxhenkel.storage.blocks;

import de.maxhenkel.storage.Config;
import de.maxhenkel.storage.Main;
import de.maxhenkel.storage.blocks.tileentity.FakeBlockTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;

public class FakeBlock extends Block implements ITileEntityProvider, IItemBlock {

    public static final Material MATERIAL = new Material(MaterialColor.AIR, false, false, false, true, true, false, false, PushReaction.BLOCK);

    public FakeBlock() {
        super(Block.Properties.create(MATERIAL).hardnessAndResistance(0.25F).sound(SoundType.SCAFFOLDING).doesNotBlockMovement());
        setRegistryName(new ResourceLocation(Main.MODID, "fakeblock"));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(getRegistryName());
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack heldItem = player.getHeldItem(handIn);

        FakeBlockTileEntity fakeBlock = getTileEntity(worldIn, pos).orElse(null);
        if (fakeBlock != null && fakeBlock.getBlock() == null) {
            if (heldItem.getItem() instanceof BlockItem) {
                Block block = ((BlockItem) heldItem.getItem()).getBlock();
                BlockState setBlockstate = block.getStateForPlacement(new BlockItemUseContext(new ItemUseContext(player, handIn, hit)));
                if (Block.isOpaque(block.getCollisionShape(setBlockstate, worldIn, pos, ISelectionContext.dummy())) && block.getRenderType(setBlockstate) == BlockRenderType.MODEL && !Config.SERVER.getFakeBlockBlacklist().contains(block)) {
                    fakeBlock.setBlockState(setBlockstate);
                    if (!worldIn.isRemote) {
                        SoundType type = block.getSoundType(setBlockstate);
                        worldIn.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, type.getPlaceSound(), SoundCategory.BLOCKS, type.getVolume(), type.getPitch());
                    }
                    if (!player.abilities.isCreativeMode) {
                        heldItem.shrink(1);
                    }
                    return ActionResultType.SUCCESS;
                }
            }
        } else if (fakeBlock != null && fakeBlock.getBlock() != null) {
            return ActionResultType.PASS;
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        Optional<FakeBlockTileEntity> tileEntity = getTileEntity(world, pos);
        if (tileEntity.isPresent() && tileEntity.get().getBlock() != null) {
            BlockState blockState = tileEntity.get().getBlock();
            SoundType type = blockState.getBlock().getSoundType(blockState);
            world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, type.getPlaceSound(), SoundCategory.BLOCKS, type.getVolume(), type.getPitch());
            tileEntity.get().setBlockState(null);
            if (!player.isCreative()) {
                spawnAsEntity(world, pos, new ItemStack(blockState.getBlock()));
            }
            return false;
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    public static Optional<FakeBlockTileEntity> getTileEntity(IBlockReader world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof FakeBlockTileEntity) {
            return Optional.of((FakeBlockTileEntity) te);
        }
        return Optional.empty();
    }

    @Override
    public SoundType getSoundType(BlockState state, IWorldReader world, BlockPos pos, @Nullable Entity entity) {
        Optional<FakeBlockTileEntity> tileEntity = getTileEntity(world, pos);
        if (tileEntity.isPresent() && tileEntity.get().getBlock() != null) {
            return tileEntity.get().getBlock().getBlock().getSoundType(state, world, pos, entity);
        }
        return super.getSoundType(state, world, pos, entity);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new FakeBlockTileEntity();
    }

    protected static final VoxelShape SHAPE_EMPTY = VoxelShapes.empty();
    protected static final VoxelShape SHAPE_FULL = VoxelShapes.fullCube();

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE_EMPTY;
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return SHAPE_FULL;
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return SHAPE_FULL;
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        Optional<FakeBlockTileEntity> tileEntity = getTileEntity(world, pos);
        return tileEntity.isPresent() && tileEntity.get().getBlock() != null ? new ItemStack(tileEntity.get().getBlock().getBlock()) : super.getPickBlock(state, target, world, pos, player);
    }
}

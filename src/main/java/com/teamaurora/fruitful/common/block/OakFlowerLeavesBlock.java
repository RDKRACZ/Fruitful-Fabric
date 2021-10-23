package com.teamaurora.fruitful.common.block;

import co.eltrut.differentiate.core.util.GroupUtil;
import com.teamaurora.fruitful.core.registry.FruitfulBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class OakFlowerLeavesBlock extends LeavesBlock {
    private final Item followItem;

    public OakFlowerLeavesBlock(Settings settings, Item followItem) {
        super(settings);
        this.followItem = followItem;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    /**
     * Performs a random tick on a block.
     */
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getMoonSize() == 1.0 && !state.get(LeavesBlock.PERSISTENT)) {
            boolean canBlossom = true;
            for (Direction dir : Direction.values()) {
                if (world.getBlockState(pos.offset(dir)).getBlock() instanceof OakBlossomBlock) canBlossom = false;
            }
            if (canBlossom) {
                world.setBlockState(pos, FruitfulBlocks.BLOSSOMING_OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, false).with(LeavesBlock.DISTANCE, state.get(LeavesBlock.DISTANCE)).with(OakBlossomBlock.POLLINATED, world.getRandom().nextInt(250) == 0));
            } else if (state.getBlock() != FruitfulBlocks.FLOWERING_OAK_LEAVES) {
                world.setBlockState(pos, FruitfulBlocks.FLOWERING_OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, false).with(LeavesBlock.DISTANCE, state.get(LeavesBlock.DISTANCE)));
            }
        }

        super.randomTick(state, world, pos, random);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, updateDistanceFromLogs(state, world, pos), 3);
    }

    private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
        var i = 7;
        var blockpos$mutable = new BlockPos.Mutable();

        for(Direction direction : Direction.values()) {
            blockpos$mutable.set(pos, direction);
            i = Math.min(i, getDistanceFromLog(world.getBlockState(blockpos$mutable)) + 1);
            if (i == 1) {
                break;
            }
        }

        return state.with(DISTANCE, i);
    }

    private static int getDistanceFromLog(BlockState state) {
        if (BlockTags.LOGS.contains(state.getBlock())) {
            return 0;
        } else {
            return state.getBlock() instanceof LeavesBlock || state.getBlock() instanceof OakBlossomBlock ? state.get(DISTANCE) : 7;
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return updateDistanceFromLogs(this.getDefaultState().with(PERSISTENT, Boolean.TRUE), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> items) {
        GroupUtil.fillItem(this.asItem(), this.followItem, group, items);
    }
}
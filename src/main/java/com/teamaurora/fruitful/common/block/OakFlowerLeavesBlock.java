package com.teamaurora.fruitful.common.block;

import com.teamaurora.fruitful.core.registry.FruitfulBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import java.util.Random;

public class OakFlowerLeavesBlock extends LeavesBlock {
    public OakFlowerLeavesBlock(Settings properties) {
        super(properties);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    /**
     * Performs a random tick on a block.
     */
    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (worldIn.getMoonSize() == 1.0 && !state.get(LeavesBlock.PERSISTENT)) {
            boolean canBlossom = true;
            for (Direction dir : Direction.values()) {
                if (worldIn.getBlockState(pos.offset(dir)).getBlock() instanceof OakBlossomBlock) canBlossom = false;
            }
            if (canBlossom) {
                worldIn.setBlockState(pos, FruitfulBlocks.BLOSSOMING_OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, false).with(LeavesBlock.DISTANCE, state.get(LeavesBlock.DISTANCE)).with(OakBlossomBlock.POLLINATED, worldIn.getRandom().nextInt(250) == 0));
            } else if (state.getBlock() != FruitfulBlocks.FLOWERING_OAK_LEAVES) {
                worldIn.setBlockState(pos, FruitfulBlocks.FLOWERING_OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, false).with(LeavesBlock.DISTANCE, state.get(LeavesBlock.DISTANCE)));
            }
        }

        super.randomTick(state, worldIn, pos, random);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        worldIn.setBlockState(pos, updateDistanceFromLogs(state, worldIn, pos), 3);
    }

    private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess worldIn, BlockPos pos) {
        int i = 7;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for(Direction direction : Direction.values()) {
            blockpos$mutable.set(pos, direction);
            i = Math.min(i, getDistanceFromLog(worldIn.getBlockState(blockpos$mutable)) + 1);
            if (i == 1) {
                break;
            }
        }

        return state.with(DISTANCE, i);
    }

    private static int getDistanceFromLog(BlockState neighbor) {
        if (BlockTags.LOGS.contains(neighbor.getBlock())) {
            return 0;
        } else {
            return neighbor.getBlock() instanceof LeavesBlock || neighbor.getBlock() instanceof OakBlossomBlock ? neighbor.get(DISTANCE) : 7;
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return updateDistanceFromLogs(this.getDefaultState().with(PERSISTENT, Boolean.TRUE), context.getWorld(), context.getBlockPos());
    }
}
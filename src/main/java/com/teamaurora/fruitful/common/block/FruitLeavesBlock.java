package com.teamaurora.fruitful.common.block;

import com.teamaurora.fruitful.core.registry.FruitfulBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class FruitLeavesBlock extends LeavesBlock {
    private final LeavesBlock leavesBlock;
    private final Item fruitItem;

    public FruitLeavesBlock(Settings properties, LeavesBlock leaves, Item fruit) {
        super(properties);
        leavesBlock = leaves;
        fruitItem = fruit;
    }

    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult hit) {
        dropStack(worldIn, pos, new ItemStack(fruitItem, 1));
        worldIn.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
        worldIn.setBlockState(pos, leavesBlock.getDefaultState().with(LeavesBlock.PERSISTENT, state.get(LeavesBlock.PERSISTENT)).with(LeavesBlock.DISTANCE, state.get(LeavesBlock.DISTANCE)), 2);
        return ActionResult.success(worldIn.isClient);
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
        if (worldIn.getMoonSize() <= 0.25 && !state.get(LeavesBlock.PERSISTENT)) {
            dropStack(worldIn, pos, new ItemStack(fruitItem, 1));
            worldIn.setBlockState(pos, FruitfulBlocks.BUDDING_OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, false).with(LeavesBlock.DISTANCE, state.get(LeavesBlock.DISTANCE)));
        }
        super.randomTick(state, worldIn, pos, random);
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

    @Override
    public void scheduledTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        worldIn.setBlockState(pos, updateDistanceFromLogs(state, worldIn, pos), 3);
    }
}
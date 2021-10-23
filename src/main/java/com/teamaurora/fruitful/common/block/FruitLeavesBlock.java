package com.teamaurora.fruitful.common.block;

import co.eltrut.differentiate.core.util.GroupUtil;
import com.teamaurora.fruitful.core.registry.FruitfulBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class FruitLeavesBlock extends LeavesBlock {
    private final LeavesBlock leavesBlock;
    private final Item fruitItem;
    private final Item followItem;

    public FruitLeavesBlock(Settings settings, LeavesBlock leavesBlock, Item fruitItem, Item followItem) {
        super(settings);
        this.leavesBlock = leavesBlock;
        this.fruitItem = fruitItem;
        this.followItem = followItem;
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        dropStack(world, pos, new ItemStack(fruitItem, 1));
        world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
        world.setBlockState(pos, leavesBlock.getDefaultState().with(LeavesBlock.PERSISTENT, state.get(LeavesBlock.PERSISTENT)).with(LeavesBlock.DISTANCE, state.get(LeavesBlock.DISTANCE)), 2);
        return ActionResult.success(world.isClient);
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
        if (world.getMoonSize() <= 0.25 && !state.get(LeavesBlock.PERSISTENT)) {
            dropStack(world, pos, new ItemStack(fruitItem, 1));
            world.setBlockState(pos, FruitfulBlocks.BUDDING_OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, false).with(LeavesBlock.DISTANCE, state.get(LeavesBlock.DISTANCE)));
        }
        super.randomTick(state, world, pos, random);
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
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, updateDistanceFromLogs(state, world, pos), 3);
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> items) {
        GroupUtil.fillItem(this.asItem(), this.followItem, group, items);
    }
}
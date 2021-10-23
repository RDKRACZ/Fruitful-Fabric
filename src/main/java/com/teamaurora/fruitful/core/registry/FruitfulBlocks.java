package com.teamaurora.fruitful.core.registry;

import co.eltrut.differentiate.core.registrator.BlockHelper;
import com.teamaurora.fruitful.common.block.FollowSaplingBlock;
import com.teamaurora.fruitful.common.block.FruitLeavesBlock;
import com.teamaurora.fruitful.common.block.OakBlossomBlock;
import com.teamaurora.fruitful.common.block.OakFlowerLeavesBlock;
import com.teamaurora.fruitful.common.block.trees.FloweringOakTree;
import com.teamaurora.fruitful.core.Fruitful;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

public class FruitfulBlocks {
    private static final BlockHelper HELPER = Fruitful.REGISTRATOR.getHelper(Registry.BLOCK);

    public static Block FLOWERING_OAK_LEAVES = HELPER.createSimpleBlock("flowering_oak_leaves", new OakFlowerLeavesBlock(Settings.FLOWERING_OAK_LEAVES, Items.FLOWERING_AZALEA), ItemGroup.DECORATIONS);
    public static Block BUDDING_OAK_LEAVES = HELPER.createSimpleBlock("budding_oak_leaves", new OakFlowerLeavesBlock(Settings.FLOWERING_OAK_LEAVES, FLOWERING_OAK_LEAVES.asItem()), ItemGroup.DECORATIONS);
    public static Block BLOSSOMING_OAK_LEAVES = HELPER.createSimpleBlock("blossoming_oak_leaves", new OakBlossomBlock(Settings.FLOWERING_OAK_LEAVES, BUDDING_OAK_LEAVES.asItem()), ItemGroup.DECORATIONS);
    public static Block APPLE_OAK_LEAVES = HELPER.createSimpleBlock("apple_oak_leaves", new FruitLeavesBlock(Settings.FLOWERING_OAK_LEAVES, (LeavesBlock)FruitfulBlocks.BUDDING_OAK_LEAVES, Items.APPLE, BLOSSOMING_OAK_LEAVES.asItem()), ItemGroup.DECORATIONS);

    public static Block FLOWERING_OAK_SAPLING = HELPER.createSimpleBlock("flowering_oak_sapling", new FollowSaplingBlock(new FloweringOakTree(), Settings.FLOWERING_OAK_SAPLING, Items.DARK_OAK_SAPLING), ItemGroup.DECORATIONS);
    public static Block POTTED_FLOWERING_OAK_SAPLING = HELPER.createBlockExcludingItem("potted_flowering_oak_sapling", new FlowerPotBlock(FLOWERING_OAK_SAPLING, FabricBlockSettings.copy(Blocks.POTTED_ALLIUM)));

    public static final class Settings {
        public static final FabricBlockSettings FLOWERING_OAK_LEAVES = FabricBlockSettings.of(Material.LEAVES, MapColor.GREEN).breakByTool(FabricToolTags.HOES).nonOpaque().strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).allowsSpawning(Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never);
        public static final FabricBlockSettings FLOWERING_OAK_SAPLING = FabricBlockSettings.of(Material.PLANT, MapColor.GREEN).noCollision().ticksRandomly().strength(0.0F).sounds(BlockSoundGroup.GRASS);
    }
}
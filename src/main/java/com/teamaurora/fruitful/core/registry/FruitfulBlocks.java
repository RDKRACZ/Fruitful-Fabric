package com.teamaurora.fruitful.core.registry;

import com.teamaurora.fruitful.common.block.FruitLeavesBlock;
import com.teamaurora.fruitful.common.block.OakBlossomBlock;
import com.teamaurora.fruitful.common.block.OakFlowerLeavesBlock;
import com.teamaurora.fruitful.common.block.trees.FloweringOakTree;
import com.teamaurora.fruitful.core.Fruitful;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

import java.util.function.Supplier;

public class FruitfulBlocks {
    public static OakFlowerLeavesBlock FLOWERING_OAK_LEAVES = register("flowering_oak_leaves", new OakFlowerLeavesBlock(FruitfulBlocks.Settings.FLOWERING_OAK_LEAVES), ItemGroup.DECORATIONS);
    public static OakFlowerLeavesBlock BUDDING_OAK_LEAVES = register("budding_oak_leaves", new OakFlowerLeavesBlock(FruitfulBlocks.Settings.FLOWERING_OAK_LEAVES), ItemGroup.DECORATIONS);
    public static OakBlossomBlock BLOSSOMING_OAK_LEAVES = register("blossoming_oak_leaves", new OakBlossomBlock(FruitfulBlocks.Settings.FLOWERING_OAK_LEAVES), ItemGroup.DECORATIONS);
    public static FruitLeavesBlock APPLE_OAK_LEAVES = register("apple_oak_leaves", new FruitLeavesBlock(FruitfulBlocks.Settings.FLOWERING_OAK_LEAVES, FruitfulBlocks.BUDDING_OAK_LEAVES, Items.APPLE), ItemGroup.DECORATIONS);

    public static SaplingBlock FLOWERING_OAK_SAPLING = register("flowering_oak_sapling", new SaplingBlock(new FloweringOakTree(), Settings.FLOWERING_OAK_SAPLING), ItemGroup.DECORATIONS);
    public static FlowerPotBlock POTTED_FLOWERING_OAK_SAPLING = register("potted_flowering_oak_sapling", new FlowerPotBlock(FLOWERING_OAK_SAPLING, FabricBlockSettings.copy(Blocks.POTTED_ALLIUM)));

    private static <B extends Block> B register(String id, B block) {
        Registry.register(Registry.BLOCK, Fruitful.id(id), block);
        return block;
    }

    private static <B extends Block> B register(String id, B block, ItemGroup group) {
        Registry.register(Registry.BLOCK, Fruitful.id(id), block);
        Registry.register(Registry.ITEM, Fruitful.id(id), new BlockItem(block, new Item.Settings().group(group)));
        return block;
    }

    public static final class Settings {
        public static final FabricBlockSettings FLOWERING_OAK_LEAVES = FabricBlockSettings.of(Material.LEAVES, MaterialColor.GREEN).breakByTool(FabricToolTags.HOES).nonOpaque().strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).allowsSpawning(FruitfulBlocks.Settings::allowsSpawnOnLeaves).suffocates(FruitfulBlocks.Settings::isntSolid).blockVision(FruitfulBlocks.Settings::isntSolid);
        public static final FabricBlockSettings FLOWERING_OAK_SAPLING = FabricBlockSettings.of(Material.PLANT, MaterialColor.GREEN).noCollision().ticksRandomly().strength(0.0F).sounds(BlockSoundGroup.GRASS);

        public static boolean allowsSpawnOnLeaves(BlockState state, BlockView access, BlockPos pos, EntityType<?> entity) {
            return entity == EntityType.OCELOT || entity == EntityType.PARROT;
        }

        public static boolean alwaysAllowSpawn(BlockState state, BlockView reader, BlockPos pos, EntityType<?> entity) {
            return true;
        }

        public static boolean needsPostProcessing(BlockState state, BlockView reader, BlockPos pos) {
            return true;
        }

        public static boolean isntSolid(BlockState state, BlockView reader, BlockPos pos) {
            return false;
        }
    }
}
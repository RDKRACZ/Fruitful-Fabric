package com.teamaurora.fruitful.common.block.trees;

import com.teamaurora.fruitful.core.registry.FruitfulFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class FloweringOakTree extends SaplingGenerator {
    @Nullable
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random randomIn, boolean largeHive) {
        if (randomIn.nextInt(10) == 0) {
            return largeHive ? FruitfulFeatures.Configured.FLOWERING_FANCY_OAK_BEES_005 : FruitfulFeatures.Configured.FLOWERING_FANCY_OAK;
        } else {
            return largeHive ? FruitfulFeatures.Configured.FLOWERING_OAK_BEES_005 : FruitfulFeatures.Configured.FLOWERING_OAK;
        }
    }
}

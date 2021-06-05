package com.teamaurora.fruitful.core.other;

import com.teamaurora.fruitful.core.registry.FruitfulBlocks;
import com.teamaurora.fruitful.core.registry.FruitfulItems;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.ComposterBlock;

public class FruitfulCompat {
    public static void registerCompact() {
        FruitfulCompat.registerCompostables();
        FruitfulCompat.registerFlammables();
    }

    public static void registerCompostables() {
        ComposterBlock.registerCompostableItem(0.95f, FruitfulBlocks.APPLE_OAK_LEAVES);
        ComposterBlock.registerCompostableItem(0.3f, FruitfulBlocks.FLOWERING_OAK_LEAVES);
        ComposterBlock.registerCompostableItem(0.3f, FruitfulBlocks.BUDDING_OAK_LEAVES);
        ComposterBlock.registerCompostableItem(0.3f, FruitfulBlocks.BLOSSOMING_OAK_LEAVES);

        ComposterBlock.registerCompostableItem(0.3f, FruitfulBlocks.FLOWERING_OAK_SAPLING);
        ComposterBlock.registerCompostableItem(0.75f, FruitfulItems.BAKED_APPLE);
    }

    public static void registerFlammables() {
        FlammableBlockRegistry.getDefaultInstance().add(FruitfulBlocks.APPLE_OAK_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(FruitfulBlocks.FLOWERING_OAK_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(FruitfulBlocks.BUDDING_OAK_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(FruitfulBlocks.BLOSSOMING_OAK_LEAVES, 30, 60);
    }
}
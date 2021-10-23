package com.teamaurora.fruitful.core.other;

import co.eltrut.differentiate.core.util.DataUtil;
import com.teamaurora.fruitful.core.registry.FruitfulBlocks;
import com.teamaurora.fruitful.core.registry.FruitfulItems;

public class FruitfulCompat {
    static {
        // Compostables //
        DataUtil.registerCompostable(FruitfulBlocks.APPLE_OAK_LEAVES, 0.95f);
        DataUtil.registerCompostable(FruitfulBlocks.FLOWERING_OAK_LEAVES, 0.3f);
        DataUtil.registerCompostable(FruitfulBlocks.BUDDING_OAK_LEAVES, 0.3f);
        DataUtil.registerCompostable(FruitfulBlocks.BLOSSOMING_OAK_LEAVES, 0.3f);
        DataUtil.registerCompostable(FruitfulBlocks.FLOWERING_OAK_SAPLING, 0.3f);
        DataUtil.registerCompostable(FruitfulItems.BAKED_APPLE, 0.75f);

        // Flammables //
        DataUtil.registerFlammable(FruitfulBlocks.APPLE_OAK_LEAVES, 30, 60);
        DataUtil.registerFlammable(FruitfulBlocks.FLOWERING_OAK_LEAVES, 30, 60);
        DataUtil.registerFlammable(FruitfulBlocks.BUDDING_OAK_LEAVES, 30, 60);
        DataUtil.registerFlammable(FruitfulBlocks.BLOSSOMING_OAK_LEAVES, 30, 60);
    }
}
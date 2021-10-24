package com.teamaurora.fruitful.core.other;

import co.eltrut.differentiate.core.util.DataUtil;
import com.teamaurora.fruitful.core.registry.FruitfulBlocks;
import com.teamaurora.fruitful.core.registry.FruitfulItems;

public class FruitfulCompat {
    static {
        // Compostables //
        DataUtil.registerCompostable(FruitfulBlocks.APPLE_OAK_LEAVES, DataUtil.CompostableChance.FRUIT_LEAVES);
        DataUtil.registerCompostable(FruitfulBlocks.FLOWERING_OAK_LEAVES, DataUtil.CompostableChance.LEAVES);
        DataUtil.registerCompostable(FruitfulBlocks.BUDDING_OAK_LEAVES, DataUtil.CompostableChance.LEAVES);
        DataUtil.registerCompostable(FruitfulBlocks.BLOSSOMING_OAK_LEAVES, DataUtil.CompostableChance.LEAVES);
        DataUtil.registerCompostable(FruitfulBlocks.FLOWERING_OAK_SAPLING, DataUtil.CompostableChance.SAPLINGS);
        DataUtil.registerCompostable(FruitfulItems.BAKED_APPLE, DataUtil.CompostableChance.BAKED_GOODS);

        // Flammables //
        DataUtil.registerFlammable(FruitfulBlocks.APPLE_OAK_LEAVES, DataUtil.FlammableChance.LEAVES.getLeft(), DataUtil.FlammableChance.LEAVES.getRight());
        DataUtil.registerFlammable(FruitfulBlocks.FLOWERING_OAK_LEAVES, DataUtil.FlammableChance.LEAVES.getLeft(), DataUtil.FlammableChance.LEAVES.getRight());
        DataUtil.registerFlammable(FruitfulBlocks.BUDDING_OAK_LEAVES, DataUtil.FlammableChance.LEAVES.getLeft(), DataUtil.FlammableChance.LEAVES.getRight());
        DataUtil.registerFlammable(FruitfulBlocks.BLOSSOMING_OAK_LEAVES, DataUtil.FlammableChance.LEAVES.getLeft(), DataUtil.FlammableChance.LEAVES.getRight());
    }
}
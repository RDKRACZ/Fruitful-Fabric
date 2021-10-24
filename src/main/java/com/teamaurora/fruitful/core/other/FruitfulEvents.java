package com.teamaurora.fruitful.core.other;

import co.eltrut.differentiate.core.util.DealUtil;
import com.teamaurora.fruitful.core.registry.FruitfulBlocks;
import net.minecraft.item.Items;

public class FruitfulEvents {
    static {
        // Trades //
        DealUtil.registerWanderingTradeItemWithoutMultiplier(2, FruitfulBlocks.FLOWERING_OAK_SAPLING.asItem(), Items.EMERALD, 5, 1, 8, 1);
    }
}
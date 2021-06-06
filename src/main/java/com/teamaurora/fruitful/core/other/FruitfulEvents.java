package com.teamaurora.fruitful.core.other;

import com.teamaurora.fruitful.core.registry.FruitfulBlocks;
import com.teamaurora.fruitful.core.util.FruitfulSellItemFactory;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;

public class FruitfulEvents {
    public static void registerEvents(){
        FruitfulEvents.addTrades();
    }

    public static void addTrades() {
        TradeOfferHelper.registerWanderingTraderOffers(2, factories ->
                factories.add(new FruitfulSellItemFactory.SellItemFactory(FruitfulBlocks.FLOWERING_OAK_SAPLING.asItem().getDefaultStack(), 5, 1, 8, 1))
        );
    }
}
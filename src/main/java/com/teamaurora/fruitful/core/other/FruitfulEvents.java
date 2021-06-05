package com.teamaurora.fruitful.core.other;

import com.teamaurora.fruitful.common.potion.SustainingEffect;
import com.teamaurora.fruitful.core.registry.FruitfulBlocks;
import com.teamaurora.fruitful.core.registry.FruitfulEffects;
import com.teamaurora.fruitful.core.util.FruitfulSellItemFactory;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;

import java.util.Objects;

public class FruitfulEvents {
    public static void registerEvents(){
        //FruitfulEvents.addEvents();
        FruitfulEvents.addTrades();
        UseItemCallback.EVENT.register(new SustainingEffect());
    }

    public static void addEvents() {
        // SUSTAINING //
        /*
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack itemStack = player.getStackInHand(hand);

            if (player.hasStatusEffect(FruitfulEffects.SUSTAINING)) {
                int amplifier = Objects.requireNonNull(player.getStatusEffect(FruitfulEffects.SUSTAINING)).getAmplifier();
                if (itemStack.getItem().isFood()) {
                    int foodToAdd = 2 * (amplifier + 1);
                    player.getHungerManager().add(foodToAdd, 0);
                }
            }

            return TypedActionResult.success(itemStack);
        });
         */

        // APPLES GIVE SUSTAINING //
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack itemStack = player.getStackInHand(hand).finishUsing(world, player);

            if (itemStack == Items.APPLE.finishUsing(itemStack, world, player)) {
                if (player.hasStatusEffect(FruitfulEffects.SUSTAINING)) {
                    int ticksRemaining = Objects.requireNonNull(player.getStatusEffect(FruitfulEffects.SUSTAINING)).getDuration();
                    player.addStatusEffect(new StatusEffectInstance(FruitfulEffects.SUSTAINING, Math.max(200, ticksRemaining), 0, false, false, true));
                } else {
                    player.addStatusEffect(new StatusEffectInstance(FruitfulEffects.SUSTAINING, 200, 0, false, false, true));
                }
            }

            return TypedActionResult.success(itemStack);
        });
    }

    public static void addTrades() {
        TradeOfferHelper.registerWanderingTraderOffers(2, factories ->
                factories.add(new FruitfulSellItemFactory.SellItemFactory(FruitfulBlocks.FLOWERING_OAK_SAPLING.asItem().getDefaultStack(), 5, 1, 8, 1))
        );
    }
}
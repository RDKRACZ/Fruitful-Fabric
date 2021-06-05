package com.teamaurora.fruitful.common.potion;

import com.teamaurora.fruitful.core.registry.FruitfulEffects;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SustainingEffect extends StatusEffect implements UseItemCallback {
    public SustainingEffect() {
        super(StatusEffectType.BENEFICIAL, 16774917);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {}

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity livingEntity, int amplifier, double health) {}

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> interact(PlayerEntity player, World world, Hand hand) {
        ItemStack item = player.getStackInHand(hand);

        if (player.hasStatusEffect(FruitfulEffects.SUSTAINING)) {
            int amplifier = Objects.requireNonNull(player.getStatusEffect(FruitfulEffects.SUSTAINING)).getAmplifier();
            if (item.isFood()) {
                int foodToAdd = 2 * (amplifier + 1);
                player.getHungerManager().add(foodToAdd, 0);
                return TypedActionResult.pass(item);
            }
        }

        return TypedActionResult.success(item);
    }
}
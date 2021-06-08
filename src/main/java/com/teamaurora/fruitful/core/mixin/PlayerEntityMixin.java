package com.teamaurora.fruitful.core.mixin;

import com.teamaurora.fruitful.core.other.FruitfulTags;
import com.teamaurora.fruitful.core.registry.FruitfulEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(at = @At("HEAD"), method = "eatFood", cancellable = true)
    public void eatFood(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // SUSTAINING //
        if (player.hasStatusEffect(FruitfulEffects.SUSTAINING)) {
            int amplifier = Objects.requireNonNull(player.getStatusEffect(FruitfulEffects.SUSTAINING)).getAmplifier();
            int foodToAdd = 2 * (amplifier + 1);
            player.getHungerManager().add(foodToAdd, 0);
        }
    }

    @Inject(at = @At("HEAD"), method = "eatFood", cancellable = true)
    public void onConsume(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // APPLES GIVE SUSTAINING //
        if (stack.getItem().isIn(FruitfulTags.Items.GIVES_SUSTAINING)) {
            if (player.hasStatusEffect(FruitfulEffects.SUSTAINING)) {
                int ticksRemaining = player.getStatusEffect(FruitfulEffects.SUSTAINING).getDuration();
                player.addStatusEffect(new StatusEffectInstance(FruitfulEffects.SUSTAINING, Math.max(200, ticksRemaining), 0, false, false, true));
            } else {
                player.addStatusEffect(new StatusEffectInstance(FruitfulEffects.SUSTAINING, 200, 0, false, false, true));
            }
        }
        if (stack.getItem().isIn(FruitfulTags.Items.GIVES_SUSTAINING_II)) {
            if (player.hasStatusEffect(FruitfulEffects.SUSTAINING)) {
                int ticksRemaining = player.getStatusEffect(FruitfulEffects.SUSTAINING).getDuration();
                player.addStatusEffect(new StatusEffectInstance(FruitfulEffects.SUSTAINING, Math.max(200, ticksRemaining), 1, false, false, true));
            } else {
                player.addStatusEffect(new StatusEffectInstance(FruitfulEffects.SUSTAINING, 200, 1, false, false, true));
            }
        }
        if (stack.getItem().isIn(FruitfulTags.Items.GIVES_SUSTAINING_LONG)) {
            if (player.hasStatusEffect(FruitfulEffects.SUSTAINING)) {
                int ticksRemaining = player.getStatusEffect(FruitfulEffects.SUSTAINING).getDuration();
                player.addStatusEffect(new StatusEffectInstance(FruitfulEffects.SUSTAINING, Math.max(400, ticksRemaining), 0, false, false, true));
            } else {
                player.addStatusEffect(new StatusEffectInstance(FruitfulEffects.SUSTAINING, 400, 0, false, false, true));
            }
        }
    }
}
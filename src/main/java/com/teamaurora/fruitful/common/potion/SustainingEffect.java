package com.teamaurora.fruitful.common.potion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import org.jetbrains.annotations.Nullable;

public class SustainingEffect extends StatusEffect {
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
}
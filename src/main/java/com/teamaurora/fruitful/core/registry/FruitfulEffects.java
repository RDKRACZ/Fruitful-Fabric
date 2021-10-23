package com.teamaurora.fruitful.core.registry;

import co.eltrut.differentiate.core.registrator.EffectHelper;
import com.teamaurora.fruitful.core.Fruitful;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.registry.Registry;

public class FruitfulEffects {
    private static final EffectHelper HELPER = Fruitful.REGISTRATOR.getHelper(Registry.STATUS_EFFECT);

    public static StatusEffect SUSTAINING = HELPER.createEffect("sustaining", new StatusEffect(StatusEffectCategory.BENEFICIAL, 16774917));
}
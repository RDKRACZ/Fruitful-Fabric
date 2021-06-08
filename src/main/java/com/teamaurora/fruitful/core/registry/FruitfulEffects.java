package com.teamaurora.fruitful.core.registry;

import com.teamaurora.fruitful.core.Fruitful;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.registry.Registry;

public class FruitfulEffects {
    public static StatusEffect SUSTAINING;

    private static <S extends StatusEffect> S register(String path, S effect) {
        Registry.register(Registry.STATUS_EFFECT, Fruitful.id(path), effect);
        return effect;
    }

    public static void init(){
        SUSTAINING = register("sustaining", new StatusEffect(StatusEffectType.BENEFICIAL, 16774917));
    }
}
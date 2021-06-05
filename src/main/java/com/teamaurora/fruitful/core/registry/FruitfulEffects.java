package com.teamaurora.fruitful.core.registry;

import com.teamaurora.fruitful.common.potion.SustainingEffect;
import com.teamaurora.fruitful.core.Fruitful;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.registry.Registry;

public class FruitfulEffects {
    public static final SustainingEffect SUSTAINING = register("sustaining", new SustainingEffect());

    private static <S extends StatusEffect> S register(String path, S effect) {
        Registry.register(Registry.STATUS_EFFECT, Fruitful.id(path), effect);
        return effect;
    }
}
package com.teamaurora.fruitful.core.registry;

import com.teamaurora.fruitful.core.Fruitful;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class FruitfulItems {
    public static Item BAKED_APPLE = register("baked_apple", new Item(new FabricItemSettings().food(Foods.BAKED_APPLE).group(ItemGroup.FOOD)));

    private static <I extends Item> I register(String id, I item) {
        Registry.register(Registry.ITEM, Fruitful.id(id), item);
        return item;
    }

    public static class Foods {
        public static final FoodComponent BAKED_APPLE = new FoodComponent.Builder().hunger(6).saturationModifier(0.4F).statusEffect(new StatusEffectInstance(FruitfulEffects.SUSTAINING, 200, 1, false, false, true), 1.0F).build();
    }
}
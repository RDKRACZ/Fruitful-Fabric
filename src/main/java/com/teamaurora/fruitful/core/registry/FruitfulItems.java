package com.teamaurora.fruitful.core.registry;

import co.eltrut.differentiate.common.item.FollowItem;
import co.eltrut.differentiate.core.registrator.ItemHelper;
import com.teamaurora.fruitful.core.Fruitful;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

public class FruitfulItems {
    private static final ItemHelper HELPER = Fruitful.REGISTRATOR.getHelper(Registry.ITEM);

    public static Item BAKED_APPLE = HELPER.createItem("baked_apple", new FollowItem(new FabricItemSettings().food(Foods.BAKED_APPLE).group(ItemGroup.FOOD), Items.APPLE));

    public static class Foods {
        public static final FoodComponent BAKED_APPLE = new FoodComponent.Builder().hunger(6).saturationModifier(0.4F).build();
    }
}
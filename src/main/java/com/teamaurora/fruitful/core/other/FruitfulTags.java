package com.teamaurora.fruitful.core.other;

import com.teamaurora.fruitful.core.Fruitful;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.tag.Tag.Identified;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class FruitfulTags {
    public static class Items {
        public static final Identified<Item> GIVES_SUSTAINING = (Identified<Item>) create(Fruitful.id("gives_sustaining"), TagRegistry::item);
        public static final Identified<Item> GIVES_SUSTAINING_II = (Identified<Item>) create(Fruitful.id("gives_sustaining_ii"), TagRegistry::item);
        public static final Identified<Item> GIVES_SUSTAINING_LONG = (Identified<Item>) create(Fruitful.id("gives_sustaining_long"), TagRegistry::item);
    }

    private static <E> Tag<E> create(Identifier pathName, Function<Identifier, Tag<E>> tagCreateSupplier) {
        return tagCreateSupplier.apply(pathName);
    }
}
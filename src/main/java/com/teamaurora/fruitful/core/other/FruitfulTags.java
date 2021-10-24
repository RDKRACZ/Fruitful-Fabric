package com.teamaurora.fruitful.core.other;

import co.eltrut.differentiate.core.util.DataUtil;
import com.teamaurora.fruitful.core.Fruitful;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;

public class FruitfulTags {
    public static class Items {
        public static final Tag<Item> GIVES_SUSTAINING = DataUtil.registerItemTag(Fruitful.id("gives_sustaining"));
        public static final Tag<Item> GIVES_SUSTAINING_II = DataUtil.registerItemTag(Fruitful.id("gives_sustaining_ii"));
        public static final Tag<Item> GIVES_SUSTAINING_LONG = DataUtil.registerItemTag(Fruitful.id("gives_sustaining_long"));
    }
}
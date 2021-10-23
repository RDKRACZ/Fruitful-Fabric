package com.teamaurora.fruitful.core.config;

import com.teamaurora.fruitful.core.Fruitful;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.Config.Gui.Background;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.RequiresRestart;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.world.biome.BiomeKeys;

import java.util.Arrays;
import java.util.List;

@Config(name = Fruitful.MOD_ID)
@Background(value = Fruitful.MOD_ID + ":textures/config/background.png")
public class FruitfulConfig implements ConfigData {
    @RequiresRestart
    @Comment("Biomes flowering oak trees can spawn in")
    public List<String> flowerBiomes = Arrays.asList(
            BiomeKeys.FLOWER_FOREST.getValue().toString(),
            BiomeKeys.FOREST.getValue().toString(),
            BiomeKeys.WOODED_HILLS.getValue().toString()
    );

    public static void registerConfig() {
        AutoConfig.register(FruitfulConfig.class, GsonConfigSerializer::new);
    }

    public static FruitfulConfig get() {
        return AutoConfig.getConfigHolder(FruitfulConfig.class).getConfig();
    }
}
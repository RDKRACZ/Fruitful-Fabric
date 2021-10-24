package com.teamaurora.fruitful.core;

import co.eltrut.differentiate.core.registrator.Registrator;
import com.google.common.reflect.Reflection;
import com.teamaurora.fruitful.core.config.FruitfulConfig;
import com.teamaurora.fruitful.core.other.FruitfulCompat;
import com.teamaurora.fruitful.core.other.FruitfulEvents;
import com.teamaurora.fruitful.core.registry.FruitfulBlocks;
import com.teamaurora.fruitful.core.registry.FruitfulEffects;
import com.teamaurora.fruitful.core.registry.FruitfulFeatures;
import com.teamaurora.fruitful.core.registry.FruitfulItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class Fruitful implements ModInitializer {
    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_NAME = "Fruitful";
    public static final String MOD_ID = "fruitful";

    public static final Registrator REGISTRATOR = new Registrator(MOD_ID);

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void onInitialize() {
        FruitfulConfig.registerConfig();

        Reflection.initialize(
                FruitfulEffects.class,
                FruitfulCompat.class,
                FruitfulBlocks.class,
                FruitfulItems.class,
                FruitfulEvents.class,
                FruitfulFeatures.class
        );

        log(Level.INFO, "Fruity!");
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }
}